package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register
import kotlin.math.abs

@JvmName("absDR")
inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).abs(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return abs(#this#(v)); }"""
    return { v: D -> this(v).map(::abs) }.register("abs", glslTemplate, "this" to this@abs)
}

inline fun <reified D:Any> ((D) -> Double).abs(): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return abs(#this#(v)); }"""
    return { v: D -> abs(this(v)) }.register("abs", glslTemplate, "this" to this@abs)

}
