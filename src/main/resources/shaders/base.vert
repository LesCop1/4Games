#version 330 core

layout(location = 0) in vec2 vertex;

out vec2 pass_Texture;

uniform mat4 projection;
uniform vec2 position;

uniform vec2 scale;

void main(void) {
    gl_Position = projection * vec4((vertex * scale) + position, 0.0, 1.0);
    pass_Texture = vertex;
}