package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

inline fun <reified R: LinearType<R>> ((Vector3)->R).directionalBlur2DT(noinline direction: (Vector3)->Vector2, steps:Int): (Vector3)->R {
    val glslTemplate = """#R# #FUN#(vec3 x) { 
        |    vec2 d = #direction#(x) / $steps;
        |    #R# sum = #this#(x);
        |    vec3 p = x;
        |    for (int i = 1; i < $steps; ++i) {
        |       p.xy += d;
        |       sum += #this#(p);       
        |    }
        |    return sum / $steps;
        |}
    """.trimMargin()
    return { x: Vector3 ->
        var sum = this(x)
        var p = x
        var d = (direction(x) / steps.toDouble()).xy0
        for (i in 1 until steps) {
            p += d
            sum += this(p)
        }
        sum / steps.toDouble()
    }.register("directionalBlur2DT", glslTemplate, "this" to this@directionalBlur2DT, "direction" to direction)
}

@JvmName("directionalBlur2DTVector3Double")
inline fun ((Vector3)->Double).directionalBlur2DT(noinline direction: (Vector3)->Vector2, steps:Int): (Vector3)->Double {
    val glslTemplate = """#R# #FUN#(vec3 x) { 
        |    vec2 d = #direction#(x) / $steps;
        |    #R# sum = #this#(x);
        |    vec3 p = x;
        |    for (int i = 1; i < $steps; ++i) {
        |       p.xy += d;
        |       sum += #this#(p);       
        |    }
        |    return sum / $steps;
        |}
    """.trimMargin()
    return { x: Vector3 ->
        var sum = this(x)
        var p = x
        val d = (direction(x) / steps.toDouble()).xy0
        for (i in 1 until steps) {
            p += d
            sum += this(p)
        }
        sum / steps.toDouble()
    }.register("directionalBlur2DT", glslTemplate, "this" to this@directionalBlur2DT, "direction" to direction)
}

inline fun <reified R: LinearType<R>> ((Vector3)->R).directionalBlurNL2DT(noinline direction: (Vector3)->Vector2, steps:Int): (Vector3)->R {
    val glslTemplate = """#R# #FUN#(vec3 x) { 
        |    vec3 d = vec3(#direction#(x) / $steps, 0.0);
        |    #R# sum = #this#(x);
        |    vec3 p = x;
        |    for (int i = 1; i < $steps; ++i) {
        |       p += d;
        |       d = vec3(#direction#(p), 0.0) / $steps;
        |       sum += #this#(p);       
        |    }
        |    return sum / $steps;
        |}
    """.trimMargin()
    return { x: Vector3 ->
        var sum = this(x)
        var p = x
        var d = (direction(x) / steps.toDouble()).xy0
        for (i in 1 until steps) {
            p += d
            d = direction(p).xy0 / steps.toDouble()
            sum += this(p)
        }
        sum / steps.toDouble()
    }.register("directionalBlur2DT", glslTemplate, "this" to this@directionalBlurNL2DT, "direction" to direction)
}
