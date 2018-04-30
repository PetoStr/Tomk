package com.example.tomk.engine;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.game.GameGLSurfaceView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Created by zenit on 26. 3. 2018.
 */

public class GameRenderer implements GLSurfaceView.Renderer {

    private ShaderProgram program;

    private int fps;
    private long startTime;

    private final float[] projectionMatrix = new float[16];

    private List<GameObject> gameObjects = new ArrayList<>();

    private GameGLSurfaceView gameGLSurfaceView;

    public GameRenderer(GameGLSurfaceView surfaceView) {
        this.gameGLSurfaceView = surfaceView;

        startTime = System.nanoTime();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int width = gameGLSurfaceView.getWidth();
        int height = gameGLSurfaceView.getHeight();
        updateScreen(width, height);

        program = new ShaderProgram();

        gameGLSurfaceView.surfaceCreated();
    }

    private void updateGameObjects() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        double currentTime = System.nanoTime();

        updateGameObjects();

        glClear(GL_COLOR_BUFFER_BIT);

        program.start();

        for (GameObject gameObject : gameObjects) {
            Mesh mesh = gameObject.getMesh();

            int MPMatrixHandle = glGetUniformLocation(program.getProgramID(), "MPMatrix");

            float[] MPMatrix = new float[16];
            Matrix.multiplyMM(MPMatrix, 0, projectionMatrix, 0, gameObject.getModelMatrix(), 0);
            glUniformMatrix4fv(MPMatrixHandle, 1, false, MPMatrix, 0);

            int col = glGetUniformLocation(program.getProgramID(), "vColor");
            glUniform4fv(col, 1, gameObject.getColor(), 0);

            int size = glGetUniformLocation(program.getProgramID(), "size");
            glUniform2f(size, gameObject.getScale().x, gameObject.getScale().y);

            int position = glGetUniformLocation(program.getProgramID(), "position");
            glUniform2f(size, gameObject.getPosition().x, gameObject.getPosition().y);

            mesh.draw();
        }

        program.stop();

        fps++;
        if (System.nanoTime() - startTime > 1000000000L) {
            System.out.println("FPS: " + String.valueOf(fps));
            fps = 0;
            startTime = System.nanoTime();
        }

        Screen.deltaFrameTime = (System.nanoTime() - currentTime) / 1e6;
    }

    private void updateScreen(int width, int height) {
        Screen.width = width;
        Screen.height = height;

        Matrix.orthoM(projectionMatrix, 0, -width / 2, width / 2, -height / 2, height / 2, -1.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if (height == 0) {
            height = 1;
        }

        glViewport(0, 0, width, height);
        updateScreen(width, height);
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

}
