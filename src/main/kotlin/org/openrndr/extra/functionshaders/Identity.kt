package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

@JvmName("identityDD")
inline fun <reified D:Any> ((D)->D).identity(): (D) -> D {
    val glslTemplate = """#D# #FUN#(#R# x) { return x; }"""
    return { x: D -> x }.register("identity", glslTemplate)
}

val Double.Companion.identity get() = ::dDDummy.identity()
val Vector2.Companion.identity get() = ::v2V2Dummy.identity()
val Vector3.Companion.identity get() = ::v3V3Dummy.identity()


