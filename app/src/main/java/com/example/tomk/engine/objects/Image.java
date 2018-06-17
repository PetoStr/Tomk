package com.example.tomk.engine.objects;

import android.util.Log;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Texture;

/**
 * Created by peto on 6/17/18.
 */

public class Image extends GameObject {

    private final String name;

    public Image(float x, float y, float scaleX, float scaleY, String name) {
        super(x, y, scaleX, scaleY, null);

        this.name = name;
        super.setTexture(name);
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

    @Override
    public int getObjectType() {
        return 3;
    }

    public String getName() {
        return name;
    }
}
