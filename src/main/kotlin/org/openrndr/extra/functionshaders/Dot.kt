package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register

@JvmName("dotDoubleR")
inline fun <reified R : EuclideanVector<R>> ((Double) -> R).dot(noinline other: (Double) -> R): (Double) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return dot(#this#(v)); }"""
    return { v: Double -> glslTemplate; this(v).dot(other(v)) }.register("dot", glslTemplate, "this" to this@dot)
}

inline fun <reified D : Any, reified R : EuclideanVector<R>> ((D) -> R).dot(noinline other: (D) -> R): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return dot(#this#(v)); }"""
    return { v: D -> glslTemplate; this(v).dot(other(v)) }.register("dot", glslTemplate, "this" to this@dot)
}
