package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

@JvmName("curlVector2Vector2")
fun ((Vector2) -> Vector2).curl(epsilon: Double = 0.1): (Vector2) -> Vector2 {
    val glslTemplate = """vec2 #FUN#(vec2 x) {
        |   const float e = $epsilon;
        |   vec2 dx = vec2(e, 0.0);
        |   vec2 dy = vec2(0.0, e);
        |   vec2 x0 = #this#(p - dx);
        |   vec2 x1 = #this#(p + dx);
        |   vec2 y0 = #this#(p - dy);
        |   vec2 y1 = #this#(p +dy);
        |   float sx = x1.y + x0.y;
        |   float sy = y1.x - y0.x;
        |   const float divisor = 1.0 / (2.0 * e);
        |   return normalize(vec2(sx, sy) * divisor);
        |}
    """.trimMargin()
    return { x: Vector2 ->
        val dx = Vector2(epsilon, 0.0)
        val dy = Vector2(0.0, epsilon)
        val px0 = this(x - dx)
        val px1 = this(x + dx)
        val py0 = this(x - dy)
        val py1 = this(x + dy)
        val sx = px1.y - px0.y
        val sy = py1.x - py0.x
        val divisor = 1.0 / (2.0 * epsilon)
        (Vector2(sx, sy) * divisor).normalized
    }.register("curl", glslTemplate, "this" to this@curl)
}


@JvmName("curlVector3Vector3")
fun ((Vector3) -> Vector3).curl(epsilon: Double = 0.1): (Vector3) -> Vector3 {
    val glslTemplate = """vec3 #FUN#(vec3 x) {
        |   const float e = $epsilon;
        |   vec3 dx = vec3(e, 0.0, 0.0);
        |   vec3 dy = vec3(0.0, e, 0.0);
        |   vec3 dz = vec3(0.0, 0.0, e); 
        |   vec3 x0 = #this#(x - dx);
        |   vec3 x1 = #this#(x + dx);
        |   vec3 y0 = #this#(x - dy);
        |   vec3 y1 = #this#(x + dy);
        |   vec3 z0 = #this#(x - dz);
        |   vec3 z1 = #this#(x + dz);
        |   float sx = y1.z - y0.z - z1.y + z0.y;
        |   float sy = z1.x - z0.x - x1.z + x0.z;
        |   float sz = x1.y - x0.y - y1.x + y0.x;
        |   const float divisor = 1.0 / (2.0 * e);
        |   return normalize(vec3(sx, sy, sz) * divisor);
        |}
    """.trimMargin()
    return { x: Vector3 ->
        val dx = Vector3(epsilon, 0.0, 0.0)
        val dy = Vector3(0.0, epsilon, 0.0)
        val dz = Vector3(0.0, 0.0, epsilon)
        val x0 = this(x - dx)
        val x1 = this(x + dx)
        val y0 = this(x - dy)
        val y1 = this(x + dy)
        val z0 = this(x - dz)
        val z1 = this(x + dz)
        val sx = y1.z - y0.z - z1.y + z0.y
        val sy = z1.x - z0.x - x1.z + x0.z
        val sz = x1.y - x0.y - y1.x + y0.x;
        val divisor = 1.0 / (2.0 * epsilon)
        (Vector3(sx, sy, sz) * divisor).normalized
    }.register("curl", glslTemplate, "this" to this@curl)
}