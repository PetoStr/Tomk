attribute vec3 vPosition;

uniform mat4 PMatrix;
uniform mat4 MMatrix;

void main()
{
    gl_Position = PMatrix * MMatrix * vec4(vPosition, 1);
}