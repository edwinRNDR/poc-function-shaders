package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

//inline operator fun <reified D:Any, reified R: LinearType<R>> ((D) -> R).times(noinline other: (D) -> R): (D) -> R {
//    val glslTemplate = """#R# #FUN#(#D# v) { return #this(v) * #other#(v); }"""
//    return { v: D -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
//}

@JvmName("timesDDouble")
inline operator fun <reified D:Any> ((D) -> Double).times(noinline other: (D) -> Double): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #other#(v); }"""
    return { v: D -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
}

@JvmName("timesDR")
inline operator fun <reified D:Any, reified R:LinearType<R>> ((D) -> R).times(noinline other: (D) -> Double): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #other#(v); }"""
    return { v: D -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
}

@JvmName("timesDR")
inline operator fun <reified D:Any, reified R:LinearType<R>> ((D) -> R).times(scale: Double): (D) -> R {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #scale#; }"""
    return { v: D -> this(v) * scale }.register("times", glslTemplate, "this" to this@times, "scale" to scale)
}


@JvmName("timesDDouble")
inline operator fun <reified D:Any> ((D) -> Double).times(scale: Double): (D) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #scale#; }"""
    return { v: D -> this(v) * scale }.register("times", glslTemplate, "this" to this@times, "scale" to scale)
}


@JvmName("timesDoubleDouble")
operator fun ((Double) -> Double).times(other: (Double) -> Double): (Double) -> Double {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #other#(v); }"""
    return { v: Double -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
}

@JvmName("timesVector2Vector2")
operator fun ((Vector2)->Vector2).times(other: (Vector2) -> Vector2): (Vector2) -> Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return #this#(v) * #other#(v); }"""
    return { v: Vector2 -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
}

@JvmName("timesVector3Vector3")
operator fun ((Vector3)->Vector3).times(other: (Vector3) -> Vector3): (Vector3) -> Vector3 {
    val glslTemplate = """#R# #FeUN#(#D# v) { return #this#(v) * #other#(v); }"""
    return { v: Vector3 -> this(v) * other(v) }.register("times", glslTemplate, "this" to this@times, "other" to other)
}



inline operator fun <reified D:Any> ((D) -> Matrix44).times(noinline other: (D) -> Vector2): (D) -> Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return (#this#(v) * vec4(#other#(v), 0.0, 1.0)).xy; }"""
    return { v: D -> (this(v) * other(v).xy01).div.xy }.register("times", glslTemplate, "this" to this@times, "other" to other)
}

