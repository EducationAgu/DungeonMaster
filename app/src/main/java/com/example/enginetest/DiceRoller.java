package com.example.enginetest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

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
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.threed.jpct.Camera;
import com.threed.jpct.CollisionEvent;
import com.threed.jpct.CollisionListener;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Matrix3f;
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
                    new Vector3f(0, 35, 0),
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

        dynamicsWorld.updateAabbs();
        move();
        for (RigidBody body : cubesPhysics) {
            Vector3f cubePosition = body.getWorldTransform(new Transform()).origin;
            Logger.log("rigid body position is: " + cubePosition.x + " " + cubePosition.y + " " + cubePosition.z);
//            cube.translate(cubePosition.x, cubePosition.y, cubePosition.z);
        }

        if (master.isPressed) {
            isStarted = true;
        }

        //move();

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
    private boolean isStarted = false;

    /*Метод отвечает за перемещение кубика (и объектов) из jPCT */
    private void move () {
        dynamicsWorld.stepSimulation(1.0f / 60.0f);
        if (applyForce) {

            for (RigidBody body : cubesPhysics) {
                Transform cubeTransform = new Transform();
                body.getMotionState().getWorldTransform(cubeTransform);
                Vector3f controlBallLocation = cubeTransform.origin;
                SimpleVector camera = world.getCamera().getPosition();
                Vector3f cameraPosition = new Vector3f(camera.x, camera.y, camera.z);
                Vector3f force = new Vector3f();
                force.sub(cameraPosition, controlBallLocation);
                body.activate(true);
                body.applyCentralForce(force);
            }

        }


        SimpleVector moveRes = new SimpleVector(0, 0, 0);
        final SimpleVector t = cube.getZAxis();

        if (isStarted && cube.getTransformedCenter().z < 50) {
            t.scalarMul(SPEED);
            moveRes.add(t);
        }

//        if( master.touchTurn != 0 ) {
//            final SimpleVector t = cube.getZAxis();
//            t.scalarMul(SPEED);
//            moveRes.add(t);
//
//            cube.rotateY(master.touchTurn);
//            master.touchTurn = 0;
//        }
//
//        if( master.touchTurnUp != 0 ) {
//            final SimpleVector t = cube.getZAxis();
//            t.scalarMul(-SPEED);
//            moveRes.add(t);
//
//            cube.rotateX(master.touchTurnUp);
//            master.touchTurnUp = 0;
//        }
//        if( moveRes.length() > MAXSPEED ) {
//            moveRes.makeEqualLength( new SimpleVector(0, 0, MAXSPEED) );
//        }
//
//        moveRes = cube.checkForCollisionEllipsoid(moveRes, ellipsoid, 8);
        cube.translate(moveRes);
//        moveRes = new SimpleVector(0, 0, 0);
    }


    private void physicsWorld() {
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher collisionDispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();

        BroadphaseInterface broadphase = new DbvtBroadphase();
        dynamicsWorld = new DiscreteDynamicsWorld(collisionDispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -10, 0));

        // Коллизия земли
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 0.25f);
        MotionState groundMotionState = new DefaultMotionState(
                new Transform(
                        new Matrix4f(
                                new Quat4f(0, 0, 0, 1),
                                new Vector3f(0,0,0 ),
                                1.0f)));
        RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(
                0,
                groundMotionState,
                groundShape,
                new Vector3f(0, 0 ,0));
        groundBodyConstructionInfo.restitution = 0.3f;
        RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
        dynamicsWorld.addRigidBody(groundRigidBody);
        ground = groundRigidBody;
        // Коллизия для кубика
        SimpleVector simpleVector = cube.getTransformedCenter();
        Vector3f physicsVector = new Vector3f(simpleVector.x, simpleVector.y, simpleVector.z);
        CollisionShape cubeShape = new BoxShape(physicsVector);
        MotionState cubeMotionState = new DefaultMotionState(DEFAULT_CUBE_TRANSFORM);
        Vector3f cubeInertia = new Vector3f(0, 0, 0);
        cubeShape.calculateLocalInertia(2.5f, cubeInertia);
        RigidBodyConstructionInfo cubeConstructionInfo = new RigidBodyConstructionInfo(2.5f, cubeMotionState, cubeShape, cubeInertia);
        cubeConstructionInfo.restitution = 0.5f;
        cubeConstructionInfo.friction = 0.95f;
//        cubeConstructionInfo.angularDamping = 0.95f;
        cubePhysics = new RigidBody(cubeConstructionInfo);
        cubePhysics.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        dynamicsWorld.addRigidBody(cubePhysics);

        this.cubesPhysics.add(cubePhysics); // добавляю его в сет, на случай если кубиков на экране будет несколько
    }

    private RigidBody ground;

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
        cube.translate(0, -20, -20); // Позиция кубика

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
}
