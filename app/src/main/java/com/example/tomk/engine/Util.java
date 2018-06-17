package com.example.tomk.engine;

import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.Pipe;

/**
 * Created by zenit on 11. 6. 2018.
 */

public class Util {

    public static boolean intersection(Circle circle, Pipe pipe) {

        float cx = Math.abs(circle.getPosition().x - pipe.getPosition().x);
        float cy = Math.abs(circle.getPosition().y - pipe.getPosition().y);

        if (cx > (pipe.getSize().x/2 + circle.getSize().x)) { return false; }
        if (cy > (pipe.getSize().y/2 + circle.getSize().x)) { return false; }

        if (cx <= (pipe.getSize().x/2)) { return true; }
        if (cy <= (pipe.getSize().y/2)) { return true; }

        float cornerDistance_sq = (float) ((Math.pow(cx - pipe.getSize().x/2, 2.0)) +
                        Math.pow((cy - pipe.getSize().y/2), 2.0));

        return (cornerDistance_sq <= (Math.pow(circle.getSize().x, 2.0)));

    }

    public static boolean isVisible(GameObject gameObject) {
        float x = gameObject.getPosition().x;
        float y = gameObject.getPosition().y;
        float w = gameObject.getSize().x / 2;
        float h = gameObject.getSize().y / 2;

        return !(x + w > Screen.getWidth()
                || x - w < -Screen.getWidth()
                || y + h > Screen.getHeight()
                || y - h < -Screen.getHeight());
    }

}
