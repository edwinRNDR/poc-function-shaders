package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector3
import shadergen.register

fun ((Vector3)->Double).gradient2DTAbsAngle(epsilon:Double = 0.01): (Vector3)->Double {
    val glslTemplate = """float #FUN#(#D# x) {
        |   float dx = #this#(x + vec3($epsilon, 0.0, 0.0)) - #this#(x - vec3($epsilon, 0.0, 0.0));
        |   float dy = #this#(x + vec3(0.0, $epsilon, 0.0)) - #this#(x - vec3(0.0, $epsilon, 0.0));
        |   dx /= 2 * $epsilon;
        |   dy /= 2 * $epsilon;
        |   return atan(abs(dy), abs(dx));
        |}
    """.trimMargin()
    return { x: Vector3 -> error("not implemented") }.register("gradient2DTAbsAngle", glslTemplate, "this" to this@gradient2DTAbsAngle)
}