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
import shadergen.rotateZ
import shadergen.scaleDomain
import shadergen.simplex2D


fun main() = application {
    configure {
        width = 800
        height = 800
    }
    program {
        NoisePhraseBook.register()
        TransformBook.register()

        val f = mix(
            ColorRGBa.BISQUE.constant(),
            ColorRGBa.CORAL.constant(),
            simplex2D().fbm(octaves=2).bindSeed(5).warp(

                (((Vector2.identity * 2.0)
                    .warp(simplex2D().fbm(octaves = 2).vector2Range().bindSeed(952) * 1.0).length() * 2.0).exp() * 10.0).rotateZ() *
                        (Vector2.identity +
                                simplex2D().fbm(octaves = 4, lacunarity = 1.783).vector2Range()
                                    .bindSeed(413)
                                    .scaleDomain(1.0) * 1.0)

            ).unipolar()
        )

        val ss = shadeStyle {
            fragmentPreamble = f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
            println(fragmentPreamble)
            fragmentTransform = """
                x_fill= demo(c_boundsPosition.xy*2.0 - vec2(1.0, 1.0));
            """.trimIndent()
        }
        extend {
            ss.parameter("offset", Vector2(seconds, 0.0))
            drawer.shadeStyle = ss
            drawer.rectangle(drawer.bounds)
        }
    }
}