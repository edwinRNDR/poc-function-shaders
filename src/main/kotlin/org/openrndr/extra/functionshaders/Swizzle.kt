package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

inline fun <reified D:Any> ((D)->Double).xx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { float v = #this#(x); return vec2(v, v); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v, v)
    }.register("xx", glslTemplate, "this" to this@xx)
}

inline fun <reified D:Any> ((D)->Double).xxx() : (D)-> Vector3 {
    val glslTemplate = """#R# #FUN#(#D# x) { float v = #this#(x); return vec3(v, v, v); }"""
    return { x: D ->
        val v = this(x)
        Vector3(v, v, v)
    }.register("xx", glslTemplate, "this" to this@xxx)
}

@JvmName("xxDVector2")
inline fun <reified D:Any> ((D)->Vector2).xx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec2 v = #this#(x); return vec2(v.x, v.x); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.x, v.x)
    }.register("xx", glslTemplate, "this" to this@xx)
}

@JvmName("yyDVector2")
inline fun <reified D:Any> ((D)->Vector2).yy() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec2 v = #this#(x); return vec2(v.y, v.y); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.y, v.y)
    }.register("yy", glslTemplate, "this" to this@yy)
}

@JvmName("yxDVector2")
inline fun <reified D:Any> ((D)->Vector2).yx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec2 v = #this#(x); return vec2(v.y, v.x); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.y, v.x)
    }.register("yx", glslTemplate, "this" to this@yx)
}

//

@JvmName("xxDVector3")
inline fun <reified D:Any> ((D)->Vector3).xx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.x, v.x); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.x, v.x)
    }.register("xx", glslTemplate, "this" to this@xx)
}

@JvmName("yyDVector3")
inline fun <reified D:Any> ((D)->Vector3).yy() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.y, v.y); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.y, v.y)
    }.register("yy", glslTemplate, "this" to this@yy)
}

@JvmName("yxDVector3")
inline fun <reified D:Any> ((D)->Vector3).yx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.y, v.x); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.y, v.x)
    }.register("yx", glslTemplate, "this" to this@yx)
}

@JvmName("zzDVector3")
inline fun <reified D:Any> ((D)->Vector3).zz() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.z, v.z); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.z, v.z)
    }.register("zz", glslTemplate, "this" to this@zz)
}

@JvmName("xzDVector3")
inline fun <reified D:Any> ((D)->Vector3).xz() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.x, v.z); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.x, v.z)
    }.register("xz", glslTemplate, "this" to this@xz)
}

@JvmName("zxDVector3")
inline fun <reified D:Any> ((D)->Vector3).zx() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.z, v.x); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.z, v.x)
    }.register("zx", glslTemplate, "this" to this@zx)
}

@JvmName("yzDVector3")
inline fun <reified D:Any> ((D)->Vector3).yz() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.y, v.z); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.y, v.z)
    }.register("xz", glslTemplate, "this" to this@yz)
}

@JvmName("zyDVector3")
inline fun <reified D:Any> ((D)->Vector3).zy() : (D)->Vector2 {
    val glslTemplate = """#R# #FUN#(#D# x) { vec3 v = #this#(x); return vec2(v.z, v.y); }"""
    return { x: D ->
        val v = this(x)
        Vector2(v.z, v.y)
    }.register("zy", glslTemplate, "this" to this@zy)
}
