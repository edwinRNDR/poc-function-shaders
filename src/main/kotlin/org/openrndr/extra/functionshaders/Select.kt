package shadergen

import org.openrndr.math.Vector2

val ((Vector2) -> Vector2).x: (Vector2) -> Double
    get() {
        val glslTemplate = "float #FUN#(#D# v) { return #this#(v).x; }"
        return { v: Vector2 -> this(v).x }.register("x", glslTemplate, "this" to this@x)
    }

val ((Vector2) -> Vector2).y: (Vector2) -> Double
    get() {
        val glslTemplate = "float #FUN#(#D# v) { return #this#(v).y; }"
        return { v: Vector2 -> this(v).x }.register("y", glslTemplate, "this" to this@y)
    }
