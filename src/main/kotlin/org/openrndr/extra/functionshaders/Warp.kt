package org.openrndr.extra.functionshaders

import shadergen.register

inline fun <reified D : Any, reified R : Any> ((D) -> R).warp(noinline warper: (D) -> D): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) { return #this#(#warper#(x)); }"""
    return { x: D -> this(warper(x)) }.register("warp", glslTemplate, "this" to this@warp, "warper" to warper)
}