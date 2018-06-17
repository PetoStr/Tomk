attribute vec3 vPosition;

uniform mat4 MMatrix;
uniform mat4 PMatrix;

varying vec4 fPos;
varying vec2 f_uv;

void main(void)
{
    fPos = vec4(vPosition, 1.0);
    f_uv = (fPos.xy + 1.0) / 2.0;
    gl_Position = PMatrix * MMatrix * vec4(vPosition, 1.0);
}
