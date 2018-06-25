package com.example.tomk.engine;

import android.opengl.Matrix;

/**
 * Created by zenit on 25. 6. 2018.
 */

public class Camera {

    private static Camera camera = new Camera();

    private Vector2f position = new Vector2f();
    private double angle;

    private float[] viewMatrix = new float[16];

    private Camera() {}

    public static Camera getInstance() {
        return camera;
    }

    public void createViewMatrix() {
        Matrix.setLookAtM(viewMatrix,0,
                position.x, position.y, -0.3f,
                position.x, position.y, 0.0f,
                (float) Math.sin(angle), (float) Math.cos(angle), 0.0f);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(float[] viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

}
