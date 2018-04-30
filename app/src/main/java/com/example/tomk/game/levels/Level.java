package com.example.tomk.game.levels;

import com.example.tomk.engine.GameRenderer;

/**
 * Created by peto on 4/29/18.
 */

public abstract class Level {

    protected GameRenderer gameRenderer;

    public Level(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        init();
    }

    public abstract void init();

}
