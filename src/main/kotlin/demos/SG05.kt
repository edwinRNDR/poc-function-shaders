package demos

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
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


        //val noise = (worley3D().bindSeed(0)).warp(Vector3.identity + worley3D().vector3Range().bindSeed(200))
        //val noise = worley3D().bindSeed(300)
        val noise = worley2DT(-1.0, 1.0).scaleDomain(2.5).warp(Vector3.identity + fastSimplex3D().vector3Range().bindSeed(200).scaleDomain(1.0) * Vector3(0.0, 0.0, 0.5).constant())

        val f = mix(
            ColorRGBa.BLACK.constant(),
            ColorRGBa.WHITE.constant(),
            noise
        )

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3(c_boundsPosition.xy*5.0, p_seconds));
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