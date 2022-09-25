package shadergen

import org.openrndr.math.smoothstep

inline fun <reified D:Any> smoothstep(noinline edge0:(D)->Double, noinline edge1: (D)->Double, noinline t:(D)->Double) : (D)->Double {
    val glslTemplate = """float #FUN#(#D# x) { return smoothstep(#edge0#(x), #edge1#(x), #t#(x); }"""
    return { x:D -> smoothstep(edge0(x), edge1(x), t(x)) }.register("smoothstep", glslTemplate, "edge0" to edge0, " edge1" to edge1, "t" to t)
}

inline fun <reified D:Any> ((D)->Double).smoothstep(noinline edge0:(D)->Double, noinline edge1: (D)->Double, noinline t:(D)->Double) : (D)->Double {
    return shadergen.smoothstep(edge0, edge1, this@smoothstep)
}