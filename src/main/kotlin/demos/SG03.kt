package demos

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.color.presets.BISQUE
import org.openrndr.extra.color.presets.CORAL
import org.openrndr.extra.functionshaders.*
import org.openrndr.extra.shaderphrases.preprocess
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.*

fun main() = application {
    configure {
        width = 800
        height = 800
    }
    program {
        FastNoisePhraseBook.register()
        TransformBook.register()

        val warp = Vector3.identity + (fastSimplex3D().vector3Range().bindSeed(200) * 0.5).scaleDomain(0.2)
        val noise = (
                fastSimplex3D()
                    .fbm(octaves = 4, lacunarity = 1.32)
                    .bindSeed(300) * 0.5
                ).warp(warp).abs()

        val f = mix(
            ColorRGBa.BLACK.constant(),
            ColorRGBa.WHITE.constant(),
            noise,
        )

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3((c_boundsPosition.xy*2.0 - vec2(1.0, 1.0))*4.0, p_seconds));
            """.trimIndent()
        }
        extend {
            ss.parameter("seconds", seconds)
            drawer.shadeStyle = ss
            drawer.rectangle(drawer.bounds)
        }
    }
}