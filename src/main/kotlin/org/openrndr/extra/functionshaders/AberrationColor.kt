package org.openrndr.extra.functionshaders

import org.openrndr.color.ColorRGBa
import shadergen.register
import kotlin.math.abs

inline fun <reified D:Any> ((D)->Double).aberrationColor() : (D)-> ColorRGBa {
    // based on https://www.shadertoy.com/view/MdsyDX
    val glslTemplate = """vec4 #FUN#(#D# x) {
        |   float f = #this#(x);
        |   f = f * 3.0 - 1.5;
        |   return clamp(vec4(-f, 1.0 - abs(f), f, 1.0), 0.0, 1.0);
        |}""".trimMargin()
    return { x : D ->
        val f = this(x)
        val c = f * 3.0 - 1.5
        ColorRGBa(-c, 1.0 - abs(c), c)
    }.register("aberrationColor", glslTemplate, "this" to this@aberrationColor)
}