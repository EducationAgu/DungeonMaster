package com.dungeonmaster.instruments.dice;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
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
import com.dungeonmaster.R;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class DiceRoller implements GLSurfaceView.Renderer {

    private FrameBuffer fb = null;
    private CubeScene master;

    private World world;
    private DynamicsWorld dynamicsWorld;

    private Light sun;

    private Object3D cube;
    private Object3D ground;

    private RigidBody groundPhysics;
    private RigidBody cubePhysics;

    private RGBColor back = new RGBColor(256, 256, 256);

    private long time = System.currentTimeMillis();
    private int fps = 0;

    private float[] rotations;
    public float xA, yA;
    public boolean isStarted = false;

    public DiceRoller(CubeScene context) {
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
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        update();

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


    private void update() {
        if (isStarted) {
            dynamicsWorld.stepSimulation(1.0f / 60.0f);
            cube = setGraphicFromTransform(cubePhysics.getCenterOfMassTransform(new Transform()), cube);
            ground = setGraphicFromTransform(groundPhysics.getCenterOfMassTransform(new Transform()), ground);
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
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1f, 0), 1f /* m */);//заметка: planeConstant влияет на скольжение стоящего предмета
        MotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(
                new Quat4f(0, 0,0, 1), //вротация
                new Vector3f(0, 0, 0), 1.0f))); //позиция
        RigidBodyConstructionInfo groundBodyConstructionInfo =
                new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        groundBodyConstructionInfo.restitution = 0.5f;
        groundBodyConstructionInfo.angularDamping = 0.5f;
        groundPhysics = new RigidBody(groundBodyConstructionInfo);
        dynamicsWorld.addRigidBody(groundPhysics);

        // Кубик
        Transform cubeTransform = new Transform(
                new Matrix4f(
//                        new Quat4f(0, 0, 0, 1),
                        new Quat4f(rotations[0], rotations[1], rotations[2], 1),
                        new Vector3f(0, 70, 0),
                        1.0f));

        MotionState cubeMotionState = new DefaultMotionState(cubeTransform);
        Vector3f cubeInertia = new Vector3f(0, 0, 0);
        CollisionShape cubeShape = new BoxShape(new Vector3f(15, 15, 15));
        cubeShape.calculateLocalInertia(12.5f, cubeInertia);
        RigidBodyConstructionInfo cubeConstructionInfo = new RigidBodyConstructionInfo(12.5f, cubeMotionState, cubeShape, cubeInertia);
        cubeConstructionInfo.restitution = 0.5f;
        cubeConstructionInfo.angularDamping = 0.99f;
        cubePhysics = new RigidBody(cubeConstructionInfo);
        dynamicsWorld.addRigidBody(cubePhysics);
    }

    private void graphicsWorld() {
        // Игровой мир
        world = new World();
        world.setAmbientLight(20, 20, 20);

        // Освещение
        sun = new Light(world);
        sun.setIntensity(250, 250, 250);

        // Кубик
        cube = Utils.loadObjModel(master.getResources().openRawResource(R.raw.portal_cube), master.getResources().openRawResource(R.raw.model), 2); // загружаю .obj модельку и .mtl текстуру для кубика

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 120);

        cube = setGraphicFromTransform(cubePhysics.getCenterOfMassTransform(new Transform()), cube);

        world.addObject(cube);

        //Земля
        ground = Primitives.getPlane(2, 10);
        ground.setAdditionalColor( new RGBColor( 0, 0, 0 ) );
        ground.setOrigin(new SimpleVector(0, 0, -5));
        Transform t = groundPhysics.getCenterOfMassTransform(new Transform());
        Logger.log(t.origin.x + " " +  t.origin.y + " " + t.origin.z);
        ground = setGraphicFromTransform(groundPhysics.getCenterOfMassTransform(new Transform()), ground);
        world.addObject(ground);

        // Устанавливаем источник света относительно кубика
        SimpleVector sv = new SimpleVector();
        sv.set(cube.getTransformedCenter());
        sv.y -= 100;
        sv.z -= 100;
        sun.setPosition(sv);

        MemoryHelper.compact();
    }

    private void setUpWorld() {
        physicsWorld();
        graphicsWorld();
    }

    private Object3D setGraphicFromTransform(Transform tran, Object3D object) {
        SimpleVector pos = object.getTransformedCenter();
        object.translate(tran.origin.x - pos.x,
                (-tran.origin.z) - pos.y,
                (-tran.origin.y) - pos.z);

        float[] dump = object.getRotationMatrix().getDump();

        Matrix matrixGfx = new Matrix();
        MatrixUtil.getOpenGLSubMatrix(tran.basis, dump);
        matrixGfx.setDump(dump);

        object.setRotationMatrix(matrixGfx);
        return object;
    }
}
