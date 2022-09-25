package shadergen

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.color.presets.BISQUE
import org.openrndr.extra.color.presets.CORAL
import org.openrndr.extra.functionshaders.*
import org.openrndr.extra.shaderphrases.preprocess
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2


fun main() = application {
    configure {
        width = 800
        height = 800
    }
    program {
        NoisePhraseBook.register()
        TransformBook.register()


//        val f = mix(
//            mix(ColorRGBa.PINK.constant(), ColorRGBa.BLUE.constant(), Vector2.identity.x.mod(0.25.constant())),
//            mix(ColorRGBa.BLACK.constant(), ColorRGBa.WHITE.constant(), Vector2.identity.y),
//            valueNoise2D().bindSeed(5.constant()).warp(
//                ((Vector2.identity * 21.0.constant()) +
//                        Vector2.jointMux(
//                            x = valueNoise2D().bindSeed(1243.constant()) * 10.0.constant(),
//                            y = valueNoise2D().bindSeed(5435.constant()) * 10.0.constant()
//                        )).warp(Vector2.identity * 10.0.constant())
//            ).abs()
//        ).sum((Vector2.UNIT_X*1.0/640.0).constant(), (1.0/7.0).constant(), -3..3)
//            .sum((Vector2.UNIT_Y*1.0/640.0).constant(), (1.0/7.0).constant(), -3..3)

        val f0 = Matrix44.IDENTITY.constant<Vector2, Matrix44>() * Vector2.identity

        println(f0.glsl())


        val f1 = Vector2.identity.tunnel(Vector2.identity.length() * 10.0.constant(), Double.identity.rotateZ())
        println(f1.glsl())

        println(f1(Vector2.UNIT_X))

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
            fragmentPreamble = """
            """.trimIndent().preprocess() + f.glsl(customFunctionNames = mapOf(f to "demo")).preprocess()
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