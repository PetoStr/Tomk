package com.example.tomk.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by zenit on 26. 3. 2018.
 */

public class Mesh {

    private int[] ibo = new int[1];
    private int[] vbo = new int[1];

    private int vertexCount;

    public Mesh(float[] coords, short[] indices) {
        this.vertexCount = indices.length;

        createBuffers(coords, indices);
    }

    private void beginMeshRender() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
    }

    private void endMeshRender() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void draw() {
        beginMeshRender();

        glDrawElements(GL_TRIANGLES, this.vertexCount, GL_UNSIGNED_SHORT, 0);

        endMeshRender();
    }

    private void createBuffers(float[] coords, short[] indices) {
        glGenBuffers(1, vbo, 0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(coords.length * (Float.SIZE / 8))
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(coords);
        vertexBuffer.position(0);
        glBufferData(GL_ARRAY_BUFFER, coords.length * (Float.SIZE / 8), vertexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glGenBuffers(1, ibo, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
        ShortBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length * (Short.SIZE / 8))
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indices);
        indexBuffer.position(0);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.length * (Short.SIZE / 8), indexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void clean() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        glDeleteBuffers(1, vbo, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
        glDeleteBuffers(1, ibo, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
