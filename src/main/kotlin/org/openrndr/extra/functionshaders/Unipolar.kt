package org.openrndr.extra.functionshaders

import org.openrndr.extra.noise.uniform
import org.openrndr.extra.noise.uniformRing
import org.openrndr.math.Vector2
import org.openrndr.math.map
import shadergen.register
import kotlin.math.max
import kotlin.math.min

fun ((Vector2) -> Double).unipolar(
    samples: Int = 1000,
    center: Vector2 = Vector2.ZERO,
    radius: Double = 1.0
): (Vector2) -> Double {

    var min = Double.MAX_VALUE
    var max = Double.MIN_VALUE

    for (i in 0 until samples) {
        val position = center + Vector2.uniformRing(0.0, radius)
        val sample = this(position)

        min = min(min, sample)
        max = max(max, sample)
    }

    val glslTemplate = """float #FUN#(#D# x) { return (#this#(x) - $min) / ($max - $min); }"""

    return { x: Vector2 ->
        this(x).map(min, max, 0.0, 1.0)
    }.register("unipolar", glslTemplate, "this" to this@unipolar)
}

@JvmName("unipolarDoubleDouble")
fun ((Double) -> Double).unipolar(
    samples: Int = 1000,
    center: Double = 0.0,
    radius: Double = 1.0
): (Double) -> Double {

    var min = Double.MAX_VALUE
    var max = Double.MIN_VALUE

    for (i in 0 until samples) {
        val position = center + Double.uniform(-radius, radius)
        val sample = this(position)

        min = min(min, sample)
        max = max(max, sample)
    }

    val glslTemplate = """float #FUN#(#D# x) { return (#this#(x) - $min) / ($max - $min); }"""

    return { x: Double ->
        this(x).map(min, max, 0.0, 1.0)
    }.register("unipolar", glslTemplate, "this" to this@unipolar)
}