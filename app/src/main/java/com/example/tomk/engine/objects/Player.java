package com.example.tomk.engine.objects;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Vector2f;

/**
 * Created by peto on 4/29/18.
 */

public class Player extends GameObject {

    public Player(Mesh mesh, Vector2f position, Vector2f scale, float[] color) {
        super(position, scale, color);
    }

    public Player(float x, float y, float scaleX, float scaleY, float[] color) {
        super(x, y, scaleX, scaleY, color);
    }

    public Player(float x, float y, Vector2f scale, float[] color) {
        super(x, y, scale, color);
    }

    public Player(float x, float y, float[] color) {
        super(x, y, color);
    }

    @Override
    protected void createMesh() {
        /*float coords[] = {
                0.0f, 0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f, // bottom right
        };

        short[] indices = {
                0, 1, 2
        };*/

        int n = 60;
        float coords[] = new float[3 * (n + 1)];

        coords[0] = 0.0f;
        coords[1] = 0.0f;
        coords[2] = 0.0f;
        for (int i = 3; i < coords.length; i += 3) {
            double angle = 2 * Math.PI / n * (i / 3 - 3);
            coords[i] = (float) Math.sin(angle);
            coords[i + 1] = (float) Math.cos(angle);
            coords[i + 2] = 0.0f;
        }

        short indices[] = new short[n * 3];
        for (int i = 0; i < indices.length - 3; i += 3) {
            indices[i] = 0;
            indices[i + 1] = (short) (i / 3 + 1);
            indices[i + 2] = (short) (i / 3 + 2);
        }
        indices[indices.length - 3] = 0;
        indices[indices.length - 2] = (short) n;
        indices[indices.length - 1] = 1;

        super.mesh = new Mesh(coords, indices);
    }

}
