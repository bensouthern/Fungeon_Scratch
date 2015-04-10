package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

import box2dLight.ChainLight;
import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by Ben on 2015-03-07.
 */
public class Box2D_Scratch extends ApplicationAdapter {
    OrthographicCamera camera;
    Vector2 gravity=new Vector2(0,-9.8f), CurMove=new Vector2(0,0);
    BodyDef groundDef,squareDef;
    Body squareBody, groundBody;
    FixtureDef squareFixDef;
    PolygonShape squareBox, groundBox;
    World world;
    RayHandler handler;
    int height, width,X,Y;
    double VX = 0, VY = 0;
    Box2DDebugRenderer b2Renderer;

    Array<Body> arWorldBodies = new Array<Body>();

    SpriteBatch batch;
    Touchpad touchpadMove;
    Touchpad.TouchpadStyle touchpadMoveStyle;
    Skin touchpadMoveSkin;
    Drawable touchMoveKnob, touchMoveBackground;
    Stage stage;
    Texture imgB, imgLogic, imgG;
    Sprite sprite;
    @Override
    public void create(){
        height= Gdx.graphics.getHeight()/4;
        width= Gdx.graphics.getWidth()/4;
        X=width/2;
        Y=height/2;
        camera= new OrthographicCamera(width, height);
        camera.position.set(width/2f,height/2f,0);
        camera.update();
        world=new World(gravity,false);
        groundDef=new BodyDef();
        squareDef=new BodyDef();
        groundBox=new PolygonShape();
        squareBox=new PolygonShape();
        b2Renderer=new Box2DDebugRenderer();
        squareFixDef=new FixtureDef();



        groundDef.position.set(0.0f, 3);
        groundBody= world.createBody(groundDef);
        groundBox.setAsBox(width, 10f);
        groundBody.createFixture(groundBox, 0f);

        squareBox.setAsBox(10f, 10f);
        squareDef.position.set(width/2, height/2);
        squareDef.type= BodyDef.BodyType.DynamicBody;
        squareBody=world.createBody(squareDef);
        squareFixDef.restitution=0.3f;
        squareFixDef.shape=squareBox;
        squareFixDef.density=0.3f;
        squareFixDef.friction=0.2f;
        squareBody.createFixture(squareFixDef);
        handler=new RayHandler(world);
        handler.setCombinedMatrix(camera.combined);

        new PointLight(handler, 1500, Color.ORANGE, 200, (width/2)-100,(height/2));
        new PointLight(handler, 1500, Color.PURPLE, 250, (width/2)+100,(height/2));

        stage = new Stage();
        batch = new SpriteBatch();
        imgLogic = new Texture("badlogic.jpg");
        imgB = new Texture("black.png");
        imgG = new Texture("grey.png");
        touchpadMoveSkin = new Skin();//making a touchpad which is kinda like an analog stick
        touchpadMoveSkin.add("touchKnob", imgB);
        touchpadMoveSkin.add("touchBackground", imgLogic);
        touchMoveKnob = touchpadMoveSkin.getDrawable("touchKnob");
        touchMoveBackground = touchpadMoveSkin.getDrawable("touchBackground");
        touchpadMoveStyle = new Touchpad.TouchpadStyle();
        touchpadMoveStyle.knob = touchMoveKnob;
        touchpadMoveStyle.background = touchMoveBackground;
        touchpadMove = new Touchpad(1, touchpadMoveStyle);
        touchpadMove.setSize(200, 200);
        touchpadMove.setPosition(width/2-100, height/2-50);
        stage.addActor(touchpadMove);
        Gdx.input.setInputProcessor(stage);

        sprite = new Sprite(imgLogic);
        sprite.setSize(10f*2,10f*2);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        squareBody.setUserData(sprite);
    }
    @Override
    public void dispose(){
        world.dispose();
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void render(){
        super.render();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2Renderer.render(world, camera.combined);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(arWorldBodies);
        for(Body body: arWorldBodies){  //this is new to me
            if(body.getUserData()!=null && body.getUserData() instanceof Sprite){
                Sprite newSprite = (Sprite)body.getUserData();
                newSprite.setPosition(body.getPosition().x-newSprite.getWidth()/2
                        ,body.getPosition().y-newSprite.getHeight()/2);
                newSprite.setRotation(body.getAngle()* MathUtils.radiansToDegrees);
                newSprite.draw(batch);
            }
        }
        batch.end();

        CurMove.set(squareBody.getLinearVelocity());
        if(touchpadMove.isTouched()){
            squareBody.setLinearVelocity(touchpadMove.getKnobPercentX()*10,CurMove.y);
        }
        handler.updateAndRender();
        handler.setAmbientLight(0.5f);
        stage.draw();
        world.step(1/60f, 6, 2);
    }
}
