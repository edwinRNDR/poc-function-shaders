package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import shadergen.register
import kotlin.math.sqrt

@JvmName("modDR")
inline fun <reified D : Any, reified R : EuclideanVector<R>> ((D) -> R).mod(noinline right: (D) -> Double): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return mod(#this#(v), #R#(#right#(v))); }"""
    return { v: D -> this(v).map { x -> x.mod(right(v)) } }.register(
        "mod",
        glslTemplate,
        "this" to this@mod,
        "right" to right
    )
}

inline fun <reified D : Any> ((D) -> Double).mod(noinline right: (D)->Double): (D) -> Double {
    println("hallo")
    val glslTemplate = """#R# #FUN#(#D# v) { return mod(#this#(v), #right#(v)); }"""
    return { v: D -> sqrt(this(v)) }.register("mod", glslTemplate, "this" to this@mod, "right" to right)

}
