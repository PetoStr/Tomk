package com.example.tomk.engine;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES20.*;

/**
 * Created by zenit on 26. 3. 2018.
 */

public class ShaderProgram {

    private final String vertexShaderFileName = "res/raw/vertex_shader.glsl";
    private final String fragmentShaderFileName = "res/raw/fragment_shader.glsl";

    private int programID;

    private int vertexShader;
    private int fragmentShader;

    public ShaderProgram() {

        String vertexShaderCode = readFile(vertexShaderFileName);
        String fragmentShaderCode = readFile(fragmentShaderFileName);

        vertexShader = loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        fragmentShader = loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);

        programID = glCreateProgram();

        glAttachShader(programID, vertexShader);
        glAttachShader(programID, fragmentShader);

        glLinkProgram(programID);
        int[] linkStatus = new int[1];
        glGetProgramiv(programID, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GL_TRUE) {
            System.err.println("Failed to link program: " + glGetProgramInfoLog(programID));
            glDeleteProgram(programID);
            programID = 0;
        }
    }

    private String readFile(String fileName) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    private int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = glCreateShader(type);

        // add the source code to the shader and compile it
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        int compiled[] = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);

        if (compiled[0] == GL_FALSE) {
            System.err.println("Failed to compile shader " + String.valueOf(type) + ": "+ glGetShaderInfoLog(shader));
            glDeleteShader(shader);
            shader = 0;
        }

        return shader;
    }

    public void start() {
        glUseProgram(programID);
    }

    public void stop() {
        glUseProgram(0);
    }

    public int getProgramID() {
        return programID;
    }

}
