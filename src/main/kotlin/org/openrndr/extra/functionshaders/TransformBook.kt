package org.openrndr.extra.functionshaders

import org.openrndr.extra.shaderphrases.ShaderPhrase
import org.openrndr.extra.shaderphrases.ShaderPhraseBook

object TransformBook : ShaderPhraseBook("transforms") {
    val rotateZ = ShaderPhrase("""mat4 rotate_z(float ar) {
        |   float c = cos(ar);
        |   float s = sin(ar);
        |   return mat4(
        |       vec4(c, s, 0.0, 0.0), 
        |       vec4(-s, c, 0.0, 0.0), 
        |       vec4(0.0, 0.0, 1.0, 0.0), 
        |       vec4(0.0, 0.0, 0.0, 1.0)
        |   );
        |}
    """.trimMargin())
}
