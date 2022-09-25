package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register

inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).length(): (D) -> Double {
    val glslTemplate = """float #FUN#(#D# v) { return length(#this#(v)); }"""
    return { v: D -> glslTemplate; this(v).length }.register("length", glslTemplate, "this" to this@length)
}
