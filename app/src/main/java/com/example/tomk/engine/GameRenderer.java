package com.example.tomk.engine;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.tomk.engine.objects.GameObject;
import com.example.tomk.game.GameGLSurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Created by zenit on 26. 3. 2018.
 */

public class GameRenderer implements GLSurfaceView.Renderer {

    private ShaderProgram program;

    private Map<String, Integer> uniformLocations;

    private int fps;
    private long startTime;

    private final float[] projectionMatrix = new float[16];

    private List<GameObject> gameObjects = new ArrayList<>();
    private List<UpdateCallback> updateCallbacks = new ArrayList<>();
    private boolean shouldBreak;

    private GameGLSurfaceView gameGLSurfaceView;

    private Camera camera;

    public GameRenderer(GameGLSurfaceView surfaceView) {
        this.gameGLSurfaceView = surfaceView;

        startTime = System.nanoTime();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        //glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);

        int width = gameGLSurfaceView.getWidth();
        int height = gameGLSurfaceView.getHeight();
        updateScreen(width, height);

        camera = Camera.getInstance();

        program = new ShaderProgram();

        uniformLocations = new HashMap<>();
        uniformLocations.put("PMatrix", glGetUniformLocation(program.getProgramID(), "PMatrix"));
        uniformLocations.put("VMatrix", glGetUniformLocation(program.getProgramID(), "VMatrix"));
        uniformLocations.put("MMatrix", glGetUniformLocation(program.getProgramID(), "MMatrix"));
        uniformLocations.put("size", glGetUniformLocation(program.getProgramID(), "size"));
        uniformLocations.put("vColor", glGetUniformLocation(program.getProgramID(), "vColor"));
        uniformLocations.put("time", glGetUniformLocation(program.getProgramID(), "time"));
        uniformLocations.put("objectType", glGetUniformLocation(program.getProgramID(), "objectType"));
        uniformLocations.put("hasColor", glGetUniformLocation(program.getProgramID(), "hasColor"));
        uniformLocations.put("hasTexture", glGetUniformLocation(program.getProgramID(), "hasTexture"));
        uniformLocations.put("texture", glGetUniformLocation(program.getProgramID(), "texture"));

        gameGLSurfaceView.surfaceCreated();
    }

    private void updateGameObjects() {
        for (GameObject gameObject : gameObjects) {
            gameObject.onUpdate(gameObject);

            if (shouldBreak) {
                shouldBreak = false;
                break;
            }
        }
    }

    private void updateListeners() {
        for (UpdateCallback updateCallback : updateCallbacks) {
            updateCallback.onUpdate();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        double currentTime = System.nanoTime();

        updateGameObjects();
        updateListeners();
        camera.createViewMatrix();

        glClear(GL_COLOR_BUFFER_BIT);

        program.start();

        glUniformMatrix4fv(uniformLocations.get("PMatrix"), 1, false, projectionMatrix, 0);
        glUniformMatrix4fv(uniformLocations.get("VMatrix"), 1, false, camera.getViewMatrix(), 0);

        for (GameObject gameObject : gameObjects) {
            if (!gameObject.isVisible()) continue;

            Mesh mesh = gameObject.getMesh();

            glUniformMatrix4fv(uniformLocations.get("MMatrix"), 1, false, gameObject.getModelMatrix(), 0);

            boolean hasColor = gameObject.hasColor();
            glUniform1i(uniformLocations.get("hasColor"), hasColor ? 1 : 0);
            if (hasColor) {
                glUniform4fv(uniformLocations.get("vColor"), 1, gameObject.getColor(), 0);
            }
            glUniform2fv(uniformLocations.get("size"), 1, gameObject.getSize().toArray(), 0);
            glUniform1f(uniformLocations.get("time"), (float) (((System.nanoTime() - startTime) / 1e9) % (Math.PI * 5.0d)));
            glUniform1i(uniformLocations.get("objectType"), gameObject.getObjectType());

            boolean hasTexture = mesh.hasTexture();
            glUniform1i(uniformLocations.get("hasTexture"), hasTexture ? 1 : 0);
            if (hasTexture) {
                glUniform1i(uniformLocations.get("texture"), 0);
            }

            mesh.draw();
        }

        program.stop();

        /*fps++;
        if (System.nanoTime() - startTime > 1000000000L) {
            System.out.println("FPS: " + String.valueOf(fps));
            fps = 0;
            startTime = System.nanoTime();
        }*/

        Screen.setDeltaFrameTime((System.nanoTime() - currentTime) / 1e6);
    }

    private void updateScreen(int width, int height) {
        Screen.setWidth(width);
        Screen.setHeight(height);

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
        shouldBreak = true;
    }

    public void removeAllGameObjects() {
        gameObjects.clear();
        shouldBreak = true;
    }

    public void onUpdate(UpdateCallback updateCallback) {
        this.updateCallbacks.add(updateCallback);
        shouldBreak = true;
    }

    public void removeOnUpdate(UpdateCallback updateCallback) {
        this.updateCallbacks.remove(updateCallback);
        shouldBreak = true;
    }

}
