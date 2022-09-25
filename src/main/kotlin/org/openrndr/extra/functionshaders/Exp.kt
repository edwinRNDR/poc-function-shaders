package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register
import kotlin.math.exp



@JvmName("expDR")
inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).exp(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return exp(#this#(v)); }"""
    return { v: D -> this(v).map(::exp) }.register("exp", glslTemplate, "this" to this@exp)
}

@JvmName("expDDouble")
inline fun <reified D:Any> ((D) -> Double).exp(): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return exp(#this#(v)); }"""
    return { v: D -> exp(this(v)) }.register("exp", glslTemplate, "this" to this@exp)

}
