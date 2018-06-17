package com.example.tomk.engine;

/**
 * Created by peto on 4/29/18.
 */

public final class Screen {

    private Screen() { }

    private static int width;
    private static int height;

    private static Vector2f touch = new Vector2f();
    private static Vector2f deltaTouch = new Vector2f();

    private static double deltaFrameTime;

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Screen.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Screen.height = height;
    }

    public static Vector2f getTouch() {
        return touch;
    }

    public static Vector2f getDeltaTouch() {
        return deltaTouch;
    }

    public static double getDeltaFrameTime() {
        return deltaFrameTime;
    }

    public static void setDeltaFrameTime(double deltaFrameTime) {
        Screen.deltaFrameTime = deltaFrameTime;
    }
}
