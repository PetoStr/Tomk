package com.example.tomk.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.game.levels.Level;
import com.example.tomk.game.levels.Level1;

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
        Level level = new Level1(this.renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX() - getWidth() / 2;
        float y = getHeight() / 2 - e.getY();

        Screen.deltaTouch.x = 0;
        Screen.deltaTouch.y = 0;

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Screen.deltaTouch.x = x - Screen.touch.x;
                Screen.deltaTouch.y = y - Screen.touch.y;
        }

        Screen.touch.x = x;
        Screen.touch.y = y;

        //requestRender();

        return true;
    }

}
