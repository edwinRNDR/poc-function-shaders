package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import shadergen.register

inline fun <reified D:Any> ((D)-> Vector2).screenAngle() : (D)->Double {
    val glslTemplate = """float #FUN#(#D# x) {
        |   vec2 v = #this#(x);
        |   vec2 dx = dFdx(v);
        |   vec2 dy = dFdy(v);
        |   return atan(length(dy), length(dx));
        |}
    """.trimMargin()
    return { x: D ->

        error("not implemented")
    }.register("screenAngle", glslTemplate, "this" to this@screenAngle)
}

@JvmName("screenAngleDDouble")
inline fun <reified D:Any> ((D)-> Double).screenAngle() : (D)->Double {
    val glslTemplate = """float #FUN#(#D# x) {
        |   float v = #this#(x);
        |   float dx = dFdx(v);
        |   float dy = dFdy(v);
        |   return atan(abs(dy), abs(dx))*2;
        |}
    """.trimMargin()
    return { x: D ->

        error("not implemented")
    }.register("screenAngle", glslTemplate, "this" to this@screenAngle)
}