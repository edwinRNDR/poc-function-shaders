package org.openrndr.extra.functionshaders

import shadergen.register

inline fun <reified D:Any,reified R:Any> R.constant():(D)->R {
    val glslTemplate = "#R# #FUN#(#D# x) { return #this#; }"
    return { x: D -> this }.register("constant", glslTemplate, "this" to this@constant)
}
