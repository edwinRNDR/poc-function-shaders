package org.openrndr.extra.functionshaders

import org.openrndr.color.ColorRGBa
import shadergen.register

data class ColorMap(val color: ColorRGBa, val center: Double, val width: Double, val fuzz: Double)


fun ColorMap.toGLSL() =
    "vec4(${color.r},${color.g},${color.b},${color.alpha}) * smoothstep(${center - width / 2.0}, ${center - (1.0 - fuzz) * width / 2.0}, v) * smoothstep(${center + width / 2.0}, ${center + (1.0 - fuzz) * width / 2.0}, v)"

inline fun <reified D : Any> ((D) -> Double).colorMap(entries: List<ColorMap>): (D) -> ColorRGBa {
    val glslTemplate = """vec4 #FUN#(#D# x) {
        |float v = #this#(x);
        |return ${entries.joinToString("+") { it.toGLSL() }};
        |}
    """.trimMargin()

    return { x: D -> error("not implemented ") }.register("colorMap", glslTemplate, "this" to this@colorMap)
}