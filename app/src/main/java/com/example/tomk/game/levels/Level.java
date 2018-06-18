package com.example.tomk.game.levels;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.engine.Util;
import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.GameObjectUpdateCallback;
import com.example.tomk.engine.objects.Image;
import com.example.tomk.engine.objects.Pipe;
import com.example.tomk.game.gameobjects.Animation;
import com.example.tomk.game.gameobjects.Line;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peto on 4/29/18.
 */

public abstract class Level {

    protected GameRenderer gameRenderer;

    protected final int pipesCount = 10;

    protected Circle player;
    protected Line lines[];
    protected Map<Pipe, Line> barrieredLines;

    protected int currentPipe;
    protected boolean changingPipes;
    private int directionX;

    public Level(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        init();
    }

    public void init() {
        float[] circleColor = { 1.0f, 0.0f, 0.0f, 1.0f };
        float[] pipeColor = {0.0f, 0.0f, 1.0f, 1.0f};
        float[] startColor = {0.0f , 1.0f, 0.0f, 1.0f};
        float[] endColor = {1.0f , 1.0f, 0.0f, 1.0f};
        float[] barrierColor = { 1.0f, 0.0f, 0.0f, 1.0f };

        currentPipe = 0;
        changingPipes = false;

        lines = new Line[pipesCount];
        barrieredLines = new HashMap<>();


        /* background image */
        Image img = new Image(0.0f, 0.0f, "bg2.png");
        img.setSizeLinked(true);
        img.setHeight(Screen.getHeight());
        Image img2 = new Image(0.0f, 0.0f, "bg.jpeg");
        img2.setSizeLinked(true);
        img2.setHeight(Screen.getHeight());
        Image[] images = { img, img2 };
        Animation animation = new Animation(images, 1000.0f, gameRenderer);
        animation.activate();




        int a = (int) ((Screen.getWidth() * 0.75f) / pipesCount);

        /* start */
        lines[0] = new Line(a * (0 - pipesCount / 2) + a / 2, 0.0f, Screen.getWidth() / 130.0f, Screen.getHeight(), startColor, 0.0f);
        gameRenderer.addGameObject(lines[0].getPipe());

        /* create lines with barriers */
        for (int i = 1; i < pipesCount - 1; i++) {
            float speed = i % 2 == 0 ? 0.2f : -0.2f;
            lines[i] = new Line(a * (i - pipesCount / 2) + a / 2, 0.0f, Screen.getWidth() / 200.0f, Screen.getHeight(), pipeColor, speed);

            Pipe barrier1 = new Pipe(a * (i - pipesCount / 2) + a / 2, 0.0f, Screen.getWidth() / 150.0f, Screen.getHeight() / 2, barrierColor);
            Pipe barrier2 = new Pipe(a * (i - pipesCount / 2) + a / 2, barrier1.getSize().y + 100.0f, Screen.getWidth() / 150.0f, Screen.getHeight() / 2, barrierColor);

            barrieredLines.put(barrier1, lines[i]);
            barrieredLines.put(barrier2, lines[i]);

            gameRenderer.addGameObject(lines[i].getPipe());
            gameRenderer.addGameObject(barrier1);
            gameRenderer.addGameObject(barrier2);
        }

        /* end */
        lines[pipesCount - 1] = new Line(a * (pipesCount - 1 - pipesCount / 2) + a / 2, 0.0f, Screen.getWidth() / 130.0f, Screen.getHeight(), endColor, 0.0f);
        gameRenderer.addGameObject(lines[pipesCount - 1].getPipe());



        /* player */
        float scale = Screen.getHeight() / 25;
        player = new Circle(lines[currentPipe].getPipe().getPosition().x, 0.0f, scale, scale, circleColor);
        gameRenderer.addGameObject(player);



        /* player update callback */
        player.onUpdate(new GameObjectUpdateCallback() {
            @Override
            public void onUpdate(GameObject gameObject) {
                /* get the player's current line */
                Line line = lines[currentPipe];

                /* get a pipe from the line */
                Pipe pipe = line.getPipe();

                if (!changingPipes) {
                    float x = pipe.getPosition().x;
                    float dy = (float) (Screen.getDeltaTouch().y + line.getSpeed() * Screen.getDeltaFrameTime());

                    player.getPosition().x = x;

                    if (Math.abs(player.getPosition().y + dy) <= Screen.getHeight() / 2.0f)
                        player.move(0, dy);

                    if (Screen.getDeltaTouch().x > 15) { // right
                        if (currentPipe < pipesCount - 1) {
                            currentPipe++;
                            changingPipes = true;
                            directionX = 1;
                        }
                    } else if (Screen.getDeltaTouch().x < -15) { // left
                        if (currentPipe != 0) {
                            currentPipe--;
                            changingPipes = true;
                            directionX = -1;
                        }
                    }
                } else {
                    player.move(1.0f * directionX * (float) Screen.getDeltaFrameTime(), 0.0f);
                    if ((player.getPosition().x >= pipe.getPosition().x && directionX == 1)
                            || (player.getPosition().x <= pipe.getPosition().x && directionX == -1)) {
                        changingPipes = false;
                        player.getPosition().x = pipe.getPosition().x;
                        Screen.getDeltaTouch().x = 0;
                        Screen.getDeltaTouch().y = 0;
                    }
                }
            }

        });



        /* update callback for every barrier */
        for (final Pipe barrier : barrieredLines.keySet()) {
            barrier.onUpdate(new GameObjectUpdateCallback() {
                @Override
                public void onUpdate(GameObject gameObject) {
                    /* barrier */
                    Pipe pipe = (Pipe) gameObject;

                    /* line with which barrier is associated */
                    Line line = barrieredLines.get(barrier);

                    /* move the barrier */
                    pipe.move(0.0f, (float) (line.getSpeed() * Screen.getDeltaFrameTime()));

                    /* check collision with player */
                    if (Util.intersection(player, pipe)) {
                        restartPlayerPosition();
                    }

                    /* return barrier to the beginning if it is no longer visible (goes out of screen) */
                    if (!Util.isVisible(pipe)) {
                        int upDown = line.getSpeed() > 0 ? -1 : 1;
                        pipe.getPosition().y = (Screen.getHeight() / 2 + pipe.getSize().y / 2) * upDown;
                    }


                }
            });
        }










        /* end pipe update callback */
        lines[pipesCount - 1].getPipe().onUpdate(new GameObjectUpdateCallback() {
            @Override
            public void onUpdate(GameObject gameObject) {
                Pipe pipe = (Pipe) gameObject;

                if (Util.intersection(player, pipe)) {
                    /* victory */
                    gameRenderer.removeGameObject(player);
                }
            }
        });
    }

    private void restartPlayerPosition() {
        currentPipe = 0;
        player.getPosition().x = lines[currentPipe].getPipe().getPosition().x;
        player.getPosition().y = 0.0f;
    }

    public void cleanUp() {
        gameRenderer.removeAllGameObjects();
    }

}
