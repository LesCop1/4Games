#version 410 core

in vec2 pass_UV;
in vec4 pass_Color;

out vec4 FragColor;

uniform sampler2D textureSampler;
uniform vec4 override_Color = vec4(1.0);

void main(void) {
    FragColor = override_Color * pass_Color * vec4(vec3(1.0).xyz, texture(textureSampler, pass_UV).r);
}