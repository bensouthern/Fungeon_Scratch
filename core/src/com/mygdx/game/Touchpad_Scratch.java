package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Touchpad_Scratch extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int X = 550, Y = 550;
    double VX = 0, VY = 0;
    Touchpad touchpadMove;
    Touchpad.TouchpadStyle touchpadMoveStyle;
    Skin touchpadMoveSkin;
    Drawable touchMoveKnob, touchMoveBackground;
    Stage stage;

    @Override
    public void create() {
        stage = new Stage();

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        touchpadMoveSkin = new Skin();//making a touchpad which is kinda like an analog stick
        touchpadMoveSkin.add("touchKnob", img);
        touchpadMoveSkin.add("touchBackground", img);
        touchMoveKnob = touchpadMoveSkin.getDrawable("touchKnob");
        touchMoveBackground = touchpadMoveSkin.getDrawable("touchBackground");
        touchpadMoveStyle = new Touchpad.TouchpadStyle();
        touchpadMoveStyle.knob = touchMoveKnob;
        touchpadMoveStyle.background = touchMoveBackground;
        touchpadMove = new Touchpad(1, touchpadMoveStyle);
        touchpadMove.setSize(100, 100);
        touchpadMove.setPosition(200, 200);
        stage.addActor(touchpadMove);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, X, Y);
        batch.end();
        stage.draw();

        VX = touchpadMove.getKnobPercentX() * -5;
        VY = touchpadMove.getKnobPercentY() * -5;
        X += VX;
        Y += VY;
    }
}