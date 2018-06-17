package com.example.tomk.engine.objects;

import android.opengl.Matrix;

import com.example.tomk.engine.Mesh;
import com.example.tomk.engine.Texture;
import com.example.tomk.engine.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peto on 4/8/18.
 */

public abstract class GameObject implements GameObjectUpdateCallback {

    protected Mesh mesh;

    protected Vector2f position;

    protected Vector2f size;

    protected float[] modelMatrix;

    protected float[] color;

    private float angle;

    protected List<GameObjectUpdateCallback> gameObjectUpdateCallbacks = new ArrayList<>();

    public GameObject(Vector2f position, Vector2f size, float[] color) {
        this.position = position;
        this.size = size;
        this.color = color;

        modelMatrix = new float[16];
        createModelMatrix();
        createMesh();
    }

    public GameObject(float x, float y, float scaleX, float scaleY, float[] color) {
        this(new Vector2f(x, y), new Vector2f(scaleX, scaleY), color);
    }

    public GameObject(float x, float y, Vector2f size, float[] color) {
        this(new Vector2f(x, y), size, color);
    }

    public GameObject(float x, float y, float[] color) {
        this(new Vector2f(x, y), new Vector2f(1.0f, 1.0f), color);
    }

    public void createModelMatrix() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, position.x, position.y, 0);
        Matrix.rotateM(modelMatrix, 0, -angle, 0, 0, 1);
        Matrix.scaleM(modelMatrix, 0, size.x, size.y, 1.0f);
    }

    @Override
    public void onUpdate(GameObject gameObject) {
        for (GameObjectUpdateCallback gameObjectUpdateCallback : gameObjectUpdateCallbacks) {
            gameObjectUpdateCallback.onUpdate(gameObject);
        }
        createModelMatrix();
    }

    public void move(float dx, float dy) {
        position.x += dx;
        position.y += dy;
    }

    public void rotate(float da) {
        angle = (angle + da) % 360;
    }

    public void onUpdate(GameObjectUpdateCallback gameObjectUpdateCallback) {
        this.gameObjectUpdateCallbacks.add(gameObjectUpdateCallback);
    }

    protected abstract void createMesh();
    public abstract int getObjectType();

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

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean hasColor() {
        return color != null;
    }

    public void setTexture(String path) {
        this.mesh.setTexture(new Texture(path));
    }

}
