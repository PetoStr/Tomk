package com.example.tomk.game.levels;

import com.example.tomk.engine.GameRenderer;
import com.example.tomk.engine.Screen;
import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.engine.objects.Pipe;
import com.example.tomk.engine.objects.Circle;
import com.example.tomk.engine.objects.GameObjectUpdateCallback;

/**
 * Created by peto on 4/29/18.
 */

public class Level1 extends Level {

    GameObject player;
    GameObject[] pipes;

    private int currentPipe;
    private boolean changingPipes;

    public Level1(GameRenderer gameRenderer) {
        super(gameRenderer);
    }

    @Override
    public void init() {
        //float playerColor[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
        //float playerColor[] = { 0.078431373f, 0.8f, 0.0f, 1.0f };
        float playerColor[] = { 0.0f, 1.0f, 0.0f, 1.0f };
        //float pipeColor[] = { 1.0f, 0.76953125f, 0.22265625f, 1.0f };
        float pipeColor[] = { 1.0f, 0.0f, 0.0f, 1.0f };

        pipes = new Pipe[4];
        int pipesLength = pipes.length;
        int m = Screen.width / pipesLength;
        for (int i = 0; i < pipesLength; i++) {
            pipes[i] = new Pipe(m * (i - 2) + m / 2, 0.0f, Screen.width / 200.0f, Screen.height, pipeColor);
            pipes[i].onUpdate(new GameObjectUpdateCallback() {
                @Override
                public void onUpdate(GameObject gameObject) {
                    gameObject.rotate(1f);
                }
            });
            gameRenderer.addGameObject(pipes[i]);
        }

        currentPipe = 0;
        float scale = Screen.height / 20;
        //float size = Screen.height * 2;

        player = new Circle(pipes[currentPipe].getPosition().x, 0.0f, scale, scale, playerColor);
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
                    }
                } else {
                    player.move(2.0f * (float) Screen.deltaFrameTime, 0.0f);
                    if (player.getPosition().x >= pipes[currentPipe].getPosition().x) {
                        changingPipes = false;
                        player.getPosition().x = pipes[currentPipe].getPosition().x;
                    }
                }
            }
        });
    }

}
