package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register

@JvmName("bindVector2Double")
fun ((Vector2) -> Double).bind(
    x: ((Vector2) -> Double)? = null,
    y: ((Vector2) -> Double)? = null,
): (Vector2) -> Double {

    val glslTemplate = """float #FUN#(vec2 v) {
        float x = ${if (x != null) "#x#(v)" else "v.x"};
        float y = ${if (y != null) "#y#(v)" else "v.y"};
        return vec2(x, y);
        }"""
    return { v: Vector2 ->
        this(Vector2(x?.invoke(v) ?: v.x, y?.invoke(v) ?: v.y))

    }.apply {
        val metadata = mutableListOf<Pair<String, Any>>()
        if (x != null) {
            metadata.add("x" to x)
        }
        if (y != null) {
            metadata.add("y" to y)
        }
        register("bind", glslTemplate, *metadata.toTypedArray())
    }
}


fun ((Vector3) -> Double).bind(
    x: ((Vector3) -> Double)? = null,
    y: ((Vector3) -> Double)? = null,
    z: ((Vector3) -> Double)? = null
): (Vector3) -> Double {

    val glslTemplate = """float #FUN#(vec3 v) {
        float x =${if (x != null) "#x#(v)" else "v.x"};
        float y =${if (y != null) "#y#(v)" else "v.y"};
        float z =${if (z != null) "#z#(v)" else "v.z"};
        
        }"""
    return { v: Vector3 ->
        this(Vector3(x?.invoke(v) ?: v.x, y?.invoke(v) ?: v.y, x?.invoke(v) ?: v.z))
    }.apply {
        val metadata = mutableListOf<Pair<String, Any>>()
        if (x != null) {
            metadata.add("x" to x)
        }
        if (y != null) {
            metadata.add("y" to y)
        }
        if (z != null) {
            metadata.add("z" to z)
        }
        register("bind", glslTemplate, *metadata.toTypedArray())
    }

}