package demos

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.color.presets.LEMON_CHIFFON
import org.openrndr.extra.color.presets.LIGHT_CORAL
import org.openrndr.extra.color.presets.LIGHT_GREEN
import org.openrndr.extra.color.presets.LIGHT_SKY_BLUE
import org.openrndr.extra.functionshaders.*
import org.openrndr.extra.shaderphrases.preprocess
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

        val noise = worley2DT(-2.0, 2.0)
            .scaleDomain(2.5)
            .warp(
                Vector3.identity +
                        fastSimplex3D().vector3Range().bindSeed(200).scaleDomain(1.0) * Vector3(0.2, 0.2, 0.5).constant()
            )


        val f =
            (noise.abs() + random1D(Vector3.identity)*0.1 + (-0.05).constant() ).colorMap(listOf(
                ColorMap(ColorRGBa.LIGHT_SKY_BLUE.toLinear(), 0.0, 1.0, 1.0),
                ColorMap(ColorRGBa.LIGHT_CORAL.toLinear(), 0.25, 0.5, 1.0),
                ColorMap(ColorRGBa.PINK.toLinear(), 0.5, 0.1, 1.0),
                ColorMap(ColorRGBa.LIGHT_GREEN.toLinear(), 0.75, 0.5, 1.0),
                ColorMap(ColorRGBa.LIGHT_SKY_BLUE.toLinear(), 1.0, 0.35, 1.0),
            ))

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3(c_boundsPosition.xy*3.0, p_seconds*0.1));
                x_fill.rgb = pow(x_fill.rgb, vec3(1.0/2.2));
            """.trimIndent()
        }
        extend {
            ss.parameter("seconds", seconds)
            drawer.shadeStyle = ss
            drawer.rectangle(drawer.bounds)
        }
    }
}