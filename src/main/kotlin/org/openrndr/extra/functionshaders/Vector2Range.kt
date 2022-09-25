package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import shadergen.register

fun ((Int, Vector2) -> Double).vector2Range(): (Int, Vector2) -> Vector2 {
    val glslTemplate =
        """vec2 #FUN#(int seed, #D# x) { return vec2(#this#(seed, x), #this#(seed^0x7f7f7f7f, vec2(-x.y, x.x))); }"""
    return { seed: Int, x: Vector2 -> Vector2(this(seed, x), this(seed xor 0x7f7f7f7f, x.perpendicular())) }
        .register("vector2Range", glslTemplate, "this" to this@vector2Range)
}