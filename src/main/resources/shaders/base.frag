#version 330 core

in vec2 pass_Texture;

out vec4 FragColor;

uniform sampler2D textureSampler;

void main(void) {
    FragColor = texture(textureSampler, pass_Texture);
}