package org.openrndr.extra.functionshaders

import org.openrndr.math.LinearType
import org.openrndr.math.Vector3
import shadergen.register

inline fun <reified R:LinearType<R>> ((Vector3)->R).temporalBlur2DT(steps:Int, dt:Double) : ((Vector3)->R) {
    val glslTemplate = """#R# #FUN#(vec3 x) {
        #R# sum = #this#(x);
        vec3 tx = x - vec3(0.0, 0.0, $dt*(${steps-1.0}));
        for (int i = 0; i < ${steps-1}; ++i) {
            sum += #this#(tx);
            tx.z += $dt;        
        }
        return sum / ${steps};
    }        
    """.trimMargin()
    return { x: Vector3 ->
        var sum = this(x)
        var tx = x - Vector3(0.0, 0.0, dt*(steps-1.0))
        for (i in 0 until steps-1) {
            sum += this(tx)
            tx += Vector3(0.0, 0.0, dt)
        }
        sum / steps.toDouble()
    }.register("temporalBlur2DT", glslTemplate, "this" to this@temporalBlur2DT)
}

@JvmName("temporalBlur2DTVector3Double")
fun ((Vector3)->Double).temporalBlur2DT(steps:Int, dt:Double) : ((Vector3)->Double) {
    val glslTemplate = """#R# #FUN#(vec3 x) {
        #R# sum = #this#(x);
        vec3 tx = x - vec3(0.0, 0.0, $dt*(${steps-1.0}));
        for (int i = 0; i < ${steps-1}; ++i) {
            sum += #this#(tx);
            tx.z += $dt;        
        }
        return sum / ${steps};
    }        
    """.trimMargin()
    return { x: Vector3 ->
        var sum = this(x)
        var tx = x - Vector3(0.0, 0.0, dt*(steps-1.0))
        for (i in 0 until steps-1) {
            sum += this(tx)
            tx += Vector3(0.0, 0.0, dt)
        }
        sum / steps.toDouble()
    }.register("temporalBlur2DT", glslTemplate, "this" to this@temporalBlur2DT)
}