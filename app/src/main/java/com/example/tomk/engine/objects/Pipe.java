package com.example.tomk.engine.objects;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Vector2f;

/**
 * Created by peto on 4/29/18.
 */

public class Pipe extends GameObject {

    public Pipe(Vector2f position, Vector2f scale, float[] color) {
        super(position, scale, color);
    }

    public Pipe(float x, float y, float scaleX, float scaleY, float[] color) {
        super(x, y, scaleX, scaleY, color);
    }

    public Pipe(float x, float y, Vector2f scale, float[] color) {
        super(x, y, scale, color);
    }

    public Pipe(float x, float y, float[] color) {
        super(x, y, color);
    }

    @Override
    public int getObjectType() {
        return 1;
    }

    @Override
    protected void createMesh() {
        float coords[] = {
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        };

        short[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        super.mesh = new Mesh(coords, indices);
    }
}
