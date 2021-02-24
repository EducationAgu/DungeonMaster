package com.example.enginetest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.threed.jpct.Camera;
import com.threed.jpct.CollisionEvent;
import com.threed.jpct.CollisionListener;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class DiceRoller implements GLSurfaceView.Renderer {

    private FrameBuffer fb = null;
    private MainActivity master;

    private World world = null;
    private DynamicsWorld dynamicsWorld = null;

    private Light sun = null;

    private Object3D cube = null;
    private RigidBody cubePhysics;

    private RGBColor back = new RGBColor(50, 50, 100);

    private long time = System.currentTimeMillis();
    private int fps = 0;

    private float[] rotations;

    public DiceRoller(MainActivity context) {
        master = context;
        rotations = new float[3];
        Random random = new Random();
        rotations[0] = (float)random.nextInt(720_00) / (float)100 - 360;
        rotations[1] = (float)random.nextInt(720_00) / (float)100 - 360;
        rotations[2] = (float)random.nextInt(720_00) / (float)100 - 360;
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

        setUpWorld();

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
        cam.lookAt(cube.getTransformedCenter());
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        update();

        if (master.isPressed) {
            isStarted = true;
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

    private boolean isStarted = true;

    private void update() {

        Transform cubePosition = cubePhysics.getWorldTransform(new Transform());
        SimpleVector cp = cube.getTransformedCenter();
        Logger.log("cube collision position before is at: " + cp.x + " " + cp.y + " " + cp.z);

        if (isStarted) {
            dynamicsWorld.stepSimulation(1.0f / 60.0f);
            setGraphicFromTransform(cubePosition);
        }
    }

    private void physicsWorld() {
        // мир
        BroadphaseInterface broadphase = new DbvtBroadphase(); // аабб предпроверка колайдов
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -100 /* m/s2 */, 0));

        // земля
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 1f /* m */);//заметка: planeConstant влияет на скольжение стоящего предмета
        MotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(
                new Quat4f(0, 0,0, 1), //вротация
                new Vector3f(0, 0, 0), 1.0f))); //позиция
        RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        groundBodyConstructionInfo.restitution = 0.25f;
        RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
        dynamicsWorld.addRigidBody(groundRigidBody);

        // Кубик
        Transform cubeTransform = new Transform(
                new Matrix4f(
                        new Quat4f(rotations[0], rotations[1], rotations[2], 1),
                        new Vector3f(0, 70, 0),
                        1.0f));
        MatrixUtil.getOpenGLSubMatrix(cubeTransform.basis, cube.getRotationMatrix().getDump());
        MotionState cubeMotionState = new DefaultMotionState(cubeTransform);
        Vector3f cubeInertia = new Vector3f(0, 0, 0);
        CollisionShape cubeShape = new BoxShape(new Vector3f(14, 14, 14));
        cubeShape.calculateLocalInertia(2.5f, cubeInertia);
        RigidBodyConstructionInfo cubeConstructionInfo = new RigidBodyConstructionInfo(2.5f, cubeMotionState, cubeShape, cubeInertia);
        cubeConstructionInfo.restitution = 0.5f;
        cubeConstructionInfo.angularDamping = 0.95f;
        cubePhysics = new RigidBody(cubeConstructionInfo);
        cubePhysics.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        dynamicsWorld.addRigidBody(cubePhysics);
    }

    private void beautyWorld() {
        // Игровой мир
        world = new World();
        world.setAmbientLight(20, 20, 20);

        // Освещение
        sun = new Light(world);
        sun.setIntensity(250, 250, 250);

        // Кубик
        cube = Utils.loadObjModel(master.getResources().openRawResource(R.raw.portal_cube), master.getResources().openRawResource(R.raw.model), 2); // загружаю .obj модельку и .mtl текстуру для кубика
        world.addObject(cube);
        SimpleVector cubePos = cube.getTransformedCenter();

        cube.translate(new SimpleVector(-cubePos.x, -cubePos.y, -cubePos.z)); // Позиция кубика
        cube.rotateX(rotations[0]);
        cube.rotateY(rotations[1]);
        cube.rotateZ(rotations[2]);

        // Устанавливаем источник света относительно кубика
        SimpleVector sv = new SimpleVector();
        sv.set(cube.getTransformedCenter());
        sv.y -= 100;
        sv.z -= 100;
        sun.setPosition(sv);

        MemoryHelper.compact();
    }

    private void setUpWorld() {
        beautyWorld();
        physicsWorld();
    }

    private void setGraphicFromTransform(Transform tran) {
        SimpleVector pos = cube.getTransformedCenter();
        cube.translate(tran.origin.x - pos.x,
                (-tran.origin.z) - pos.y,
                ((-tran.origin.y) - pos.z) + 60);

        float[] dump = cube.getRotationMatrix().getDump(); //new float[16];

        Matrix matrixGfx = new Matrix();
        MatrixUtil.getOpenGLSubMatrix(tran.basis, dump);
        matrixGfx.setDump(dump);

        cube.setRotationMatrix(matrixGfx);
    }
}
