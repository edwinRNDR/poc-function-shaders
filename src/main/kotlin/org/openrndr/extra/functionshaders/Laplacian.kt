package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import org.openrndr.math.Vector3
import shadergen.register

inline fun <reified R:LinearType<R>> ((Vector3)->R).laplacian2DT(epsilon:Double = 1E-3) : (Vector3)->R {
    val glslTemplate = """#R# #FUN#(vec3 x) {
            #R# conv = 4.0 * #this#(x);
            conv -= #this#(x + vec3($epsilon, 0.0, 0.0));
            conv -= #this#(x - vec3($epsilon, 0.0, 0.0));
            conv -= #this#(x + vec3(0.0, $epsilon, 0.0));
            conv -= #this#(x - vec3(0.0, $epsilon, 0.0));
            return conv;
        }
    """.trimIndent()

    return { x:Vector3 ->
        var conv = this(x) * 4.0
        conv -= this(x + Vector3(epsilon, 0.0, 0.0))
        conv -= this(x - Vector3(epsilon, 0.0, 0.0))
        conv -= this(x + Vector3(0.0, epsilon, 0.0))
        conv -= this(x - Vector3(0.0, epsilon, 0.0))
        conv
    }.register("laplacian2DT", glslTemplate, "this" to this@laplacian2DT)

}

inline fun <reified R:LinearType<R>> ((Vector3)->R).laplacianT(epsilon:Double = 1E-3) : (Vector3)->R {
    val glslTemplate = """#R# #FUN#(vec3 x) {
            #R# conv = 2.0 * #this#(x);
            conv -= #this#(x + vec3(0.0, 0.0, $epsilon));
            conv -= #this#(x - vec3(0.0, 0.0, $epsilon));
            return conv;
        }
    """.trimIndent()

    return { x:Vector3 ->
        var conv = this(x) * 2.0
        conv -= this(x + Vector3(0.0, 0.0, epsilon))
        conv -= this(x - Vector3(0.0, 0.0, epsilon))
        conv
    }.register("laplacianT", glslTemplate, "this" to this@laplacianT)

}