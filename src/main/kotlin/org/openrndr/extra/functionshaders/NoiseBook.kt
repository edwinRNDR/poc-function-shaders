package org.openrndr.extra.functionshaders

import org.openrndr.extra.shaderphrases.ShaderPhrase
import org.openrndr.extra.shaderphrases.ShaderPhraseBook

object NoisePhraseBook : ShaderPhraseBook("noise") {

    val gradCoord2D = ShaderPhrase("""
        |float grad_coord_2d(int seed, int x, int y, float xd, float yd) {
        |   vec2 GRAD_2D[] = { vec2(-1.0, -1.0), vec2(1.0, -1.0), vec2(-1.0, 1.0),
        |                       vec2(1.0, 1.0), vec2(0.0, -1.0), vec2(-1.0, 0.0),
        |                       vec2(0.0, 1.0), vec2(1.0, 0.0) };
        |
        |   int hash = seed;
        |   const int X_PRIME = 1619;
        |   const int Y_PRIME = 31337;
        |
        |   hash = hash ^ X_PRIME * x;
        |   hash = hash ^ Y_PRIME * y;
        |
        |   hash = hash * hash * hash * 60493;
        |   hash = hash >> 13 ^ hash;
        |   vec2 v1 = GRAD_2D[hash & 7];
        |   return xd * v1.x + yd * v1.y;
        |}
        
    """.trimMargin())

    val phraseSimplex2D = ShaderPhrase("""#pragma import noise.grad_coord_2d
        |float simplex_2d(int seed, vec2 v) {
        |   const float SQRT3 = 1.7320508075688772935274463415059;
        |   const float F2 = 0.5 * (SQRT3 - 1.0);
        |   const float G2 = (3.0 - SQRT3) / 6.0;        
        |   float t = (v.x + v.y) * F2;
        |   int i = int(floor(v.x + t));
        |   int j = int(floor(v.y + t));
        |   t = ((i + j) * G2);
        |   float x0 = v.x - (i - t);
        |   float y0 = v.y - (j - t);

        |   int i1; 
        |   int j1;
        |   if (x0 > y0) {
        |       i1 = 1;
        |       j1 = 0;
        |   } else {
        |       i1 = 0;
        |       j1 = 1;
        |   }
        |
        |   float x1 = (x0 - i1 + G2);
        |   float y1 = (y0 - j1 + G2);
        |   float x2 = (x0 - 1 + 2 * G2);
        |   float y2 = (y0 - 1 + 2 * G2);
        |        
        |   float n0;
        |   float n1;
        |   float n2;
        |
        |   t = 0.5 - x0 * x0 - y0 * y0;
        |   if (t < 0)
        |       n0 = 0.0;
        |   else {
        |       t *= t;
        |       n0 = t * t * grad_coord_2d(seed, i, j, x0, y0);
        |   }
        |
        |   t = 0.5 - x1 * x1 - y1 * y1;
        |   if (t < 0)
        |       n1 = 0.0;
        |   else {
        |       t *= t;
        |       n1 = t * t * grad_coord_2d(seed, i + i1, j + j1, x1, y1);
        |   }
        |
        |   t = 0.5 - x2 * x2 - y2 * y2;
        |   if (t < 0)
        |       n2 = 0.0;
        |   else {
        |       t *= t;
        |       n2 = t * t * grad_coord_2d(seed, i + 1, j + 1, x2, y2);
        |   }
        |
        |   return 50.0 * (n0 + n1 + n2);
        |}
    """.trimMargin())

    val phraseGradCoord3D = ShaderPhrase("""float grad_coord_3d(int seed, int x, int y, int z, float xd, float yd, float zd) {
        |   const vec3 GRAD_3D[] = {  vec3(1.0, 1.0, 0.0), vec3(-1.0, 1.0, 0.0), vec3(1.0, -1.0, 0.0), vec3(-1.0, -1.0, 0.0),
        |       vec3(1.0, 0.0, 1.0), vec3(-1.0, 0.0, 1.0), vec3(1.0, 0.0, -1.0), vec3(-1.0, 0.0, -1.0),
        |       vec3(0.0, 1.0, 1.0), vec3(0.0, -1.0, 1.0), vec3(0.0, 1.0, -1.0), vec3(0.0, -1.0, -1.0),
        |       vec3(1.0, 1.0, 0.0), vec3(0.0, -1.0, 1.0), vec3(-1.0, 1.0, 0.0), vec3(0.0, -1.0, -1.0) };

        |   const int X_PRIME = 1619;
        |   const int Y_PRIME = 31337;
        |   const int Z_PRIME = 6971;
        |   int hash = seed;
        |   hash = hash ^ X_PRIME * x;
        |   hash = hash ^ Y_PRIME * y;
        |   hash = hash ^ Z_PRIME * z;
        |   hash *= hash * hash * 60493;
        |   hash = hash >> 13 ^ hash;
        |   vec3 g = GRAD_3D[hash & 15];
        |   return xd * g.x + yd * g.y + zd * g.z;
        |}
""".trimMargin())


