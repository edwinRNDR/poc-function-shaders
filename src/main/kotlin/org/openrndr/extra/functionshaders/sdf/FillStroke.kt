package org.openrndr.extra.functionshaders.sdf

import org.openrndr.color.ColorRGBa
import shadergen.register

inline fun <reified D : Any> fillStroke(
    noinline fill: (D) -> ColorRGBa,
    noinline stroke: (D) -> ColorRGBa,
    noinline sdf: (D) -> Double
): (D) -> ColorRGBa {
    val glslTemplate = """#R# #FUN#(#D# x) { 
        |   vec4 fill = #fill#(x);
        |   vec4 stroke = #stroke#(x);
        |   float sd = #sdf#(x);
        |   float sdw = fwidth(sd);
        |   float fillFactor = smoothstep(0.01, 0.0, sd);
        |   return fill * fillFactor;
        |}
    """.trimMargin()
    return { x: D ->
        val fill = fill(x)
        val stroke = stroke(x)
        val sd = sdf(x)
        val fillFactor = if (sd <= 0.0) 1.0 else 0.0
        fill * fillFactor
    }.register("fillStroke", glslTemplate, "fill" to fill, "stroke" to stroke, "sdf" to sdf)
}