#version 330 core

layout(location = 0) in vec2 vertex;

out vec2 pass_Texture;
out vec2 VertexCoord;

layout(std140) uniform VS_IN {
    float partial;
    mat4 projection;
    mat4 model;
};

uniform vec2 scale;

void main(void) {
    gl_Position = projection * model * vec4(vertex, 0.0, 1.0);
    VertexCoord = vertex;
    pass_Texture = vertex;
}