package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register
import kotlin.math.cos

@JvmName("cosDR")
inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).cos(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return cos(#this#(v)); }"""
    return { v: D -> this(v).map(::cos) }.register("cos", glslTemplate, "this" to this@cos)
}

inline fun <reified D:Any> ((D) -> Double).cos(): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return cos(#this#(v)); }"""
    return { v: D -> cos(this(v)) }.register("cos", glslTemplate, "this" to this@cos)

}
