#version 410 core

in vec2 pass_UV;
in vec4 pass_Color;

out vec4 FragColor;

uniform sampler2D textureSampler;

void main(void) {
    FragColor = pass_Color * vec4(vec3(1.0).xyz, texture(textureSampler, pass_UV).r);
}