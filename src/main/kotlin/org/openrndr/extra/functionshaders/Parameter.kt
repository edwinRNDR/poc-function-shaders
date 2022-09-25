package shadergen

import org.openrndr.math.Vector3
import kotlin.reflect.KProperty0

inline fun <reified D:Any, reified R:Any> KProperty0<R>.parameter(): (D) -> R {
    val glslTemplate = """#ifndef P_${this.name}
        |uniform #D# p_${this.name};
        |#define P_${this.name}
        |#endif
        |#R# #FUN#(#D# x) { return p_${this.name}; }""".trimMargin()
    return { _: D -> this.get() }.register("parameter", glslTemplate, "this" to this@parameter)
}