package org.openrndr.extra.functionshaders

import org.openrndr.extra.shaderphrases.ShaderPhrase
import org.openrndr.extra.shaderphrases.ShaderPhraseBook

object FastNoisePhraseBook : ShaderPhraseBook("fastnoise") {


    val hash2D2D = ShaderPhrase("""vec2 hash22(vec2 p) {
        |   const float HASHSCALE =443.8975;
        |   vec3 p3 = fract(vec3(p.xyx) * HASHSCALE);
	    |   p3 += dot(p3, p3.yzx+19.19);
	    |   return fract(vec2((p3.x + p3.y)*p3.z, (p3.x+p3.z)*p3.y));
        |}""".trimMargin())

    val smin = ShaderPhrase("""float smin(float a, float b, float k) {
    |   float h = max(k-abs(a-b), 0.0 )/k;
    |   return min( a, b ) - h*h*k*(1.0/4.0);
    |}""".trimMargin())

    val mod289 = ShaderPhrase("""float mod289(float x) {
        |   return x - floor(x * (1.0 / 289.0)) * 289.0;
        |}
    """.trimMargin())

    val mod2892D = ShaderPhrase("""vec2 mod289_2d(vec2 x) {
        |   return x - floor(x * (1.0 / 289.0)) * 289.0;
        |}
    """.trimMargin())

    val mod2893D = ShaderPhrase("""vec3 mod289_3d(vec3 x) {
        |   return x - floor(x * (1.0 / 289.0)) * 289.0;
        |}
    """.trimMargin())

    val mod2894D = ShaderPhrase("""vec4 mod289_4d(vec4 x) {
        |   return x - floor(x * (1.0 / 289.0)) * 289.0;
        |}
    """.trimMargin())

    val permute = ShaderPhrase("""#pragma import fastnoise.mod289
        |float permute(float x) {
        |   return mod289(((x * 34.0) + 1.0)*x);
        |}
    """.trimMargin())

    val permute2D = ShaderPhrase("""#pragma import fastnoise.mod289_2d
        |vec2 permute_2d(vec2 x) {
        |   return mod289_2d(((x * 34.0) + 1.0)*x);
        |}
    """.trimMargin())

    val permute3D = ShaderPhrase("""#pragma import fastnoise.mod289_3d
        |vec3 permute_3d(vec3 x) {
        |   return mod289_3d(((x * 34.0) + 1.0)*x);
        |}
    """.trimMargin())

    val permute4D = ShaderPhrase("""#pragma import fastnoise.mod289_4d
        |vec4 permute_4d(vec4 x) {
        |   return mod289_4d(((x * 34.0) + 1.0)*x);
        |}
    """.trimMargin())

    val taylorInvSqrt = ShaderPhrase("""float taylor_inv_sqrt(float r) {
        |   return 1.79284291400159 - 0.85373472095314 * r;
        |}
    """.trimMargin())

    val random1D = ShaderPhrase("""float random_1d(float x) { 
        |   return fract(sin(x) * 43758.5453);
        |}
    """.trimMargin())

    val random2D = ShaderPhrase("""float random_2d(vec2 st) { 
        |   return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453);
        |}
    """.trimMargin())

    val random3D = ShaderPhrase("""float random_3d(vec3 pos) { 
        |   return fract(sin(dot(pos.xyz, vec3(70.9898, 78.233, 32.4355))) * 43758.5453123);
        |}
    """.trimMargin())

    val random4D = ShaderPhrase("""float random_4d(vec4 pos) { 
        |   float dot_product = dot(pos, vec4(12.9898,78.233,45.164,94.673));
        |   return fract(sin(dot_product) * 43758.5453);
        |}
    """.trimMargin())

    val random2D1D = ShaderPhrase("""vec2 random_2d_1d(float p) {
        |   const vec3 RANDOM_SCALE3 =vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(vec3(p) * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yzx + 19.19);
        |   return fract((p3.xx+p3.yz)*p3.zy);
        |}    
    """.trimMargin())

