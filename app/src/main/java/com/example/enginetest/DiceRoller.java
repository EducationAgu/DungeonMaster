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
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class DiceRoller implements GLSurfaceView.Renderer, CollisionListener {

    private FrameBuffer fb = null;
    private MainActivity master = null;

    private World world = null;
    private DynamicsWorld dynamicsWorld = null;

    private Light sun = null;

    private final Transform DEFAULT_CUBE_TRANSFORM = new Transform(
            new Matrix4f(
                    new Quat4f(0,0,0,1),
                    new Vector3f(0, 0, 0),
                    1.0f));
    private Object3D cube = null;
    private RigidBody cubePhysics;
    private Set<RigidBody> cubesPhysics = new HashSet<RigidBody>();

    private boolean applyForce = false;
    private boolean resetCube = false;

    private RGBColor back = new RGBColor(50, 50, 100);

    private long time = System.currentTimeMillis();
    private int fps = 0;

    public DiceRoller(MainActivity context) {
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

    @Override
    public void collision(CollisionEvent collisionEvent) {

    }

    @Override
    public boolean requiresPolygonIDs() {
        return false;
    }

    private final float SPEED = 2.0f;
    private final float MAXSPEED = 3.0f;
    private final SimpleVector ellipsoid = new SimpleVector(15, 18, 32);
    private boolean isStarted = true;

    /*Метод отвечает за перемещение кубика (и объектов) из jPCT */
    private void update() {
        Transform cubePosition;
        SimpleVector center = cube.getTransformedCenter();
        Transform transformOfCube = new Transform(
                new Matrix4f(
                        new Quat4f(0,0, 0, 1),
                        new Vector3f(center.x, center.z, center.y ),
                        1.0f));

        transformOfCube = setTransformFromGraphic(transformOfCube);
        cubePosition = cubePhysics.getWorldTransform(transformOfCube);

//        Logger.log("cube collision position before is at: " + cubePosition.origin.x + " " + cubePosition.origin.y + " " + cubePosition.origin.z);

        if (isStarted) {
            dynamicsWorld.stepSimulation(1.0f / 60.0f);
            setGraphicFromTransform(cubePosition);
//            cube.translate(cubePosition.x, cubePosition.z, cubePosition.y);
        }

        Logger.log("cube collision position after is at: " + cubePosition.origin.x + " " + cubePosition.origin.y + " " + cubePosition.origin.z);

        center = cube.getTransformedCenter();
        Logger.log("cube position is at: " + transformOfCube.origin.x + " " + transformOfCube.origin.y + " " + transformOfCube.origin.z);

        Transform groundTrans = ground.getWorldTransform(cubePosition);
        Logger.log("ground position is at: " + groundTrans.origin.x + " " + groundTrans.origin.y + " " + groundTrans.origin.z);
    }


    private void physicsWorld() {
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher collisionDispatcher = new CollisionDispatcher(collisionConfiguration);

        Vector3f worldAabbMin = new Vector3f(-10,-10,-10);
        Vector3f worldAabbMax = new Vector3f(10,10,10);
        AxisSweep3 overlappingPairCache = new AxisSweep3(worldAabbMin, worldAabbMax);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();

        dynamicsWorld = new DiscreteDynamicsWorld(collisionDispatcher, overlappingPairCache, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -10f, 0));
        dynamicsWorld.getDispatchInfo().allowedCcdPenetration = 0f;

        // Коллизия земли
        CollisionShape groundShape = new BoxShape(new Vector3f(100.f, 50.f, 100.f));
        Transform groundTransform = new Transform();
        groundTransform.setIdentity();
        groundTransform.origin.set(new Vector3f(0.0f, -10.0f, 0.0f));

        MotionState groundMotionState = new DefaultMotionState(groundTransform);
        RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(
                0,
                groundMotionState,
                groundShape,
                new Vector3f(0, 0 ,0));

        RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
        dynamicsWorld.addRigidBody(groundRigidBody);

        groundTransformStatic = groundTransform;
        ground = groundRigidBody;

        // Коллизия для кубика
        SimpleVector simpleVector = cube.getOrigin();
        simpleVector = cube.getTransformedCenter();
        Vector3f physicsVector = new Vector3f(simpleVector.x, simpleVector.z, simpleVector.y);
        CollisionShape cubeShape = new BoxShape(physicsVector);

        Transform cubeTransform = new Transform(
                new Matrix4f(
                        new Quat4f(0,0,0,1),
                        new Vector3f(simpleVector.x, -simpleVector.z, -simpleVector.y),
                        1.0f));
        cubeTransform = setTransformFromGraphic(cubeTransform);

        MotionState cubeMotionState = new DefaultMotionState(cubeTransform);
        Vector3f cubeInertia = new Vector3f(0, 0, 0);
        cubeShape.calculateLocalInertia(2.5f, cubeInertia);

        RigidBodyConstructionInfo cubeConstructionInfo = new RigidBodyConstructionInfo(0.1f, cubeMotionState, cubeShape, cubeInertia);
        cubeConstructionInfo.restitution = 0.5f;
        cubeConstructionInfo.friction = 0.95f;
//        cubeConstructionInfo.angularDamping = 0.95f;
        cubePhysics = new RigidBody(cubeConstructionInfo);
        cubePhysics.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        dynamicsWorld.addRigidBody(cubePhysics);

        this.cubesPhysics.add(cubePhysics); // добавляю его в сет, на случай если кубиков на экране будет несколько
    }

    private RigidBody ground;
    private Transform groundTransformStatic;

    private void beautyWorld() {
        // Игровой мир
        world = new World();
        world.setAmbientLight(20, 20, 20);

        // Освещение
        sun = new Light(world);
        sun.setIntensity(250, 250, 250);

        // Кубик
        cube = Utils.loadObjModel(master.getResources().openRawResource(R.raw.portal_cube), master.getResources().openRawResource(R.raw.model), 2); // загружаю .obj модельку и .mtl текстуру для кубика
//        cube.setCollisionMode(Object3D.COLLISION_CHECK_SELF); // Коллизия jPCT движка. Потом может уберу совсем
//        cube.addCollisionListener(this);
        world.addObject(cube);
        SimpleVector cubePos = cube.getTransformedCenter();

        cube.translate(new SimpleVector(-cubePos.x, -cubePos.y, -cubePos.z)); // Позиция кубика

        cubePos = cube.getTransformedCenter();

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

    private Transform setTransformFromGraphic(Transform tran) {
        SimpleVector p = cube.getTransformedCenter();
        tran.origin.set(p.x, -p.y, -p.z); // not sure if translation or position

        Matrix matrixGfx = cube.getRotationMatrix();
        //matrixGfx.rotateX((float)Math.PI);
        MatrixUtil.getOpenGLSubMatrix(tran.basis, matrixGfx.getDump());
        return tran;
    }

    private void setGraphicFromTransform(Transform tran) {
        SimpleVector pos = cube.getTransformedCenter();
        cube.translate(tran.origin.x - pos.x,
                (-tran.origin.z) - pos.y,
                (-tran.origin.y) - pos.z);

        float[] ma = new float[4];
        float[] dump = cube.getRotationMatrix().getDump(); //new float[16];
        Matrix matrixGfx = new Matrix();
        MatrixUtil.getOpenGLSubMatrix(tran.basis, dump);

        matrixGfx.setDump(dump);
        matrixGfx.rotateX((float)Math.PI);

        cube.setRotationMatrix(matrixGfx);
    }
}
