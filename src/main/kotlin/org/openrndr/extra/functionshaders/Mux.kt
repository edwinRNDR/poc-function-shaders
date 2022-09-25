package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import shadergen.register

fun Vector2.Companion.mux(x: (Double) -> Double, y: (Double) -> Double): (Vector2) -> Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec2(#x#(v.x), #y#(v.y)); }"""
    return { v: Vector2 -> Vector2(x(v.x), y(v.y)) }.register("mux", glslTemplate, "x" to x, "y" to y)
}

fun Vector2.Companion.jointMux(x:(Vector2)->Double, y: (Vector2)->Double) : (Vector2)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# v) { return vec2(#x#(v), #y#(v)); }"""
    return { v: Vector2 -> Vector2(x(v), y(v)) }.register("mux", glslTemplate, "x" to x, "y" to y)
}

