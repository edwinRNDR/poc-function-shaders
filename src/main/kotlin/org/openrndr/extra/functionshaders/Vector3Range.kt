package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

fun ((Int, Vector3) -> Double).vector3Range(): (Int, Vector3) -> Vector3 {
    val glslTemplate =
        """vec2 #FUN#(int seed, #D# x) { return vec3(#this#(seed, x), #this#(seed^0x7f7f7f7f, x), #this#(seed+49032,x)) ; }"""
    return { seed: Int, x: Vector3 -> Vector3(this(seed, x), this(seed xor 0x7f7f7f7f, x), this(seed + 49032,x)) }
        .register("vector3Range", glslTemplate, "this" to this@vector3Range)
}