precision mediump float;

varying vec4 fPos;

uniform vec4 vColor;
uniform vec2 size;
uniform float time;

void main(void)
{
    /*vec2 borderSize = vec2(0.7);
    vec2 objectSize = vec2(1.0) - borderSize;

    float distanceField = length(max(abs(fPos.xy) - objectSize, 0.0) / borderSize);

    float alpha = 1.0 - distanceField;

    gl_FragColor = vec4(fPos.xyz / vec3(2.0) + vec3(0.5), alpha);*/

    vec2 center = vec2(0.0);

    float dst = distance(fPos.xy, center);
    if (dst == 0.0) dst = 0.01;
    float intensity = 1.0 / (dst * 1.5);

    float alpha = max(intensity - 1.0, 0.0) - 1.0 + vColor.a;
    gl_FragColor = vec4(intensity * vColor.rgb, alpha);
}