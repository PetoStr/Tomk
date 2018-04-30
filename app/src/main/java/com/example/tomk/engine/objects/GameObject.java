package com.example.tomk.engine.objects;

import android.opengl.Matrix;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peto on 4/8/18.
 */

public abstract class GameObject implements Updatable {

    protected Mesh mesh;

    protected Vector2f position;

    protected Vector2f scale;

    protected float[] modelMatrix;

    protected float[] color;

    protected List<Updatable> updatables = new ArrayList<>();

    public GameObject(Vector2f position, Vector2f scale, float[] color) {
        this.position = position;
        this.scale = scale;
        this.color = color;

        modelMatrix = new float[16];
        createModelMatrix();
        createMesh();
    }

    public GameObject(float x, float y, float scaleX, float scaleY, float[] color) {
        this(new Vector2f(x, y), new Vector2f(scaleX, scaleY), color);
    }

    public GameObject(float x, float y, Vector2f scale, float[] color) {
        this(new Vector2f(x, y), scale, color);
    }

    public GameObject(float x, float y, float[] color) {
        this(new Vector2f(x, y), new Vector2f(1.0f, 1.0f), color);
    }

    public void createModelMatrix() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, position.x, position.y, 0);
        Matrix.scaleM(modelMatrix, 0, scale.x, scale.y, 1.0f);
    }

    @Override
    public void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
        createModelMatrix();
    }

    public void move(float dx, float dy) {
        position.x += dx;
        position.y += dy;
    }

    public void onUpdate(Updatable updatable) {
        this.updatables.add(updatable);
    }

    protected abstract void createMesh();

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }
}
