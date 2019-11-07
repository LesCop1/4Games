#version 410 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 uv;
layout(location = 2) in vec4 color;

out vec2 pass_UV;
out vec4 pass_Color;

uniform mat4 projection = mat4(1.0);
uniform mat4 model = mat4(1.0);

void main(void) {
    gl_Position = projection * model * vec4(position, 0.0, 1.0);
    pass_UV = uv;
    pass_Color = color;
}