#version 440 core

in vec2 pass_UV;
in vec4 pass_Color;

out vec4 FragColor;

uniform sampler2D textureSampler;

void main(void) {
    FragColor = pass_Color * texture(textureSampler, pass_UV);
}