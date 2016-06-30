package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import custom.BackgroundActor;
import custom.Constants;
import custom.GameManager;
import custom.ScreenCustom;

/**
 * Created by Slavik on 30.09.2015.
 */
public class ScreenSplash extends ScreenCustom{

    private BackgroundActor background;

    public ScreenSplash(GameManager gameManager){
        super(gameManager);
    }

    @Override
    protected void initActors() {
        background = new BackgroundActor(new Texture("background/splash.jpg"), Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage.addActor(background);

        background.addAction(Actions.delay(Constants.SPLASH_DELAY, Actions.run(new Runnable() {
            @Override
            public void run() {
                Gdx.app.log(TAG, "Runable");
                gameManager.gotoMenu();
            }
        })));

        Gdx.app.log(TAG, "Init actors");

    }

    @Override
    protected void update() {

    }
}
