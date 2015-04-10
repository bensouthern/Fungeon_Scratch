package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ben on 2015-03-15.
 */
public class Animation_Scratch extends ApplicationAdapter{

    int Columns=10, Rows=2;
    SpriteBatch batch;
    Animation anFire;
    Texture FireSheet;
    float Time=0;
    TextureRegion Frame;

 @Override
    public void create(){
     FireSheet = new Texture("fire trap.png");
     batch= new SpriteBatch();
     TextureRegion[][] tmp= TextureRegion.split(FireSheet,(FireSheet.getWidth())/Columns, FireSheet.getHeight()/Rows);
     TextureRegion[] Fire=new TextureRegion[Rows*Columns];
     int index=0;
     for(int i=0;i<Rows;i++){
         for(int j=0;j<Columns;j++){
             Fire[index++]=tmp[i][j];
         }
     }

     anFire=new Animation(0.075f,Fire);
 }
    @Override
    public void dispose(){
        batch.dispose();
    }
    @Override
    public void render(){
        super.render();
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        Time += Gdx.graphics.getDeltaTime();           // #15
        Frame = anFire.getKeyFrame(Time, true);  // #16
        batch.begin();
        batch.draw(Frame, 50, 50);             // #17
        batch.end();
    }
}
