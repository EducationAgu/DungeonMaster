package com.example.enginetest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class CubeView extends GLSurfaceView {

    private final DiceRoller renderer;

    public CubeView(Context context) {
        super(context);
        initOpenGLView();

        renderer = new DiceRoller((MainActivity) context);
        setRenderer(renderer);
    }

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initOpenGLView();

        renderer = new DiceRoller((MainActivity) context);
        setRenderer(renderer);
    }

    private void initOpenGLView() {
//        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
                // Ensure that we get a 16bit framebuffer. Otherwise, we'll fall
                // back to Pixelflinger on some device (read: Samsung I7500)
                int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
                EGLConfig[] configs = new EGLConfig[1];
                int[] result = new int[1];
                egl.eglChooseConfig(display, attributes, configs, 1, result);
                return configs[0];
            }
        });
    }
}