    val random2D2D = ShaderPhrase("""vec2 random_2d_2d(vec2 p) {
        |   const vec3 RANDOM_SCALE3 =vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(p.xyx * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yzx + 19.19);
        |   return fract((p3.xx+p3.yz)*p3.zy);
        |}    
    """.trimMargin())

    val random2D3D = ShaderPhrase("""vec2 random_2d_3d(vec3 p) {
        |   const vec3 RANDOM_SCALE3 = vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(p * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yzx + 19.19);
        |   return fract((p3.xx+p3.yz)*p3.zy);
        |}    
    """.trimMargin())

    val random3D1D = ShaderPhrase("""vec3 random_3d_1d(float p) {
        |   const vec3 RANDOM_SCALE3 = vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(vec3(p) * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yxz + 19.19);
        |   return fract((p3.xxy+p3.yzz)*p3.zyx); 
        |}    
    """.trimMargin())

    val random3D2D = ShaderPhrase("""vec3 random_3d_2d(vec2 p) {
        |   const vec3 RANDOM_SCALE3 = vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(vec3(p.xyx) * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yxz + 19.19);
        |   return fract((p3.xxy+p3.yzz)*p3.zyx); 
        |}    
    """.trimMargin())

    val random3D3D = ShaderPhrase("""vec3 random_3d_3d(vec3 p) {
        |   const vec3 RANDOM_SCALE3 = vec3(.1031, .1030, .0973);
        |   vec3 p3 = fract(p * RANDOM_SCALE3);
        |   p3 += dot(p3, p3.yxz + 19.19);
        |   return fract((p3.xxy+p3.yzz)*p3.zyx); 
        |}    
    """.trimMargin())

    val taylorInvSqrt4D = ShaderPhrase("""vec4 taylor_inv_sqrt_4d(vec4 r) {
        |   return 1.79284291400159 - 0.85373472095314 * r;
        |}
    """.trimMargin())

    val simplex2D = ShaderPhrase("""
        |#pragma import fastnoise.permute_2d
        |#pragma import fastnoise.permute_3d
        |float simplex_2d(vec2 v) {
        |   const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0
        |                0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)
        |                -0.577350269189626,  // -1.0 + 2.0 * C.x
        |                0.024390243902439); // 1.0 / 41.0
        |   // First corner
        |   vec2 i  = floor(v + dot(v, C.yy));
        |   vec2 x0 = v -   i + dot(i, C.xx);
        |
        |   // Other corners
        |   vec2 i1;
        |   //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0
        |   //i1.y = 1.0 - i1.x;
        |   i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);
        |   // x0 = x0 - 0.0 + 0.0 * C.xx ;
        |   // x1 = x0 - i1 + 1.0 * C.xx ;
        |   // x2 = x0 - 1.0 + 2.0 * C.xx ;
        |   vec4 x12 = x0.xyxy + C.xxzz;
        |   x12.xy -= i1;
        |   // Permutations
        |   i = mod289_2d(i); // Avoid truncation effects in permutation
        |   vec3 p = permute_3d( permute_3d( i.y + vec3(0.0, i1.y, 1.0 ))
        |   + i.x + vec3(0.0, i1.x, 1.0 ));
        |
        |   vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);
        |   m = m*m ;
        |   m = m*m ;
        |
        |   // Gradients: 41 points uniformly over a line, mapped onto a diamond.
        |   // The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)
        |
        |   vec3 x = 2.0 * fract(p * C.www) - 1.0;
        |   vec3 h = abs(x) - 0.5;
        |   vec3 ox = floor(x + 0.5);
        |   vec3 a0 = x - ox;
        |
        |   // Normalise gradients implicitly by scaling m
        |   // Approximation of: m *= inversesqrt( a0*a0 + h*h );
        |   m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );
        |
        |   // Compute final noise value at P
        |   vec3 g;
        |   g.x  = a0.x  * x0.x  + h.x  * x0.y;
        |   g.yz = a0.yz * x12.xz + h.yz * x12.yw;
        |   return 130.0 * dot(m, g);
        |}
    """.trimMargin())


