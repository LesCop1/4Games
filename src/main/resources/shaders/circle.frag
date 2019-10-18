#version 330 core

in vec2 VertexCoord;
in vec2 pass_Texture;

out vec4 FragColor;

uniform float radius;
uniform sampler2D textureSampler;

void main(void) {
    float dist = length(VertexCoord - vec2(0.5, 0.5));

    if (dist >= 0.5) {
        discard;
    }

    float sm = smoothstep(radius, radius-0.01, dist);

    FragColor = vec4(1.0, 1.0, 1.0, sm) * texture(textureSampler, pass_Texture);
}