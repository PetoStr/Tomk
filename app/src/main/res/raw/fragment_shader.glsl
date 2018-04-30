precision mediump float;

uniform vec4 vColor;

uniform vec2 position;
uniform vec2 size;

void main()
{
    vec2 borderSize = vec2(size / vec2(3.0));
    vec2 objectSize = size - borderSize;
    vec2 pos = gl_FragCoord.xy - position;

    float distanceField = length(max(abs(pos) - objectSize, 0.0));

    float alpha = 1.0 - distanceField;

    alpha = 1.0;
    gl_FragColor = vec4(vColor.rgb, alpha);
}