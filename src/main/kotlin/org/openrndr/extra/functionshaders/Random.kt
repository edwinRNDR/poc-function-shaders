package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.glslRangeType
import shadergen.register
import kotlin.math.floor
import kotlin.math.sin

fun fract(x: Double) : Double {
    return x - floor(x)
}




fun random2D(p : Vector2) : Vector2 {
    var p3 = (Vector3(p.x, p.y, p.x) * Vector3(.1031, .1030, .0973)).map(::fract)
    p3 +=  p3.dot(Vector3(p3.y + 19.19, p3.z + 19.19, p3.x + 19.19))
    return (Vector2(p3.x, p3.x) + Vector2(p3.y, p3.z)) * Vector2(p3.z, p3.y).map(::fract)
}

inline fun <reified D:Any> random2D(noinline position: (D)->Vector2) : (D)->Vector2 {
    val glslTemplate = """#pragma import fastnoise.random_2d_2d
        |#R# #FUN#(#D# x) {
        |   vec2 v = #position#(x);            
        |   return random_2d_2d(v);   
        |}
    """.trimMargin()
    return { x: D ->
        val p = position(x)
        random2D(p)
    }.register("random2D", glslTemplate, "position" to position)
}

val Vector3.yzx: Vector3 get() = Vector3(this.y, this.z, this.x)
val Vector3.zyx: Vector3 get() = Vector3(this.z, this.y, this.x)
val Vector3.xxy: Vector3 get() = Vector3(this.x, this.x, this.y)
val Vector3.yzz: Vector3 get() = Vector3(this.y, this.z, this.z)
operator fun Vector3.plus(right: Double):Vector3 {
    return Vector3(x + right, y + right, z + right)
}

fun random3D(p :Vector3) : Vector3 {
    var p3 = (p * Vector3(.1031, .1030, .0973)).map(::fract)
    p3 += p3.dot(p3.yzx + 19.19)
    return ((p3.xxy + p3.yzz) * p3.zyx).map(::fract)
}

fun random1D(p: Vector3): Double = fract(sin(p.dot(Vector3(70.9898, 78.233, 32.4355))) * 43758.5453123)

/*
val random3D = ShaderPhrase("""float random_3d(vec3 pos) {
        |   return fract(sin(dot(pos.xyz, vec3(70.9898, 78.233, 32.4355))) * 43758.5453123);
        |}
    """.trimMargin())
 */
inline fun <reified D:Any> random3D(noinline position: (D)->Vector3) : (D)->Vector3 {
    val glslTemplate = """#pragma import fastnoise.random_3d_3d
        |#R# #FUN#(#D# x) {
        |   vec3 v = #position#(x);            
        |   return random_3d_3d(v);   
        |}
    """.trimMargin()
    return { x: D ->
        val p = position(x)
        random3D(p)
    }.register("random3D", glslTemplate, "position" to position)
}

inline fun <reified D:Any> random1D(noinline position: (D)->Vector3) : (D)->Double {
    val glslTemplate = """#pragma import fastnoise.random_3d
        |#R# #FUN#(#D# x) {
        |   vec3 v = #position#(x);            
        |   return random_3d(v);   
        |}
    """.trimMargin()
    return { x: D ->
        val p = position(x)
        random1D(p)
    }.register("random1D", glslTemplate, "position" to position, "RI" to glslRangeType(position))
}
