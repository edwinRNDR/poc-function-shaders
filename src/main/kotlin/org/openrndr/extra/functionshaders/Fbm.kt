package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register

inline fun <reified D : EuclideanVector<D>> ((Int, D) -> Double).fbm(
    octaves: Int = 8,
    lacunarity: Double = 2.0,
    gain: Double = 0.5
): (Int, D) -> Double {


    val glslTemplate = """float #FUN#(int seed, #D# x) { 
        |   float sum = #this#(seed, x);
        |   float amp = 1.0;
        |   #D# lx = x;
        |   for (int i = 1; i < $octaves; ++i) {
        |       lx *= $lacunarity;
        |       amp *= $gain;
        |       sum += #this#(seed + i, lx);
        |   }
        |   return sum;
        |}
    """.trimMargin()

    return { seed: Int, x: D ->
        var sum = this(seed, x)
        var amp = 1.0
        var lx = x
        for (i in 1 until octaves) {
            lx *= lacunarity
            amp *= gain
            sum += this(seed + i, lx) * amp
        }
        sum
    }.register("fbm", glslTemplate, "this" to this@fbm)
}