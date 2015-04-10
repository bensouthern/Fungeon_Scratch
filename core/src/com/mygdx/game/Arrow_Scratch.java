package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.awt.TexturePaint;

/**
 * Created by Ben on 2015-03-03.
 */
public class Arrow_Scratch extends ApplicationAdapter{
    SpriteBatch batch;
    Texture imgB, imgLogic;
    int X, Y , nScreenHeight, nScreenWidth;
    double VX = 0, VY = 0, dGrav=0.1;
    Boolean bShot=false;
    Vector2 ArrowMove= new Vector2(0f,0f);
    Touchpad touchpadMove;
    Touchpad.TouchpadStyle touchpadMoveStyle;
    Skin touchpadMoveSkin;
    Drawable touchMoveKnob, touchMoveBackground;
    Stage stage;

    @Override
    public void create() {


        stage = new Stage();
        nScreenWidth=Gdx.graphics.getWidth();
        nScreenHeight=Gdx.graphics.getHeight();
        X=nScreenWidth/2;
        Y=nScreenHeight/2;
        batch = new SpriteBatch();
        imgLogic = new Texture("badlogic.jpg");
        imgB = new Texture("black.png");
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
        touchpadMove.setPosition(nScreenWidth/2-100, nScreenHeight/2-50);
        stage.addActor(touchpadMove);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgB, X, Y);
        batch.end();
        stage.draw();
        if(touchpadMove.isTouched() && bShot==false){
            ArrowMove.set(touchpadMove.getKnobPercentX()*-10,touchpadMove.getKnobPercentY()*-10);
        }
        else if(ArrowMove.x!=0 && ArrowMove.y!=0 && bShot==false){
            VX=ArrowMove.x;
            VY=ArrowMove.y;
            bShot=true;
            ArrowMove.set(0,0);
        }

        X += VX;
        Y += VY;
        if(bShot==true){
            VY-=dGrav;
        }
        if(X>nScreenWidth || X<0 || Y>nScreenHeight || Y<0){
            X=nScreenWidth/2;
            Y=nScreenHeight/2;
            VX=0;
            VY=0;
            bShot=false;
        }

    }
}