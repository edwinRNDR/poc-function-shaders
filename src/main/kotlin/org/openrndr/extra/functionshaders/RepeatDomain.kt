package org.openrndr.extra.functionshaders

import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.register


inline fun <reified R : Any> deindex( noinline map: (IntVector2)->Vector2, noinline f: (Vector2)-> R, ) : (IntVector2, Vector2) -> R {

    val glslTemplate = """#R# #FUN#(ivec2 ix, vec2 x) { 
        |   vec2 m = #map#(ix) + x;
        |   return #f#(m);
        |}
    """.trimMargin()


    return { ix: IntVector2, x: Vector2 ->
        f(map(ix) + x)
    }.register("deindex", glslTemplate, "map" to map, "f" to f)

}


inline fun <reified R : Any> repeatDomainIndexed(width: Double = 1.0, height: Double = 1.0, noinline f:(IntVector2, Vector2)->R): (Vector2) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) {
        |   vec2 m = vec2(${width}, ${height});
        |   vec2 mx = mod(x - m/2.0, m) + m/2.0;
        |   ivec2 ix = floor(x / m);
        |   return #f#(ix, mx);
        |}
    """.trimMargin()
    return { x: Vector2 ->
        val m = Vector2(width, height)
        val mx = (x.x + m.x / 2.0).mod(m.x) - m.x / 2.0
        val my = (x.y + m.y / 2.0).mod(m.y) - m.y / 2.0
        val i = IntVector2(0, 0)
        f(i, Vector2(mx, my))
    }.register("repeatDomain", glslTemplate, "f" to f)
}

inline fun <reified R : Any> ((Vector2) -> R).repeatDomain(width: Double = 1.0, height: Double = 1.0): (Vector2) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) {
        |   vec2 m = vec2(${width}, ${height});
        |   vec2 mx = mod(x - m/2.0, m) + m/2.0;
        |   return #this#(mx);
        |}
    """.trimMargin()
    return { x: Vector2 ->
        val m = Vector2(width, height)
        val mx = (x.x - m.x / 2.0).mod(m.x) + m.x / 2.0
        val my = (x.y - m.y / 2.0).mod(m.y) + m.y / 2.0
        this(Vector2(mx, my))
    }.register("repeatDomain", glslTemplate, "this" to this@repeatDomain)
}

inline fun <reified R : Any> ((Vector3) -> R).repeatDomain2DT(width: Double = 1.0, height: Double = 1.0): (Vector3) -> R {
    val glslTemplate = """#R# #FUN#(#D# x) {
        |   vec2 m = vec2(${width}, ${height});
        |   vec2 mx = mod(x.xy + m/2.0, m) - m/2.0;
        |   return #this#(vec3(mx, x.z));
        |}
    """.trimMargin()
    return { x: Vector3 ->
        val m = Vector2(width, height)
        val mx = (x.x - m.x / 2.0).mod(m.x) + m.x / 2.0
        val my = (x.y - m.y / 2.0).mod(m.y) + m.y / 2.0
        this(Vector3(mx, my, x.z))
    }.register("repeatDomain", glslTemplate, "this" to this@repeatDomain2DT)
}