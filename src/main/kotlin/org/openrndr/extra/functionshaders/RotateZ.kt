package shadergen

import org.openrndr.math.Matrix44
import org.openrndr.math.transforms.rotateZ

@JvmName("rotateZ1")
inline fun <reified D : Any> rotateZ(noinline degrees: (D) -> Double): (D) -> Matrix44 {

    val glslTemplate = """#pragma import transforms.rotate_z
        |#R# #FUN#(#D# x) { float degrees = #degrees#(x); return rotate_z(3.14159265 * degrees/180.0); } 
    """.trimMargin()
    return { x: D -> Matrix44.rotateZ(degrees(x)) }.register("rotateZ", glslTemplate, "degrees" to degrees)

}

@JvmName("rotateZDDouble")
inline fun <reified D : Any> ((D) -> Double).rotateZ(): (D) -> Matrix44 {
    return rotateZ(this)
}
