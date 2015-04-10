package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import org.lwjgl.Sys;

/**
 * Created by Ben on 2015-03-18.
 */
public class BigAnimation_Scratch extends ApplicationAdapter implements InputProcessor{
    TiledMap tiledMap;
    TmxMapLoader TiledMapLoader;
    TiledMapTileLayer tiledMapCol, tiledMapLight;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    int nMapScale;
    OrthographicCamera camera;
    int DeltaY=0, nDir=2;//nDir 1=Left, 2=Right
    int Columns=6, Rows=6;
    Animation WalkR,WalkL,StandR,StandL,JumpR,JumpL, CurAnim;
    Texture CharSheet;
    float Time=0;
    TextureRegion Frame;

    SpriteBatch batch;
    Texture img, img2;
    int nImgWidth, nImgHeight, nTileHeight, nTileWidth;
    int X = 600, Y = 300;
    int nOldX=X,nOldY=Y, nGrav=-1;
    double VX = 0, VY = 0;
    boolean bCollideX=false,bCollideY=false, bCanJump=false, bUp=false, bDark=true;

    public void create(){

        TiledMapLoader=new TmxMapLoader();

        // nMapScale = Gdx.graphics.getHeight() * 5 / 1080;
        tiledMap=TiledMapLoader.load("Fungeon_Scratch.tmx");
        tiledMapCol= (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tiledMapLight= (TiledMapTileLayer) tiledMap.getLayers().get(1);
        tiledMapLight.setVisible(false);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera=new OrthographicCamera();
        camera.viewportHeight= Gdx.graphics.getHeight()/2;
        camera.viewportWidth=Gdx.graphics.getWidth()/2;

        nTileWidth=(int)tiledMapCol.getTileWidth();
        nTileHeight=(int)tiledMapCol.getTileHeight();


        batch = new SpriteBatch();
        //tiledMapCol.setVisible(false);
        CharSheet = new Texture("Fungeon Char 64.png");
        nImgHeight=CharSheet.getHeight()/Rows;
        nImgWidth=CharSheet.getWidth()/Columns;
        TextureRegion[][] Character= TextureRegion.split(CharSheet,(CharSheet.getWidth())/Columns, CharSheet.getHeight()/Rows);
        WalkR=new Animation(0.075f,Character[0]);
        WalkL=new Animation(0.075f,Character[1]);
        StandR=new Animation(0.2f,Character[2]);
        StandL=new Animation(0.2f,Character[3]);
        JumpR=new Animation(0.075f,Character[4]);
        JumpL=new Animation(0.075f,Character[5]);
        CurAnim=StandR;
        Gdx.input.setInputProcessor(this);
    }
    public void render(){
        super.render();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        DeltaY+=VY;
            if(DeltaY>150 || bUp==false || VY<=0){
                VY+=nGrav;
        }
        nOldX=X;
        nOldY=Y;
        X += VX;
        Y += VY;

        Time += Gdx.graphics.getDeltaTime();           // #15
        Frame = CurAnim.getKeyFrame(Time, true);


        if(VX>0){
            bCollideX=tiledMapCol.getCell((int) ((X+nImgWidth)/nTileWidth), (int) ((Y+nImgHeight/2) / nTileHeight))//Collide on Left
                    .getTile().getProperties().containsKey("Hit");

        }
        if(VX<0){
            bCollideX=tiledMapCol.getCell((int) ((X)/nTileWidth), (int) ((Y+nImgHeight/2) / nTileHeight))//Collide on Right
                    .getTile().getProperties().containsKey("Hit");

        }
        if(VY>0){
            bCollideY=tiledMapCol.getCell((int) ((X+nImgWidth/2)/nTileWidth), ((Y+nImgHeight) / nTileHeight))//Collide Up
                    .getTile().getProperties().containsKey("Hit");

        }
        if(VY<0){
            bCollideY=tiledMapCol.getCell((int) ((X+nImgWidth/2)/nTileWidth), (int) ((Y) / nTileHeight))//Collide Down
                    .getTile().getProperties().containsKey("Hit");


        }
        if(bCollideY==true){
            while(bCollideY==true){
                Y+=1;
                bCollideY=tiledMapCol.getCell((int) ((X+nImgWidth/2)/nTileWidth), (int) ((Y) / nTileHeight))//Collide Down
                        .getTile().getProperties().containsKey("Hit");
            }
            if(VY<0){
                bCanJump=true;
                DeltaY=0;
            }
            VY=0;
        }
        if(bCollideX==true){
            X=nOldX;
        }
        if(VY!=0){
            bCanJump=false;
        }
        if(nDir==1){//left
            if(bCanJump==true){
                if(VX!=0){
                    CurAnim=WalkL;
                }
                else if(VX==0){
                    CurAnim=StandL;
                }
            }
            else{
                CurAnim=JumpL;
            }
        }
        if(nDir==2){//right
            if(bCanJump==true){
                if(VX!=0){
                    CurAnim=WalkR;
                }
                else if(VX==0){
                    CurAnim=StandR;
                }
            }
            else{
                CurAnim=JumpR;
            }
        }
        camera.position.set(X,Y,0);
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(Frame, X, Y);             // #17
        batch.end();
//System.out.println(CurAnim);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode== Input.Keys.RIGHT){
            VX=10;
            nDir=2;
        }
        if(keycode== Input.Keys.LEFT){
            VX=-10;
            nDir=1;
        }
        if(keycode== Input.Keys.UP){
            if(bCanJump==true){
                VY=10;
                bUp=true;
            }
        }
        if(keycode== Input.Keys.DOWN){
            System.out.println(bDark);
            if(bDark==false){
                CharSheet=new Texture("Fungeon Char 64.png");
                tiledMapLight.setVisible(false);
                bDark=true;
            }
            //System.out.println(bDark);
            else if(bDark==true){
                CharSheet=new Texture("Fungeon Char 64 W.png");
                tiledMapLight.setVisible(true);
                bDark=false;
            }
            TextureRegion[][] Character= TextureRegion.split(CharSheet,(CharSheet.getWidth())/Columns, CharSheet.getHeight()/Rows);
            WalkR=new Animation(0.075f,Character[0]);
            WalkL=new Animation(0.075f,Character[1]);
            StandR=new Animation(0.2f,Character[2]);
            StandL=new Animation(0.2f,Character[3]);
            JumpR=new Animation(0.075f,Character[4]);
            JumpL=new Animation(0.075f,Character[5]);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode== Input.Keys.LEFT){
                VX = 0;
        }
        if(keycode== Input.Keys.RIGHT){
                VX=0;
        }
        if(keycode== Input.Keys.UP){
            bUp=false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}