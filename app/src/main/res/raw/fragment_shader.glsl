precision mediump float;

varying vec4 fPos;
varying vec2 f_uv;

uniform vec4 vColor;
uniform vec2 size;
uniform float time;
uniform int objectType;
uniform bool hasTexture;
uniform bool hasColor;
uniform sampler2D texture;

vec4 drawCircle(vec4 color)
{
    vec2 center = vec2(0.0);

    float dst = distance(fPos.xy, center);
    if (dst == 0.0) dst = 0.01;
    float intensity = 1.0 / (dst * 0.8) * (0.9 + abs(sin(time) / 10.0));
    float alpha = max(intensity - 1.0, 0.0) - 1.0 + color.a;

    return vec4(intensity * color.rgb, alpha);
}

vec4 drawPipe(vec4 color)
{
    float x = fPos.x;
    if (x == 0.0) {
        x = 0.001;
    }

    float intensity = abs(1.0 / x);

    return intensity * color;
}

void main(void)
{
    /*vec2 borderSize = vec2(0.7);
    vec2 objectSize = vec2(1.0) - borderSize;

    float distanceField = length(max(abs(fPos.xy) - objectSize, 0.0) / borderSize);

    float alpha = 1.0 - distanceField;

    gl_FragColor = vec4(fPos.xyz / vec3(2.0) + vec3(0.5), alpha);*/

    vec4 color = vec4(0.0);

    if (hasColor) {
        if (objectType == 0) {
            color = drawCircle(vColor);
        } else if (objectType == 1) {
            color = drawPipe(vColor);
        } else {
            color = vColor;
        }
    }

    if (hasTexture) {
        color += texture2D(texture, f_uv);
    }

    gl_FragColor = color;
}
