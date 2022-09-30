package org.openrndr.extra.functionshaders

import org.openrndr.color.ColorRGBa
import org.openrndr.extra.shaderphrases.ShaderPhrase
import org.openrndr.extra.shaderphrases.ShaderPhraseBook
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.math.mod
import shadergen.register
import kotlin.math.abs

object PhysHuePhraseBook:ShaderPhraseBook("physhue") {
    // based on https://www.shadertoy.com/view/Ms33zj
    val physhue = ShaderPhrase("""vec4 phys_hue(float hue, float ratio) {
        |   vec3 r = smoothstep(vec3(0.0), vec3(1.0), abs(mod(hue + vec3(0.0, 1.0, 2.0) * ratio, 1.0) * 2.0 - 1.0));
        |   return vec4(r, 1.0);           
        |}""".trimMargin())

    // based on https://www.shadertoy.com/view/Ms33zj
    val iridescence = ShaderPhrase("""#pragma import physhue.phys_hue
        vec4 iridescence(float angle, float thickness) {
        |   float NxV = cos(angle);
        |   // energy of spectral colors
        |   float lum = 0.05064;
        |   // basic energy of light
        |   float luma = 0.01070;
        |   // tint of the final color
        |   //vec3 tint = vec3(0.7333,0.89804,0.94902);
        |   vec3 tint = vec3(0.49639,0.78252,0.88723);
        |   // interference rate at minimum angle
        |   float interf0 = 2.4;
        |   // phase shift rate at minimum angle
        |   float phase0 = 1.0 / 2.8;
        |   // interference rate at maximum angle
        |   float interf1 = interf0 * 4.0 / 3.0;
        |   // phase shift rate at maximum angle
        |   float phase1 = phase0;
        |   // fresnel (most likely completely wrong)
        |   float f = (1.0 - NxV) * (1.0 - NxV);
        |   float interf = mix(interf0, interf1, f);
        |   float phase = mix(phase0, phase1, f);
        |   float dp = (NxV - 1.0) * 0.5;
        |   // film hue
        |   vec3 hue = 
        |       // fade in higher frequency at the right end
        |       mix(
        |        phys_hue(thickness * interf0 + dp, thickness * phase0).rgb,
    	|   	phys_hue(thickness * interf1 + 0.1 + dp, thickness * phase1).rgb,
        |      f);
        |    
        |   vec3 film = hue * lum + vec3(0.49639,0.78252,0.88723) * luma;
        |    
        |  return vec4(vec3((film * 3.0 + pow(f,12.0))) * tint, 1.0);
        |}""".trimMargin())
}


inline fun <reified D : Any> ((D) -> Vector2).physHue(): (D) -> ColorRGBa {
    PhysHuePhraseBook.register()

    val glslTemplate = """#pragma import physhue.phys_hue
        |vec4 #FUN#(#D# x) {
        |   vec2 hueRatio = #this#(x);
        |   float hue = hueRatio.x;
        |   float ratio = hueRatio.y;
        |   return phys_hue(hue, ratio);   
        |}""".trimMargin()
    return { x: D ->
        val hueRatio = this(x)
        val hue = hueRatio.x
        val ratio = hueRatio.y

        val a = Vector3(0.0, 0.0, 0.0)
        val b = Vector3(1.0, 1.0, 1.0)
        val c = (Vector3(hue, hue, hue) + Vector3(0.0, 1.0, 2.0) * ratio).mod(Vector3(1.0, 1.0, 1.0))
            .map(::abs) * 2.0 - Vector3(1.0, 1.0, 1.0)
        ColorRGBa(c.x, c.y, c.z, 1.0)
    }.register("physHue", glslTemplate, "this" to this@physHue)
}

inline fun <reified D : Any> ((D) -> Vector2).iridescence(): (D) -> ColorRGBa {
    PhysHuePhraseBook.register()
    val glslTemplate = """#pragma import physhue.iridescence
        |vec4 #FUN#(#D# x) {
        |   vec2 angleThickness = #this#(x);
        |   float angle = angleThickness.x * 3.1415926536 * 0.5;
        |   float thickness = angleThickness.y;
        |   return iridescence(angle, thickness);
        |}
        """.trimMargin()
    return { x: D -> error("not implemented")}.register("iridescence", glslTemplate, "this" to this@iridescence)
}