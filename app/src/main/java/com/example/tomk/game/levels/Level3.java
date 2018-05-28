package com.example.tomk.game.levels;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.GameObjectUpdateCallback;
import com.example.tomk.engine.objects.Pipe;

/**
 * Created by zenit on 28. 5. 2018.
 */

public class Level3 extends Level {


    private final int pipesCount = 8;

    GameObject player;
    GameObject pipes[];
    GameObject start;

    private int currentPipe;
    private boolean changingPipes;
    private int directionX;

    public Level3(GameRenderer gameRenderer) {
        super(gameRenderer);
    }

    @Override
    public void init() {
        float[] circlecolor = { 1.0f, 0.0f, 0.0f, 1.0f };
        float[] pipecolor = {0.0f, 0.0f, 1.0f, 1.0f};
        float[] startcirclecolor = {0.0f , 1.0f ,0.0f, 1.0f};
        int a = (int) ((Screen.width * 0.75f) / pipesCount);

        pipes = new Pipe[pipesCount];

        for (int i = 0; i < pipesCount; i++) {
            pipes[i] = new Pipe(a * (i - pipesCount / 2) + a / 2, 0.0f, Screen.width / 200.0f, Screen.height, pipecolor);
            gameRenderer.addGameObject(pipes[i]);
        }

        currentPipe = 0;
        float scale = Screen.height / 20;
        //float size = Screen.height * 2;

        start = new Circle(Screen.width / 1000.0f, 0, scale * 1.8f, scale * 1.8f, startcirclecolor);
        gameRenderer.addGameObject(start);

        player = new Circle(pipes[currentPipe].getPosition().x, 0.0f, scale, scale, circlecolor);
        gameRenderer.addGameObject(player);



        player.onUpdate(new GameObjectUpdateCallback() {
            @Override
            public void onUpdate(GameObject gameObject) {
                if (!changingPipes) {
                    float x = pipes[currentPipe].getPosition().x;
                    float dy = Screen.deltaTouch.y;

                    player.getPosition().x = x;
                    player.move(0, dy);

                    if (Screen.deltaTouch.x > 15) { // right
                        currentPipe = (currentPipe + 1) % pipes.length;
                        changingPipes = true;
                        directionX = 1;
                    } else if (Screen.deltaTouch.x < -15) { // left
                        if (currentPipe != 0) {
                            currentPipe--;
                            changingPipes = true;
                            directionX = -1;
                        }
                    }
                } else {
                    player.move(2.0f * directionX * (float) Screen.deltaFrameTime, 0.0f);
                    if ((player.getPosition().x >= pipes[currentPipe].getPosition().x && directionX == 1)
                            || (player.getPosition().x <= pipes[currentPipe].getPosition().x && directionX == -1)) {
                        changingPipes = false;
                        player.getPosition().x = pipes[currentPipe].getPosition().x;
                    }
                }
            }

        });
    }
}
