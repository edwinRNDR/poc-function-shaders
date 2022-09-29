package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import kotlin.math.floor

fun fract(x: Double) : Double {
    return x - floor(x)
}

fun random_2d_2d(p : Vector2) : Vector2 {
    var p3 = (Vector3(p.x, p.y, p.x) * Vector3(.1031, .1030, .0973)).map(::fract)
    p3 +=   p3.dot(Vector3(p3.y + 19.19, p3.z + 19.19, p3.x + 19.19))
    return (Vector2(p3.x, p3.x) + Vector2(p3.y, p3.z)) * Vector2(p3.z, p3.y).map(::fract)
}

val Vector3.yzx: Vector3 get() = Vector3(this.y, this.z, this.x)
val Vector3.zyx: Vector3 get() = Vector3(this.z, this.y, this.x)
val Vector3.xxy: Vector3 get() = Vector3(this.x, this.x, this.y)
val Vector3.yzz: Vector3 get() = Vector3(this.y, this.z, this.z)
operator fun Vector3.plus(right: Double):Vector3 {
    return Vector3(x + right, y + right, z + right)
}

fun random_3d_3d(p :Vector3) : Vector3 {
    var p3 = (p * Vector3(.1031, .1030, .0973)).map(::fract)
    p3 += p3.dot(p3.yzx + 19.19)
    return ((p3.xxy + p3.yzz) * p3.zyx).map(::fract)
}