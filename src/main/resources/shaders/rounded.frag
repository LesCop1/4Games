#version 410 core

in vec2 pass_UV;
in vec4 pass_Color;

out vec4 FragColor;

uniform sampler2D textureSampler;
uniform vec4 override_Color = vec4(1.0);

uniform float uiWidth;
uniform float uiHeight;
uniform float radius;

void main(void) {
    vec4 tex = texture(textureSampler, pass_UV);

    float smoothness = 0.3;
    float alpha = tex.a;

    if (radius > 0.0) {
        vec2 pixelPos = pass_UV * vec2(uiWidth, uiHeight);
        float xMax = uiWidth - radius;
        float yMax = uiHeight - radius;
        if (pixelPos.x < radius && pixelPos.y < radius) {
            alpha *= 1.0 - smoothstep(radius - smoothness, radius + smoothness, length(pixelPos - vec2(radius, radius)));
        } else if (pixelPos.x < radius && pixelPos.y > yMax) {
            alpha *= 1.0 - smoothstep(radius - smoothness, radius + smoothness, length(pixelPos - vec2(radius, yMax)));
        } else if (pixelPos.x > xMax && pixelPos.y < radius) {
            alpha *= 1.0 - smoothstep(radius - smoothness, radius + smoothness, length(pixelPos - vec2(xMax, radius)));
        } else if (pixelPos.x > xMax && pixelPos.y > yMax) {
            alpha *= 1.0 - smoothstep(radius - smoothness, radius + smoothness, length(pixelPos - vec2(xMax, yMax)));
        }
    }

    FragColor = override_Color * pass_Color * vec4(tex.rgb, alpha);
}