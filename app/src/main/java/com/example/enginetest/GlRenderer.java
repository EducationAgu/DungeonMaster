package com.example.enginetest;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GlRenderer implements GLSurfaceView.Renderer {

    private FrameBuffer fb = null;
    private World world = null;

    private Object3D cube = null;
    private RGBColor back = new RGBColor(50, 50, 100);
    private long time = System.currentTimeMillis();

    private int fps = 0;

    private Light sun = null;

    private MainActivity master = null;

    public GlRenderer() {

    }

    public GlRenderer(MainActivity context) {
        master = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        if (fb != null) {
            fb.dispose();
        }
        fb = new FrameBuffer(gl, width, height);

        world = new World();
        world.setAmbientLight(20, 20, 20);

        sun = new Light(world);
        sun.setIntensity(250, 250, 250);

        cube = Primitives.getCube(10);

        Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(master.getResources().getDrawable(R.drawable.icon)), 64, 64));
        TextureManager.getInstance().addTexture("cube", texture);
        cube.calcTextureWrapSpherical();
        cube.setTexture("cube");
        cube.strip();
        cube.build();


        world.addObject(cube);

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
        cam.lookAt(cube.getTransformedCenter());

        SimpleVector sv = new SimpleVector();
        sv.set(cube.getTransformedCenter());
        sv.y -= 100;
        sv.z -= 100;
        sun.setPosition(sv);
        MemoryHelper.compact();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        if (master.touchTurn != 0) {
            cube.rotateY(master.touchTurn);
            master.touchTurn = 0;
        }

        if (master.touchTurnUp != 0) {
            cube.rotateX(master.touchTurnUp);
            master.touchTurnUp = 0;
        }

        fb.clear(back);
        world.renderScene(fb);
        world.draw(fb);
        fb.display();

        if (System.currentTimeMillis() - time >= 1000) {
            Logger.log(fps + "fps");
            fps = 0;
            time = System.currentTimeMillis();
        }
        fps++;
    }
}
