#version 440 core

in vec2 pass_UV;

out vec4 FragColor;

uniform float radius;
uniform sampler2D textureSampler;

void main(void) {
    float R = 0.5;

    float dist = length(pass_UV - vec2(R));
    if (dist >= R) {
        discard;
    }

    vec4 tex = texture(textureSampler, pass_UV);
    float alpha = tex.a * smoothstep(R, R - 0.01, dist);
    FragColor = vec4(tex.rgb, alpha);
}