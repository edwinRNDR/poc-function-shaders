package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register
import kotlin.math.sqrt

@JvmName("sqrtDR")
inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).sqrt(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return sqrt(#this#(v)); }"""
    return { v: D -> this(v).map(::sqrt) }.register("sqrt", glslTemplate, "this" to this@sqrt)
}

inline fun <reified D:Any> ((D) -> Double).sqrt(): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return sqrt(#this#(v)); }"""
    return { v: D -> sqrt(this(v)) }.register("sqrt", glslTemplate, "this" to this@sqrt)

}
