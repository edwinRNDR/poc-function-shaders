package org.openrndr.extra.functionshaders

import shadergen.glslRangeType
import shadergen.register


inline fun <reified D : Any, reified I : Any, reified R : Any, reified RO : Any> ((D) -> R).tunnel(
    noinline f: (R) -> I,
    noinline g: (I) -> RO
): (D) -> RO {


    val glslTemplate = """#R# #FUN#(#D# x) {
        |   #RI# r = #this#(x);
        |   #I# i = #f#(r);
        |   return #g#(i);
        |} 
    """.trimMargin()

    return { x: D ->
        val r = this(x)
        val i = f(r)
        g(i)
    }.register(
        "tunnel",
        glslTemplate,
        "f" to f,
        "g" to g,
        "this" to this@tunnel,
        "I" to glslRangeType(f),
        "RI" to glslRangeType(this@tunnel)
    )

}