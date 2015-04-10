package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


/**
 * Created by Ben on 2015-02-13.
 */
public class TiledMap_Scratch extends ApplicationAdapter {
    TiledMap tiledMap;
    TmxMapLoader TiledMapLoader;
    TiledMapTileLayer tiledMapCol;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    int nMapScale;
    OrthographicCamera camera;

    SpriteBatch batch;
    Texture img, img2;
    int nImgWidth=100, nImgHeight=100, nTileHeight, nTileWidth;
    int X = 600, Y = 300;
    int nOldX=X,nOldY=Y;
    double VX = 0, VY = 0;
    boolean bCollideX=false,bCollideY=false;
    Touchpad touchpadMove;
    Touchpad.TouchpadStyle touchpadMoveStyle;
    Skin touchpadMoveSkin;
    Drawable touchMoveKnob, touchMoveBackground;
    Stage stage;
    public TiledMap_Scratch(){

    }
    public void create(){
        TiledMapLoader=new TmxMapLoader();

       // nMapScale = Gdx.graphics.getHeight() * 5 / 1080;
        tiledMap=TiledMapLoader.load("Fungeon_Scratch.tmx");
        tiledMapCol= (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera=new OrthographicCamera();
        camera.viewportHeight=Gdx.graphics.getHeight();
        camera.viewportWidth=Gdx.graphics.getWidth();

        nTileWidth=(int)tiledMapCol.getTileWidth();
        nTileHeight=(int)tiledMapCol.getTileHeight();

        stage = new Stage();

        batch = new SpriteBatch();
        img = new Texture("black.png");
        img2= new Texture("badlogic.jpg");

        touchpadMoveSkin = new Skin();//making a touchpad which is kinda like an analog stick
        touchpadMoveSkin.add("touchKnob", img);
        touchpadMoveSkin.add("touchBackground", img2);
        touchMoveKnob = touchpadMoveSkin.getDrawable("touchKnob");
        touchMoveBackground = touchpadMoveSkin.getDrawable("touchBackground");
        touchpadMoveStyle = new Touchpad.TouchpadStyle();
        touchpadMoveStyle.knob = touchMoveKnob;
        touchpadMoveStyle.background = touchMoveBackground;
        touchpadMove = new Touchpad(1, touchpadMoveStyle);
        touchpadMove.setSize(200, 200);
        touchpadMove.setPosition(X-500, Y-300);
        stage.addActor(touchpadMove);
        Gdx.input.setInputProcessor(stage);
        nImgWidth=img.getWidth();
        nImgHeight=img.getHeight();
        //tiledMapCol.setVisible(false);
    }
    public void render(){
        super.render();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(X,Y,0);
        batch.setProjectionMatrix(camera.combined);//figure this s*** out plz?
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        VX = touchpadMove.getKnobPercentX() * 5;
        VY = touchpadMove.getKnobPercentY() * 5;
        nOldX=X;
        nOldY=Y;
        X += VX;
        Y += VY;

        batch.begin();
        batch.draw(img, X, Y);
        batch.end();
        stage.draw();




        if(VX>0){
            bCollideX=tiledMapCol.getCell((int) ((X+nImgWidth)/nTileWidth), (int) ((Y+nImgHeight/2) / nTileHeight))
                    .getTile().getProperties().containsKey("Hit");
            if(bCollideX==true){
                X=600;
                Y=350;
                bCollideX=false;
            }
        }
        if(VX<0){
            bCollideX=tiledMapCol.getCell((int) ((X)/nTileWidth), (int) ((Y+nImgHeight/2) / nTileHeight))
                    .getTile().getProperties().containsKey("Hit");

        }
        if(VY>0){
            bCollideY=tiledMapCol.getCell((int) ((X+nImgWidth/2)/nTileWidth), ((Y+nImgHeight) / nTileHeight))
                    .getTile().getProperties().containsKey("Hit");

        }
        if(VY<0){
            bCollideY=tiledMapCol.getCell((int) ((X+nImgWidth/2)/nTileWidth), (int) ((Y) / nTileHeight))
                    .getTile().getProperties().containsKey("Hit");
        }
        if(bCollideX==true){
            X=nOldX;
        }
        if(bCollideY==true){
            Y=nOldY;
        }

    }
}