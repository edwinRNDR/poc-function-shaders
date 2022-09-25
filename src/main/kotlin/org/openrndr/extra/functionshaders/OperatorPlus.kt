package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import shadergen.register

inline operator fun <reified D:Any, reified R: LinearType<R>> ((D) -> R).plus(noinline other: (D) -> R): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) + #other#(v); }"""
    return { v: D -> this(v) + other(v) }.register("plus", glslTemplate, "this" to this@plus, "other" to other)
}
@JvmName("plusDDouble")
inline operator fun <reified D:Any> ((D) -> Double).plus(noinline other: (D) -> Double): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) + #other#(v); }"""
    return { v: D -> this(v) + other(v) }.register("plus", glslTemplate, "this" to this@plus, "other" to other)
}

@JvmName("plusDoubleDouble")
operator fun ((Double) -> Double).plus(other: (Double) -> Double): (Double) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) + #other#(v); }"""
    return { v: Double -> this(v) + other(v) }.register("plus", glslTemplate, "this" to this@plus, "other" to other)
}
