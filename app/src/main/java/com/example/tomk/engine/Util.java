package com.example.tomk.engine;

import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.Pipe;

/**
 * Created by zenit on 11. 6. 2018.
 */

public class Util {

    public boolean intersection(Circle circle, Pipe pipe) {

        circle.getPosition().x = Math.abs(circle.getPosition().x - pipe.getPosition().x);
        circle.getPosition().y = Math.abs(circle.getPosition().y - pipe.getPosition().y);

        if (circle.getPosition().x > (pipe.getSize().x/2 + circle.getSize().x)) { return false; }
        if (circle.getPosition().y > (pipe.getSize().y/2 + circle.getSize().x)) { return false; }

        if (circle.getPosition().x <= (pipe.getSize().x/2)) { return true; }
        if (circle.getPosition().y <= (pipe.getSize().y/2)) { return true; }

        float cornerDistance_sq = (float) ((Math.pow(circle.getPosition().x - pipe.getSize().x/2, 2.0)) +
                        Math.pow((circle.getPosition().y - pipe.getSize().y/2), 2.0));

        return (cornerDistance_sq <= (Math.pow(circle.getSize().x, 2.0)));

    }

}
