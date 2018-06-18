package com.example.tomk.game.gameobjects;

import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.GameObjectUpdateCallback;
import com.example.tomk.engine.objects.Pipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenit on 11. 6. 2018.
 */

public class Line {
    private float speed;
    private Pipe pipe;

    public Line(float x, float y, float widthX, float heightY, float[] color, float speed) {
        pipe = new Pipe(x, y, widthX, heightY, color);
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }
}