    val phraseSimplex3D = ShaderPhrase("""#pragma import noise.grad_coord_3d
        |float simplex_3d(int seed, vec3 v) {
        |   float F3 = (1.0 / 3.0);
        |   float G3 = (1.0 / 6.0);
        |   float G33 = G3 * 3 - 1;
        |   float t = (v.x + v.y + v.z) / 3.0;
        |   int i = int(floor(v.x + t));
        |   int j = int(floor(v.y + t));
        |   int k = int(floor(v.z + t));
        |   float t2 = (i + j + k) / 6.0;
        |   float x0 = v.x - (i - t2);
        |   float y0 = v.y - (j - t2);
        |   float z0 = v.z - (k - t2);
        |   int i1;
        |   int j1;
        |   int k1;
        |   int i2;
        |   int j2;
        |   int k2;

        |   if (x0 >= y0) {
        |       if(y0 >= z0) { i1 = 1; j1 = 0; k1 = 0; i2 = 1; j2 = 1; k2 = 0; }
        |       else if (x0 >= z0) { i1 = 1; j1 = 0; k1 = 0; i2 = 1; j2 = 0; k2 = 1; }
        |       else  { i1 = 0; j1 = 0; k1 = 1; i2 = 1; j2 = 0; k2 = 1; }
        |   } else {
        |       if (y0 < z0) { i1 = 0; j1 = 0; k1 = 1; i2 = 0; j2 = 1; k2 = 1; }
        |       else if (x0 < z0) { i1 = 0; j1 = 1; k1 = 0; i2 = 0; j2 = 1; k2 = 1; }
        |       else { i1 = 0; j1 = 1; k1 = 0; i2 = 1; j2 = 1; k2 = 0; }
        |   }
        |
        |   float x1 = x0 - i1 + 1.0 / 6.0;
        |   float y1 = y0 - j1 + 1.0 / 6.0;
        |   float z1 = z0 - k1 + 1.0 / 6.0;
        |   float x2 = x0 - i2 + 1.0 / 3.0;
        |   float y2 = y0 - j2 + 1.0 / 3.0;
        |   float z2 = z0 - k2 + 1.0 / 3.0;
        |   float x3 = x0 + G33;
        |   float y3 = y0 + G33;
        |   float z3 = z0 + G33;

        |   float n0; 
        |   {
        |       float lt = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
        |       if (lt < 0) {
        |           n0 = 0.0;
        |       } else {
        |           lt *= lt;
        |           n0 = lt * lt * grad_coord_3d(seed, i, j, k, x0, y0, z0);
        |       }
        |   }
        |   float n1;
        |   {
        |       float lt = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
        |       if (lt < 0) {
        |           n1 = 0.0;
        |       } else {
        |           lt *= lt;
        |           n1 = lt * lt * grad_coord_3d(seed, i + i1, j + j1, k + k1, x1, y1, z1);
        |       }
        |   }
        |   float n2;
        |   {
        |       float lt = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
        |       if (lt < 0) {
        |           n2 = 0.0;
        |       } else {
        |        lt *= lt;
        |        n2 = lt * lt * grad_coord_3d(seed, i + i2, j + j2, k + k2, x2, y2, z2);
        |       }
        |   }
        |   float n3;
        |   {
        |       float lt = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
        |       if (lt < 0)
        |           n3 = 0.0;
        |       else {
        |           lt *= lt;
        |           n3 = lt * lt * grad_coord_3d(seed, i + 1, j + 1, k + 1, x3, y3, z3);
        |       }
        |   }
        |   return 32 * (n0 + n1 + n2 + n3);
        |}
    """.trimMargin())
}