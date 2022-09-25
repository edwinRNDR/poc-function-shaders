package shadergen

import org.openrndr.math.EuclideanVector
import kotlin.math.pow
import kotlin.math.sqrt

@JvmName("powDR")
inline fun <reified D : Any, reified R : EuclideanVector<R>> ((D) -> R).pow(noinline right: (D) -> Double): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return pow(#this#(v), #R#(#right#(v))); }"""
    return { v: D -> this(v).map { x -> x.pow(right(v)) } }.register(
        "pow",
        glslTemplate,
        "this" to this@pow,
        "right" to right
    )
}

inline fun <reified D : Any> ((D) -> Double).pow(noinline right: (D)->Double): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return pow(#this#(v), #right#(v)); }"""
    return { v: D -> sqrt(this(v)) }.register("pow", glslTemplate, "this" to this@pow, "right" to right)
}