    val simplex3D = ShaderPhrase("""#pragma import fastnoise.permute_4d
        |#pragma import fastnoise.taylor_inv_sqrt_4d
        |#pragma import fastnoise.mod289_3d
        |float simplex_3d(vec3 v) {
        |  const vec2  C = vec2(1.0/6.0, 1.0/3.0) ;
    const vec4  D = vec4(0.0, 0.5, 1.0, 2.0);

    // First corner
    vec3 i  = floor(v + dot(v, C.yyy) );
    vec3 x0 =   v - i + dot(i, C.xxx) ;

    // Other corners
    vec3 g = step(x0.yzx, x0.xyz);
    vec3 l = 1.0 - g;
    vec3 i1 = min( g.xyz, l.zxy );
    vec3 i2 = max( g.xyz, l.zxy );

    //   x0 = x0 - 0.0 + 0.0 * C.xxx;
    //   x1 = x0 - i1  + 1.0 * C.xxx;
    //   x2 = x0 - i2  + 2.0 * C.xxx;
    //   x3 = x0 - 1.0 + 3.0 * C.xxx;
    vec3 x1 = x0 - i1 + C.xxx;
    vec3 x2 = x0 - i2 + C.yyy; // 2.0*C.x = 1/3 = C.y
    vec3 x3 = x0 - D.yyy;      // -1.0+3.0*C.x = -0.5 = -D.y

    // Permutations
    i = mod289_3d(i);
    vec4 p = permute_4d( permute_4d( permute_4d(
                i.z + vec4(0.0, i1.z, i2.z, 1.0 ))
            + i.y + vec4(0.0, i1.y, i2.y, 1.0 ))
            + i.x + vec4(0.0, i1.x, i2.x, 1.0 ));

    // Gradients: 7x7 points over a square, mapped onto an octahedron.
    // The ring size 17*17 = 289 is close to a multiple of 49 (49*6 = 294)
    float n_ = 0.142857142857; // 1.0/7.0
    vec3  ns = n_ * D.wyz - D.xzx;

    vec4 j = p - 49.0 * floor(p * ns.z * ns.z);  //  mod(p,7*7)

    vec4 x_ = floor(j * ns.z);
    vec4 y_ = floor(j - 7.0 * x_ );    // mod(j,N)

    vec4 x = x_ *ns.x + ns.yyyy;
    vec4 y = y_ *ns.x + ns.yyyy;
    vec4 h = 1.0 - abs(x) - abs(y);

    vec4 b0 = vec4( x.xy, y.xy );
    vec4 b1 = vec4( x.zw, y.zw );

    //vec4 s0 = vec4(lessThan(b0,0.0))*2.0 - 1.0;
    //vec4 s1 = vec4(lessThan(b1,0.0))*2.0 - 1.0;
    vec4 s0 = floor(b0)*2.0 + 1.0;
    vec4 s1 = floor(b1)*2.0 + 1.0;
    vec4 sh = -step(h, vec4(0.0));

    vec4 a0 = b0.xzyw + s0.xzyw*sh.xxyy ;
    vec4 a1 = b1.xzyw + s1.xzyw*sh.zzww ;

    vec3 p0 = vec3(a0.xy,h.x);
    vec3 p1 = vec3(a0.zw,h.y);
    vec3 p2 = vec3(a1.xy,h.z);
    vec3 p3 = vec3(a1.zw,h.w);

    //Normalise gradients
    vec4 norm = taylor_inv_sqrt_4d(vec4(dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3)));
    p0 *= norm.x;
    p1 *= norm.y;
    p2 *= norm.z;
    p3 *= norm.w;

    // Mix final noise value
    vec4 m = max(0.6 - vec4(dot(x0,x0), dot(x1,x1), dot(x2,x2), dot(x3,x3)), 0.0);
    m = m * m;
    return 42.0 * dot( m*m, vec4( dot(p0,x0), dot(p1,x1),
                                dot(p2,x2), dot(p3,x3) ) );
        
    |}
    """.trimMargin())
}