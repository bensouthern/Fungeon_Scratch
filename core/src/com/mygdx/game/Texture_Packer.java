package com.mygdx.game;

/**
 * Created by Ben on 2015-04-02.
 */
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
public class Texture_Packer {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("C:/Users/Ben/AndroidStudioProjects/Fungeon_Scratch/android/assets",
                "C:/Users/Ben/AndroidStudioProjects/Fungeon_Scratch/android/assets/android/assets", "packer");
    }
}