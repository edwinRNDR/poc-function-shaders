package shadergen

import java.util.WeakHashMap

private val functionData = WeakHashMap<Any, Map<String, Any>>()

fun mapType(name: String): String {
    return when (name) {
        "Vector3" -> "vec3"
        "Vector4" -> "vec4"
        "Vector2" -> "vec2"
        "Double"-> "float"
        "Boolean" -> "bool"
        "Int" -> "int"
        "ColorRGBa" -> "vec4"
        "Matrix44" -> "mat4"
        else -> error("unknown type $name")
    }
}

inline fun <reified R:Any, reified D:Any> glslDomainType(f: (D)->R): String {
    return mapType(D::class.simpleName ?: error("no name"))
}
inline fun <reified R:Any, reified D:Any> glslRangeType(f: (D)->R): String {
    return mapType(R::class.simpleName ?: error("no name"))
}

inline fun <reified R:Any, reified D:Any> glslDomainType(f: (Int, D)->R): String {
    return mapType(D::class.simpleName ?: error("no name"))
}
inline fun <reified R:Any, reified D:Any> glslRangeType(f: (Int, D)->R): String {
    return mapType(R::class.simpleName ?: error("no name"))
}


inline fun <reified R: Any, reified D:Any> ((D)->R).register(name: String, glslTemplate:String, vararg data:Pair<String, Any>) : (D)->R {
    val metadata = mutableMapOf<String, Any>()
    metadata["FUN"] = name
    metadata["R"] = glslRangeType(this)
    metadata["D"] = glslDomainType(this)
    metadata["glslTemplate"] = glslTemplate
    for (d  in data) {
        metadata[d.first] = d.second
    }
    register(this, metadata)
    return this
}

inline fun <reified R: Any, reified D:Any> ((Int, D)->R).register(name: String, glslTemplate:String, vararg data:Pair<String, Any>) : (Int, D)->R {
    val metadata = mutableMapOf<String, Any>()
    metadata["FUN"] = name
    metadata["R"] = glslRangeType(this)
    metadata["D"] = glslDomainType(this)
    metadata["glslTemplate"] = glslTemplate
    for (d  in data) {
        metadata[d.first] = d.second
    }
    register(this, metadata)
    return this
}


fun register(a: Any, data: Map<String, Any>) {
    functionData.put(a, data)
}
fun metadata(a: Any) : Map<String, Any> {
    return functionData.get(a) ?: error("no metadata for ${a::class}")
}