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
import org.openrndr.extra.functionshaders.sdf.fillStroke
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

        val f = fillStroke(ColorRGBa.WHITE.constant(), ColorRGBa.WHITE.constant(),

            min(
                circleSDF(Vector3.identity.xy(), 1.0),
                circleSDF(Vector3.identity.xy() + Vector2(0.5, 0.5).constant(), 1.0)
            )

        )

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo( vec3(c_boundsPosition.xy*5.0 - vec2(2.5,2.5) , p_seconds*0.1));
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