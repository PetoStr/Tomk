package com.example.tomk.engine.objects;

import android.util.Log;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Texture;

/**
 * Created by peto on 6/17/18.
 */

public class Image extends GameObject {

    private final String name;

    private boolean isSizeLinked;

    public Image(float x, float y, float scaleX, float scaleY, String name) {
        super(x, y, scaleX, scaleY, null);

        this.name = name;
        super.setTexture(name);
    }

    public Image(float x, float y, String name) {
        this(x, y, 0, 0, name);
        Texture texture = this.mesh.getTexture();
        super.getSize().x = texture.getWidth();
        super.getSize().y = texture.getHeight();
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

    public boolean isSizeLinked() {
        return isSizeLinked;
    }

    public void setSizeLinked(boolean sizeLinked) {
        isSizeLinked = sizeLinked;
    }

    public void setWidth(float width) {
        super.getSize().x = width;
        if (isSizeLinked) {
            float scale = width / super.mesh.getTexture().getWidth();
            super.getSize().y = super.mesh.getTexture().getHeight() * scale;
        }
    }

    public void setHeight(float height) {
        super.getSize().y = height;
        if (isSizeLinked) {
            float scale = height / super.mesh.getTexture().getHeight();
            super.getSize().x = super.mesh.getTexture().getWidth() * scale;
        }
    }
}
