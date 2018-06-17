package com.example.tomk.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.game.levels.Level;
import com.example.tomk.game.levels.Level1;
import com.example.tomk.game.levels.Level2;

/**
 * Created by zenit on 26. 3. 2018.
 */

public class GameGLSurfaceView extends GLSurfaceView {

    private GameRenderer renderer;

    public GameGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        this.renderer = new GameRenderer(this);

        setRenderer(renderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void surfaceCreated() {
        Level level = new Level2(this.renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX() - getWidth() / 2;
        float y = getHeight() / 2 - e.getY();

        Screen.getDeltaTouch().x = 0;
        Screen.getDeltaTouch().y = 0;

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Screen.getDeltaTouch().x = x - Screen.getTouch().x;
                Screen.getDeltaTouch().y = y - Screen.getTouch().y;
        }

        Screen.getTouch().x = x;
        Screen.getTouch().y = y;

        //requestRender();

        return true;
    }

}
