package org.openrndr.extra.functionshaders

import org.openrndr.math.EuclideanVector
import org.openrndr.math.LinearType
import shadergen.register

inline fun <reified D : EuclideanVector<D>, reified R : LinearType<R>> sum(
    noinline summand: (D) -> R,
    noinline direction: (D) -> D,
    noinline weighting: (Int) -> Double,
    steps: IntRange,
): (D) -> R {

    val glslTemplate = """#R# #FUN#(#D# x) { 
        |   #R# sum = #R#(0.0);
        |   #D# d = #direction#(x);
        |   for (int i = ${steps.first}; i <= ${steps.endInclusive}; ++i) {
        |       sum += #summand#(x + d * float(i)) * #weighting#(i);
        |   }
        |   return sum;
        |}""".trimMargin()

    return { x: D ->
        val d = direction(x)
        var sum = summand(x) * 0.0;
        for (i in steps) {
            sum += summand(x + d * i.toDouble()) * weighting(i)
        }
        sum
    }.register("sum", glslTemplate, "summand" to summand, "weighting" to weighting, "direction" to direction)
}

@JvmName("sumDR")
inline fun <reified D : EuclideanVector<D>, reified R : LinearType<R>> ((D)->R).sum(
    noinline direction: (D) -> D,
    noinline weighting: (Int) -> Double,
    steps: IntRange,
): (D) -> R  {
    return sum(this, direction, weighting, steps)
}