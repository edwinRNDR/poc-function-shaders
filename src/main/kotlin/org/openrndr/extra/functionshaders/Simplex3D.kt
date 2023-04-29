package shadergen

import org.openrndr.extra.noise.simplex
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3

fun simplex3D(): (Int, Vector3) -> Double {
    val glslTemplate = """#pragma import noise.simplex_3d
        |#R# #FUN#(int seed, #D# x) { return simplex_3d(seed, x); }
    """.trimMargin()
    return { seed: Int, x: Vector3 -> simplex(seed, x) }.register("simplex3D", glslTemplate)
}

fun fastSimplex3D(): (Int, Vector3) -> Double {
    val glslTemplate = """#pragma import fastnoise.simplex_3d
        |#pragma import fastnoise.permute_3d
        |#R# #FUN#(int seed, #D1# x) { return simplex_3d(x + permute_3d(vec3(seed,-seed, seed))); }
    """.trimMargin()
    return { seed: Int, x: Vector3 -> error("not implemented") }.register("simplex3D", glslTemplate)
}
