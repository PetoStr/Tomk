precision mediump float;

uniform vec4 vColor;

uniform vec2 size;

void main()
{
    vec2 borderSize = vec2(size / vec2(3.0));
    vec2 objectSize = size - borderSize;

    vec2 uv = gl_FragCoord.xy / size;

    float distanceField = length(max(uv - objectSize, 0.0) / borderSize);

    float alpha = 1.0 - distanceField;

    gl_FragColor = vColor;
}