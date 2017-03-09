package com.example.dimpychhabra.myfrenchapp;

import android.media.AudioManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.security.PublicKey;

/**
 * Created by Dimpy Chhabra on 3/9/2017.
 */

public class Word {
    private static final int NO_IMAGE_PROVIDED = -1;

    private int DefaultLangTranslationId;
    private int FrenchLangTranslationId;
    private int AudioResourceId;
    private int ImageResourceId = NO_IMAGE_PROVIDED;
    // to set imageId as -1 initially, using a macro is a good practice

    public Word(int defaultLangTranslationId, int frenchLangTranslationId, int imageResourceId, int audioResourceId){
        DefaultLangTranslationId=defaultLangTranslationId;
        FrenchLangTranslationId=frenchLangTranslationId;
        ImageResourceId= imageResourceId;
        AudioResourceId = audioResourceId;
    }

    public Word(int defaultLangTranslationId, int frenchLangTranslationId, int audioResourceId){
        DefaultLangTranslationId=defaultLangTranslationId;
        FrenchLangTranslationId=frenchLangTranslationId;
        AudioResourceId = audioResourceId;
    }

    public int getDefaultLangTranslationId(){ return DefaultLangTranslationId;}
    public int getFrenchLangTranslationId(){return FrenchLangTranslationId;}
    public int getAudioResourceId(){return AudioResourceId;}
    public int getImageResourceId(){return ImageResourceId;}
    public boolean hasImage(){ return ImageResourceId !=NO_IMAGE_PROVIDED;}
    //hasImage is defined to check which constructor must be used.
    //the argument in the method defines a boolean on comparison of the LHS and RHS.
}
