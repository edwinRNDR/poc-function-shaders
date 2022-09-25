package shadergen

import org.openrndr.math.EuclideanVector
import kotlin.math.sin

@JvmName("sinDR")
inline fun <reified D:Any, reified R : EuclideanVector<R>> ((D) -> R).sin(): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return sin(#this#(v)); }"""
    return { v: D -> this(v).map(::sin) }.register("sin", glslTemplate, "this" to this@sin)
}

inline fun <reified D:Any> ((D) -> Double).sin(): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return sin(#this#(v)); }"""
    return { v: D -> sin(this(v)) }.register("sin", glslTemplate, "this" to this@sin)

}
