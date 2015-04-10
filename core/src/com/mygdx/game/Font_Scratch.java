package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ben on 2015-04-08.
 */
public class Font_Scratch extends ApplicationAdapter{

    SpriteBatch batch;
    BitmapFont font;
    Texture txtFont;

    @Override
    public void create(){
        txtFont=new Texture("FONT.png");
        TextureRegion[][] rgnFont = TextureRegion.split(txtFont,(txtFont.getWidth()/26),(txtFont.getHeight()/2));
        for(int i = 0;i<26;i++){

        }
        font= new BitmapFont();


    }
    @Override
    public void render(){
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
       // font.draw(batch, "yea",20,20);
        batch.end();
    }
    @Override
    public void dispose(){
        batch.dispose();
    }
}
