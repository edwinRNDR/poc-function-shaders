package org.openrndr.extra.functionshaders

import org.openrndr.math.Vector2
import shadergen.register
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.min

fun worley2D() : (Vector2) -> Double {

    val glslTemplate = """#pragma import fastnoise.random_2d_2d
        |float #FUN#(vec2 x) {
        |   vec2 n = floor(x);
        |   vec2 f = fract(x);
        |   float dis = 1.0;
        |   for (int j = -1; j <= 1; ++j) {
        |       for (int i = -1; i <= 1; ++i) {
        |           vec2 g = vec2(i, j);
        |           vec2 o = random_2d_2d(n + g);
        |           vec2 delta = g + o - f;
        |           float d = length(delta);
        |           dis = min(dis, d);
        |       }
        |   }
        |   return 1.0 - dis;
        |}""".trimMargin()

    return { x : Vector2 ->
        val n = x.map(::floor)
        val f = x.map { (it - floor(it)).absoluteValue }
        var dis = 1.0
        for (j in -1 .. 1) {
            for (i in -1 .. 1) {
                val g = Vector2(i.toDouble(), j.toDouble())
                val o =  random_2d_2d(n + g)
                val delta = g + o - f
                val d = delta.length
                dis = min(dis, d)
            }
        }
        1.0 - dis
    }.register("worley2D", glslTemplate)
}

/*
float worley(vec2 p){
    vec2 n = floor( p );
    vec2 f = fract( p );

    float dis = 1.0;
    for( int j= -1; j <= 1; j++ )
        for( int i=-1; i <= 1; i++ ) {
                vec2  g = vec2(i,j);
                vec2  o = random2( n + g );
                vec2  delta = g + o - f;
                float d = length(delta);
                dis = min(dis,d);
    }

    return 1.0-dis;
}

float worley(vec3 p){
    vec3 n = floor( p );
    vec3 f = fract( p );

    float dis = 1.0;
    for( int k = -1; k <= 1; k++ )
        for( int j= -1; j <= 1; j++ )
            for( int i=-1; i <= 1; i++ ) {
                vec3  g = vec3(i,j,k);
                vec3  o = random3( n + g );
                vec3  delta = g+o-f;
                float d = length(delta);
                dis = min(dis,d);
    }

    return 1.0-dis;
}
 */