package com.example.tomk.game.gameobjects;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.engine.UpdateCallback;
import com.example.tomk.engine.objects.Image;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zenit on 18. 6. 2018.
 */

public class Animation implements UpdateCallback {

    private GameRenderer gameRenderer;

    private Image[] images;
    private int currentImage;

    private float fps;

    private double startTime;

    private boolean repeating = false;

    public Animation(Image[] images, float fps, GameRenderer gameRenderer) {
        this.images = images;
        this.fps = fps;
        this.gameRenderer = gameRenderer;

        currentImage = 0;
        for (Image image : images) {
            image.setVisible(false);
            gameRenderer.addGameObject(image);
        }
    }

    @Override
    public void onUpdate() {
        if (Screen.getTime() - startTime >= fps) {
            images[currentImage].setVisible(false);

            currentImage++;
            if (currentImage >= images.length) {
                if (!repeating) {
                    deactivate();
                    return;
                }

                currentImage = 0;
            }

            images[currentImage].setVisible(true);

            startTime = Screen.getTime();
        }
    }

    public void activate() {
        images[currentImage].setVisible(true);
        this.startTime = Screen.getTime();
        gameRenderer.onUpdate(this);
    }

    public void deactivate() {
        gameRenderer.removeOnUpdate(this);
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }
}
