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
}