#version 410 core

in vec2 pass_UV;
in vec4 pass_Color;

out vec4 FragColor;

uniform sampler2D textureSampler;
uniform vec4 overrideColor = vec4(1.0);

void main(void) {
    FragColor = overrideColor * pass_Color * texture(textureSampler, pass_UV);
}