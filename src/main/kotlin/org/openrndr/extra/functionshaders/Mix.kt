package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import shadergen.register

@JvmName("mix1")
inline fun <reified D: Any, reified R:LinearType<R>> mix(noinline left: (D)->R, noinline right: (D)->R, noinline factor: (D)->Double) : (D)->R {
    val glslTemplate = """#R# #FUN#(#D# x) { return mix(#left#(x), #right#(x), #factor#(x)); }"""
    return { x: D -> val f = factor(x); left(x) * (1.0-f) + right(x) * f }.register("mix", glslTemplate, "left" to left, "right" to right, "factor" to factor)
}

inline fun <reified D : Any> ((D) -> Double).mix(
    noinline other: (D) -> Double,
    noinline factor: (D) -> Double
): ((D) -> Double) {
    val glslTemplate = """#R# #FUN#(#D# x) { return mix(#this#(x), #other#(x), #factor#(x)); }
    """.trimIndent()

    return { v: D ->
        val v0 = this(v)
        val v1 = other(v)
        val f = factor(v)
        v0 * (1.0 - f) + v1 * f
    }.register("mix", glslTemplate, "other" to other, "factor" to factor, "this" to this@mix)
}

@JvmName("mixDR")
inline fun <reified D : Any, reified R : LinearType<R>> ((D) -> R).mix(
    noinline other: (D) -> R,
    noinline factor: (D) -> Double
): ((D) -> R) {
    val glslTemplate = """#R# #FUN#(#D# x) { return mix(#this#(x), #other#(x), #factor#(x)); }
    """.trimIndent()

    return { v: D ->
        val v0 = this(v)
        val v1 = other(v)
        val f = factor(v)
        v0 * (1.0 - f) + v1 * f
    }.register("mix", glslTemplate, "other" to other, "factor" to factor, "this" to this@mix)
}