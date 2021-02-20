package com.example.enginetest;

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

public class MyRenderer implements GLSurfaceView.Renderer {
    private static MainActivity master = null;

    private long time = System.currentTimeMillis();

    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(50, 50, 100);

    private Object3D cube = null;

    private int fps = 0;

    private Light sun = null;

    public MyRenderer() {

    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (fb != null) {
            fb.dispose();
        }
        fb = new FrameBuffer(gl, w, h);

        if (master == null) {

            world = new World();
            world.setAmbientLight(20, 20, 20);

            sun = new Light(world);
            sun.setIntensity(250, 250, 250);

            Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(master.getResources().getDrawable(R.drawable.icon)), 64, 64));
            TextureManager.getInstance().addTexture("texture", texture);

            cube = Primitives.getCube(10);
            cube.calcTextureWrapSpherical();
            cube.setTexture("texture");
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
    }
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void onDrawFrame(GL10 gl) {
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
