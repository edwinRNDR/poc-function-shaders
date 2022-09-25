package org.openrndr.extra.functionshaders

import org.openrndr.extra.noise.fastFloor
import org.openrndr.extra.noise.valCoord2D
import org.openrndr.math.Vector2
import org.openrndr.math.mix
import shadergen.register

fun valueNoise2D(interpolation: (Double) -> Double = Double.identity): (Int, Vector2) -> Double {

    val glslTemplate = """
        
        #ifndef VALCOORD2D
        #define VALCOORD2D
        float valCoord2D(int seed, int x, int y){
            int X_PRIME = 1619;
            int Y_PRIME = 31337;
            int n = seed;
            n = n ^ X_PRIME * x;
            n = n ^ Y_PRIME * y;
            return n * n * n * 60493 / 2147483648.0;
        }
        #endif
        
        float #FUN#(int seed, vec2 v) { 
            int x0 = int(floor(v.x));
            int y0 = int(floor(v.y));
            int x1 = x0 + 1;
            int y1 = y0 + 1;

            float xs = #interpolation#(v.x - x0);
            float ys = #interpolation#(v.y - y0);

            float xf0 = mix(valCoord2D(seed, x0, y0), valCoord2D(seed, x1, y0), xs);
            float xf1 = mix(valCoord2D(seed, x0, y1), valCoord2D(seed, x1, y1), xs);

            return mix(xf0, xf1, ys);
        }
    """.trimIndent()


    return { seed: Int, v: Vector2 ->
        glslTemplate;
        val x0 = v.x.fastFloor()
        val y0 = v.y.fastFloor()
        val x1 = x0 + 1
        val y1 = y0 + 1

        val xs = interpolation(v.x - x0)
        val ys = interpolation(v.y - y0)

        val xf0 = mix(valCoord2D(seed, x0, y0), valCoord2D(seed, x1, y0), xs)
        val xf1 = mix(valCoord2D(seed, x0, y1), valCoord2D(seed, x1, y1), xs)

        mix(xf0, xf1, ys)
    }.register("valueNoise", glslTemplate, "interpolation" to interpolation)
}

inline fun <reified D : Any, reified R : Any> ((Int, D) -> R).bindSeed(noinline seed: (D) -> Int): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) { return #this#(#seed#(x), x); } """
    return { x: D -> this(seed(x), x) }.register("bindSeed", glslTemplate, "seed" to seed, "this" to this@bindSeed)
}

inline fun <reified D : Any, reified R : Any> ((Int, D) -> R).bindSeed(seed: Int): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) { return #this#(#seed#, x); } """
    return { x: D -> this(seed, x) }.register("bindSeed", glslTemplate, "seed" to seed, "this" to this@bindSeed)
}