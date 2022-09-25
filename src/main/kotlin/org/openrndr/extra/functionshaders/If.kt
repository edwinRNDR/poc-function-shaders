package org.openrndr.extra.functionshaders

import shadergen.glslRangeType
import shadergen.register

inline fun <reified D:Any> ((D) -> Double).lessThan(noinline other: (D) -> Double): (D) -> Boolean {
    val glslTemplate = """bool #FUN#(#D# x) { return #this#(x) < #other#(x); }"""
    return { x: D ->
        this(x) < other(x)
    }.register("lessThan", glslTemplate, "this" to this@lessThan, "other" to other)

}

inline fun <reified D:Any, reified R:Any> ((D) -> Boolean).thenElse(
    noinline action0: (D) -> R,
    noinline action1: (D) -> R
): (D) -> R {
    val glslTemplate = """#ROUT# #FUN#(#D# x) { return #this#(x)?  #action0#(x) : #action1#(x); }"""
    return { x: D ->
        if (this(x)) {
            action0(x)
        } else {
            action1(x)
        }
    }.register("thenElse", glslTemplate, "this" to this@thenElse, "ROUT" to glslRangeType(action0))
}