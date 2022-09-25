package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import shadergen.register

inline operator fun <reified D:Any, reified R: LinearType<R>> ((D) -> R).minus(noinline other: (D) -> R): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this(v) - #other#(v); }"""
    return { v: D -> this(v) + other(v) }.register("minus", glslTemplate, "this" to this@minus, "other" to other)
}
@JvmName("minusDDouble")
inline operator fun <reified D:Any> ((D) -> Double).minus(noinline other: (D) -> Double): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this(v) - #other#(v); }"""
    return { v: D -> this(v) + other(v) }.register("minus", glslTemplate, "this" to this@minus, "other" to other)
}

@JvmName("minusDoubleDouble")
operator fun ((Double) -> Double).minus(other: (Double) -> Double): (Double) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this(v) - #other#(v); }"""
    return { v: Double -> this(v) + other(v) }.register("minus", glslTemplate, "this" to this@minus, "other" to other)
}
