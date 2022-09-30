package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import org.openrndr.math.Vector3
import shadergen.register

inline fun <reified R : LinearType<R>> hashBlur2DT(
    noinline f: (Vector3)->R,
    noinline radius: (Vector3) -> Double,
    samples: Int
): (Vector3) -> R {
    val glslTemplate = """#pragma import fastnoise.hash22
        |   #R# #FUN#(vec3 x) {
        |   float radius = #radius#(x);
        |   vec2 circle = vec2(radius);
        |   vec2 r = hash22(x.xy + vec2(x.z));
        |   const float TAU = 6.28318530718;
        |   r = fract(r * vec2(33.3983, 43.4427));
        |   vec2 r2 = sqrt(r.x+.001) * vec2(sin(r.y * TAU), cos(r.y * TAU))*.5;
        |   #R# sum = #f#(vec3(vec2(x.xy + circle * r2),x.z));
        |   for (int i = 0; i < ${samples - 1}; ++i) {
        |       r = fract(r * vec2(33.3983, 43.4427));
        |       r2 = sqrt(r.x+.001) * vec2(sin(r.y * TAU), cos(r.y * TAU))*.5;
        |       sum += #f#(vec3(vec2(x.xy + circle * r2),x.z));           
        |   }
        |   return sum / $samples;
        |}
    """.trimMargin()

    return { x: Vector3 ->
        error("not implemented")
    }.register("hashBlur", glslTemplate, "f" to f, "radius" to radius)
}

@JvmName("hashBlur2DTVector3R")
inline fun <reified R : LinearType<R>> ((Vector3)->R).hashBlur2DT(noinline radius:(Vector3)->Double, samples:Int) = hashBlur2DT(this,radius, samples)


//

@JvmName("hashBlur2DT1")
inline fun  hashBlur2DT(
    noinline f: (Vector3)->Double,
    noinline radius: (Vector3) -> Double,
    samples: Int
): (Vector3) -> Double {
    val glslTemplate = """#pragma import fastnoise.hash22
        |   #R# #FUN#(vec3 x) {
        |   float radius = #radius#(x);
        |   vec2 circle = vec2(radius);
        |   vec2 r = hash22(x.xy + vec2(x.z));
        |   const float TAU = 6.28318530718;
        |   r = fract(r * vec2(33.3983, 43.4427));
        |   vec2 r2 = sqrt(r.x+.001) * vec2(sin(r.y * TAU), cos(r.y * TAU))*.5;
        |   #R# sum = #f#(vec3(vec2(x.xy + circle * r2),x.z));
        |   for (int i = 0; i < ${samples - 1}; ++i) {
        |       r = fract(r * vec2(33.3983, 43.4427));
        |       r2 = sqrt(r.x+.001) * vec2(sin(r.y * TAU), cos(r.y * TAU))*.5;
        |       sum += #f#(vec3(vec2(x.xy + circle * r2),x.z));           
        |   }
        |   return sum / $samples;
        |}
    """.trimMargin()

    return { x: Vector3 ->
        error("not implemented")
    }.register("hashBlur", glslTemplate, "f" to f, "radius" to radius)
}


@JvmName("hashBlur2DTVector3Double")
inline fun ((Vector3)->Double).hashBlur2DT(noinline radius:(Vector3)->Double, samples:Int) = hashBlur2DT(this,radius, samples)