package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register

inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).normalize(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return normalize(#this#(v)); }"""
    return { v: D -> glslTemplate; this(v).normalized }.register("normalize", glslTemplate, "this" to this@normalize)
}
