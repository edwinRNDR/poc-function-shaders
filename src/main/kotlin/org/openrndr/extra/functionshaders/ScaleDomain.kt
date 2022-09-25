package shadergen

import org.openrndr.math.LinearType

inline fun <reified D:LinearType<D>, reified R:Any> ((D)->R).scaleDomain(scale: Double) : (D)->R {
    val glslTemplate = """#R# #FUN#(#D# x) { return #this#(x * #scale#); }"""
    return { x: D -> this(x * scale) }.register("scaleDomain", glslTemplate, "this" to this@scaleDomain, "scale" to scale)
}