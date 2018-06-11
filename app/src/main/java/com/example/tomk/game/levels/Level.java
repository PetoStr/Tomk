package com.example.tomk.game.levels;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.GameObjectUpdateCallback;
import com.example.tomk.engine.objects.Pipe;
import com.example.tomk.game.Line;

/**
 * Created by peto on 4/29/18.
 */

public abstract class Level {

    protected GameRenderer gameRenderer;

    protected final int pipesCount = 8;

    protected GameObject player;
    protected Line lines[];
    protected GameObject start;
    protected GameObject end;
    protected Line barrier;

    protected int currentPipe;
    protected boolean changingPipes;
    private int directionX;
    protected boolean h;

    public Level(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        init();
    }

    public void restart() {

        gameRenderer.removeGameObject(player);
        for (Line line : lines) {
            gameRenderer.removeGameObject(line.getPipe());
        }
        gameRenderer.removeGameObject(start);
        gameRenderer.removeGameObject(end);
        gameRenderer.removeGameObject(barrier.getPipe());

        init();
    }

    public void init() {
        float[] circlecolor = { 1.0f, 0.0f, 0.0f, 1.0f };
        float[] pipecolor = {0.0f, 0.0f, 1.0f, 1.0f};
        float[] startcirclecolor = {0.0f , 1.0f ,0.0f, 1.0f};
        float[] barriercolor = { 1.0f, 0.0f, 0.0f, 1.0f };
        int a = (int) ((Screen.width * 0.75f) / pipesCount);

        currentPipe = 0;
        changingPipes = false;


        lines = new Line[pipesCount];

        for (int i = 0; i < pipesCount; i++) {
            float speed = i % 2 == 0 ? 0.2f : -0.2f;
            lines[i] = new Line(a * (i - pipesCount / 2) + a / 2, 0.0f, Screen.width / 200.0f, Screen.height, pipecolor, speed);
            gameRenderer.addGameObject(lines[i].getPipe());
        }

        barrier = new Line(a * (2 - pipesCount / 2) + a / 2, 0.0f, Screen.width / 150.0f, Screen.height / 2, barriercolor, 0.5f);
        gameRenderer.addGameObject(barrier.getPipe());



        currentPipe = 0;
        float scale = Screen.height / 20;
        //float size = Screen.height * 2;

        start = new Circle(Screen.width - Screen.width * 1.5f , 0, scale * 1.8f, scale * 1.8f, startcirclecolor);
        gameRenderer.addGameObject(start);

        end = new Circle(Screen.width / 2.0f, 0, scale * 1.8f, scale * 1.8f, startcirclecolor);
        gameRenderer.addGameObject(end);

        player = new Circle(lines[currentPipe].getPipe().getPosition().x, 0.0f, scale, scale, circlecolor);
        gameRenderer.addGameObject(player);

        barrier.getPipe().onUpdate(new GameObjectUpdateCallback() {
            @Override
            public void onUpdate(GameObject gameObject) {

                gameObject.move(0.0f, (float) (barrier.getSpeed() * Screen.deltaFrameTime));

                if (gameObject.getPosition().y < Screen.height / 2  && h)
                    barrier.setSpeed(0.25f);
                else if (gameObject.getPosition().y >= Screen.height / 2 && h)
                    h = false;
                else if (gameObject.getPosition().y > -Screen.height / 2 && !h)
                    barrier.setSpeed(-0.25f);
                else if (gameObject.getPosition().y <= -Screen.height / 2 && !h)
                    h = true;


            }
        });

        player.onUpdate(new GameObjectUpdateCallback() {
            @Override
            public void onUpdate(GameObject gameObject) {

                Line line = lines[currentPipe];
                Pipe pipe = line.getPipe();

                if (!changingPipes) {
                    float x = pipe.getPosition().x;
                    float dy = (float) (Screen.deltaTouch.y + line.getSpeed() * Screen.deltaFrameTime);

                    player.getPosition().x = x;

                    if (Math.abs(player.getPosition().y + dy) <= Screen.height / 2.0f)
                        player.move(0, dy);

                    if (Screen.deltaTouch.x > 15) { // right
                        if (currentPipe < pipesCount - 1) {
                            currentPipe++;
                            changingPipes = true;
                            directionX = 1;
                        }
                    } else if (Screen.deltaTouch.x < -15) { // left
                        if (currentPipe != 0) {
                            currentPipe--;
                            changingPipes = true;
                            directionX = -1;
                        }
                    }
                } else {
                    player.move(2.0f * directionX * (float) Screen.deltaFrameTime, 0.0f);
                    if ((player.getPosition().x >= pipe.getPosition().x && directionX == 1)
                            || (player.getPosition().x <= pipe.getPosition().x && directionX == -1)) {
                        changingPipes = false;
                        player.getPosition().x = pipe.getPosition().x;
                    }
                }
            }

        });
    }

}
