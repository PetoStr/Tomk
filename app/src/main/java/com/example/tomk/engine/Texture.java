package com.example.tomk.engine;

/**
 * Created by peto on 6/6/18.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import static android.opengl.GLES20.*;

public class Texture {

    public static final String TEXTURE_NAME_PREFIX = "res/drawable/";

    private int[] id = new int[1];

    public Texture(String imagePath, int internalFormat) {
        createTexture(imagePath, internalFormat);
    }

    public Texture(String imagePath) {
        this(imagePath, GL_RGBA);
    }

    public Texture(int internalFormat) {
        this(null, internalFormat);
    }

    public Texture() {
        this(null, GL_RGBA);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id[0]);
    }

    public static void unBind() {
        glBindTexture(GL_TEXTURE0, 0);
    }

    private void createTexture(String imagePath, int internalFormat) {
        glGenTextures(1, id, 0);

        bind();

        int width = 0;
        int height = 0;

        Bitmap bitmap = null;

        if (imagePath == null) {
            width = 100;
            height = 100;
            glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, GL_RGB, GL_FLOAT, null);
        } else {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(TEXTURE_NAME_PREFIX + imagePath);
            try {
                bitmap = BitmapFactory.decodeStream(in);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        unBind();
    }

    public void deleteTexture() {
        glDeleteTextures(1, id, 0);
    }

    public int getId() {
        return id[0];
    }

}
