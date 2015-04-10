package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Touchpad_Scratch;
import com.mygdx.game.TiledMap_Scratch;
import com.mygdx.game.Arrow_Scratch;
import com.mygdx.game.Box2D_Scratch;
import com.mygdx.game.Animation_Scratch;
import com.mygdx.game.BigAnimation_Scratch;
import com.mygdx.game.Texture_Packer;
import com.mygdx.game.Font_Scratch;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BigAnimation_Scratch(), config);
	}
}
