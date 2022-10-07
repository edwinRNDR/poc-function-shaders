package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import shadergen.register

@JvmName("circleSDF1")
inline fun <reified D : Any> circleSDF(noinline f: (D) -> Vector2, radius: Double): (D) -> Double {
    val glslTemplate = """float #FUN#(#D# x) { 
        |   vec2 p = #f#(x);
        |   return length(p) - $radius;
        |}""".trimMargin()
    return { x: D -> val p = f(x)
        p.length - radius
    }.register("circleSDF", glslTemplate, "f" to f)
}

@JvmName("circleSDFDVector2")
inline fun <reified D : Any> ((D)->Vector2).circleSDF(radius: Double): (D) -> Double = circleSDF(this, radius)

