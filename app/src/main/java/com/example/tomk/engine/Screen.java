package com.example.tomk.engine;

/**
 * Created by peto on 4/29/18.
 */

public final class Screen {

    private Screen() { }

    public static int width;
    public static int height;

    public static Vector2f touch = new Vector2f();
    public static Vector2f deltaTouch = new Vector2f();

    public static double deltaFrameTime;

}
