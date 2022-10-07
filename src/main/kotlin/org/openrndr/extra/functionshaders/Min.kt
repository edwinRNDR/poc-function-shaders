package org.openrndr.extra.functionshaders

import shadergen.register

inline fun <reified D:Any> min(vararg fs:((D)->Double)):(D)->Double {
    val named = fs.mapIndexed { index, it -> Pair("f$index", it)}
    val glslTemplate = """#R# #FUN#(#D# x) {
        |   float v = 10000000;
        |   ${named.joinToString("") { "    v = min(v, #${it.first}#(x));\n" }}
        |   return v;
        |}   
    """.trimMargin()
    return { x: D ->
        fs.minOfOrNull { it(x) }!!
    }.register("min", glslTemplate, *(named.toTypedArray()))

}