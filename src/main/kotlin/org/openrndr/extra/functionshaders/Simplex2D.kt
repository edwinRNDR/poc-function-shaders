package shadergen

import org.openrndr.extra.noise.simplex
import org.openrndr.math.Vector2

fun simplex2D() : (Int, Vector2)->Double {
    val glslTemplate = """#pragma import noise.simplex_2d
        |#R# #FUN#(int seed, #D# x) { return simplex_2d(seed, x); }
    """.trimMargin()
    return { seed: Int, x: Vector2 -> simplex(seed, x) }.register("simplex2D", glslTemplate)
}
