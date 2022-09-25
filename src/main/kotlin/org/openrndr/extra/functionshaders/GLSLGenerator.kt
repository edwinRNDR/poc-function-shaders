package org.openrndr.extra.functionshaders

import org.openrndr.color.ColorRGBa
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import shadergen.metadata
import kotlin.reflect.KFunction



fun <D:Any, R:Any> ((D)->R).glsl(customFunctionNames: Map<Any, String> = emptyMap()): String {
    var result = StringBuilder()
    val processed = mutableSetOf<Int>()
    val functionNames = mutableMapOf<Int, String>()

    for ((k,v) in customFunctionNames) {
        functionNames[k.hashCode()] = v
    }

    fun recurse(f:Any) {
        val fhash = f.hashCode()
        if (!processed.contains(fhash)) {
            processed.add(fhash)
        } else {
            return
        }

        val metadata = metadata(f)

        val template = metadata["glslTemplate"] ?: error("no metadata for $f")

        if (template != null) {
            val text = template as String
            val re = "#([a-zA-Z_0-9]+)#".toRegex()

            val matches = re.findAll(text).toList()
            var code = text

            for (value in metadata.values) {
                if (value is KFunction<*> || value is Function<*>) {
                    recurse(value)
                }

            }

            for (match in matches) {
                if (match.value != "#FUN#") {
                    val name = "${match.value.drop(1).dropLast(1)}"


                    val reference = metadata[name] ?: error("no metadata for $name $f ${f::class}")
                    val hash = reference.hashCode()

                    when (reference) {
                        is String -> code = code.replace(match.value, reference.toString())
                        is Double -> code = code.replace(match.value, reference.toString())
                        is Int -> code = code.replace(match.value, reference.toString())
                        is KFunction<*>, is Function<*> -> {
                            val functionMeta = metadata(reference) ?: error("no data for $reference")
                            val local = functionMeta["FUN"] ?: error("no name for $f ${f::class} ${reference} ${reference::class}")
                            val functionName = functionNames.getOrPut(hash, { "${local}_${functionNames.size}" })
                            code = code.replace(match.value, functionName)


                        }

                        is Vector2 -> code = code.replace(match.value, "vec2(${reference.x}, ${reference.y})")
                        is Vector3 -> code =
                            code.replace(match.value, "vec3(${reference.x}, ${reference.y}, ${reference.z})")

                        is ColorRGBa -> code =
                            code.replace(match.value, "vec4(${reference.r}, ${reference.g}, ${reference.b}, ${reference.alpha})")

                        is Matrix44 -> {
                            val m = reference
                            code =
                                code.replace(match.value, "mat4(${m.c0r0}, ${m.c0r1}, ${m.c0r2}, ${m.c0r3}, ${m.c1r0}, ${m.c1r1}, ${m.c1r2}, ${m.c1r3}, ${m.c2r0}, ${m.c2r1}, ${m.c2r2}, ${m.c2r3}, ${m.c3r0}, ${m.c3r1}, ${m.c3r2}, ${m.c3r3})")

                        }
                        else -> error("I don't know what to do $reference ${reference::class}")
                    }
                    code = code.replace(match.value, "function_${hash}")
                } else {
                    val hash = f.hashCode()
                    val local = metadata["FUN"] ?: error("no name for $f")
                    val functionName = functionNames.getOrPut(hash, { "${local}_${functionNames.size}" })
                    code = code.replace(match.value, functionName)
                }
            }

            //result.insert(0, code + "\n")
            result.append(code+"\n")
        }




    }
    recurse(this)
    return result.toString()
}