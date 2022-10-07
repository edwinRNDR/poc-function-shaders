package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

fun Vector2.Companion.mux(x: (Double) -> Double, y: (Double) -> Double): (Vector2) -> Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec2(#x#(v.x), #y#(v.y)); }"""
    return { v: Vector2 -> Vector2(x(v.x), y(v.y)) }.register("mux", glslTemplate, "x" to x, "y" to y)
}

fun Vector3.Companion.mux(x: (Double) -> Double, y: (Double) -> Double, z: (Double) -> Double): (Vector3) -> Vector3 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec3(#x#(v.x), #y#(v.y), #z#(v.z)); }"""
    return { v: Vector3 -> Vector3(x(v.x), y(v.y), z(v.z)) }.register("mux", glslTemplate, "x" to x, "y" to y, "z" to z)
}

fun Vector2.Companion.jointMux(x: (Vector2) -> Double, y: (Vector2) -> Double): (Vector2) -> Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec2(#x#(v), #y#(v)); }"""
    return { v: Vector2 -> Vector2(x(v), y(v)) }.register("mux", glslTemplate, "x" to x, "y" to y)
}

fun Vector3.Companion.jointMux(
    x: (Vector3) -> Double,
    y: (Vector3) -> Double,
    z: (Vector3) -> Double
): (Vector3) -> Vector3 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec3(#x#(v), #y#(v), #z#(v)); }"""
    return { v: Vector3 -> Vector3(x(v), y(v), z(v)) }.register("mux", glslTemplate, "x" to x, "y" to y, "z" to z)
}
