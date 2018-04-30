attribute vec3 vPosition;

uniform mat4 MPMatrix;

void main()
{
    gl_Position = MPMatrix * vec4(vPosition, 1);
}