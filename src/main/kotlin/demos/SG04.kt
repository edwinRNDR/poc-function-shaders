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


        val noise = Vector3.identity.tunnel(Vector3.identity.xy(), worley2D())

        val f = mix(
            ColorRGBa.BLACK.constant(),
            ColorRGBa.WHITE.constant(),
            noise.warp(Vector3.identity + fastSimplex3D().vector3Range().bindSeed(300).curl()*0.2),
        )

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3(c_boundsPosition.xy*10.0, p_seconds));
            """.trimIndent()
        }
        extend {
            ss.parameter("seconds", seconds)
            drawer.shadeStyle = ss
            drawer.rectangle(drawer.bounds)
        }
    }
}