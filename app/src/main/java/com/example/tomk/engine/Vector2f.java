package com.example.tomk.engine;

/**
 * Created by peto on 4/8/18.
 */

public class Vector2f {

    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        this(0.0f, 0.0f);
    }

    public float[] toArray() {
        return new float[] { this.x, this.y };
    }

}
