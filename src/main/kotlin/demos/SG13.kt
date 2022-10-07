package demos

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.camera.Camera2D
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

        val noise = worley2DT().warp(Vector3.identity + Vector3.jointMux(0.0.constant(), 0.0.constant(), fastSimplex3D().bindSeed(200) * 0.5))


        val f = noise.gradient2DTAbsAngle(0.01).iridescence(noise * 0.4 + 0.5.constant()) * (noise.smoothstep(0.0.constant(), 0.5.constant()))


        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3(c_boundsPosition.xy*5.0 + vec2(p_seconds*0.1,0.0), p_seconds*0.1));
                x_fill.rgb = pow(x_fill.rgb*4.0, vec3(1.0/2.2));
            """.trimIndent()
        }
        extend(Camera2D())
        extend {
            ss.parameter("seconds", seconds)
            drawer.shadeStyle = ss
            drawer.rectangle(drawer.bounds)
        }
    }
}