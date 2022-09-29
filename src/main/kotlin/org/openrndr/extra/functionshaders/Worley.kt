package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.min

fun worley2D(): (Vector2) -> Double {
    val glslTemplate = """#pragma import fastnoise.random_2d_2d
        |float #FUN#(vec2 x) {
        |   vec2 n = floor(x);
        |   vec2 f = fract(x);
        |   float dis = 1.0;
        |   for (int j = -1; j <= 1; ++j) {
        |       for (int i = -1; i <= 1; ++i) {
        |           vec2 g = vec2(i, j);
        |           vec2 o = random_2d_2d(n + g);
        |           vec2 delta = g + o - f;
        |           float d = length(delta);
        |           dis = min(dis, d);
        |       }
        |   }
        |   return 1.0 - dis;
        |}""".trimMargin()

    return { x: Vector2 ->
        val n = x.map(::floor)
        val f = x.map { (it - floor(it)).absoluteValue }
        var dis = 1.0
        for (j in -1..1) {
            for (i in -1..1) {
                val g = Vector2(i.toDouble(), j.toDouble())
                val o = random_2d_2d(n + g)
                val delta = g + o - f
                val d = delta.length
                dis = min(dis, d)
            }
        }
        1.0 - dis
    }.register("worley2D", glslTemplate)
}

fun worley2DT(moveFreqMin:Double = 0.0, moveFreqMax: Double = 1.0): (Vector3) -> Double {
    val glslTemplate = """#pragma import fastnoise.random_2d_3d
        |#pragma import fastnoise.random_2d_2d
        |#pragma import fastnoise.smin
        |float #FUN#(vec3 x) {
        |   vec2 n = floor(x.xy);
        |   vec2 f = fract(x.xy);
        |   float dis = 1.0;
        |   for (int j = -1; j <= 1; ++j) {
        |       for (int i = -1; i <= 1; ++i) {
        |           vec2 g = vec2(i, j);
        |           vec2 cellProp = random_2d_2d(floor(n + g));
        |           float cellZ = x.z * (cellProp.x * ${moveFreqMax-moveFreqMin} + $moveFreqMin) + cellProp.y;
        |           float nt = floor(cellZ);
        |           
        |           float ft = fract(cellZ);
        |           //ft = smoothstep(0.0, 1.0, ft);
        |           vec2 o = random_2d_3d(vec3(n + g, (nt))) * (1.0 - ft) + random_2d_3d(vec3(n + g, (nt+1))) * ft;
        |           vec2 delta = (g + o) - f;
        |           float d = length(delta);
        |           dis = min(dis, d);
        |           //dis = smin(dis, d, 0.1);
        |       }
        |   }
        |   return 1.0 - dis;
        |}""".trimMargin()

    return { x: Vector3 ->
        error("not implemented")
    }.register("worley3D", glslTemplate)
}


fun worley3D(): (Int, Vector3) -> Double {
    val glslTemplate = """#pragma import fastnoise.random_3d_3d
        |#pragma import fastnoise.permute_3d
        |#pragma import fastnoise.random_3d_1d
        |float #FUN#(int seed, vec3 y) {
        |   vec3 x = y + random_3d_1d(seed);
        |   vec3 n = floor(x);
        |   vec3 f = fract(x);
        |   float dis = 1.0;
        |   for (int k = -1; k <= 1; ++k) {
        |       for (int j = -1; j <= 1; ++j) {
        |           for (int i = -1; i <= 1; ++i) {
        |               vec3 g = vec3(i, j, k);
        |               vec3 o = random_3d_3d(n + g);
        |               vec3 delta = g + o - f;
        |               float d = length(delta);
        |               dis = min(dis, d);
        |           }
        |       }
        |   }
        |   return 1.0 - dis;
        |}""".trimMargin()

    return { seed: Int, x: Vector3 ->
        val n = x.map(::floor)
        val f = x.map { (it - floor(it)).absoluteValue }
        var dis = 1.0
        for (k in -1..1) {
            for (j in -1..1) {
                for (i in -1..1) {
                    val g = Vector3(i.toDouble(), j.toDouble(), k.toDouble())
                    val o = random_3d_3d(n + g)
                    val delta = g + o - f
                    val d = delta.length
                    dis = min(dis, d)
                }
            }
        }
        1.0 - dis
    }.register("worley3D", glslTemplate)
}
