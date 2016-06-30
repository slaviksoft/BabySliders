package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import custom.Constants;
import custom.GameManager;
import custom.StageBugs;
import custom.StageExitButtons;
import custom.StageHelp;

/**
 * Created by Slavik on 06.08.2015.
 */
public class ScreenGame implements Screen {

    private final String TAG = getClass().getSimpleName();

    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    private GameManager gameManager;

    private StageBugs stageBugs;
    private StageExitButtons stageExitButtons;
    private StageHelp stageHelp;


    public ScreenGame(GameManager gameManager){
        Gdx.app.log(TAG, "constructor");
        this.gameManager = gameManager;
    }

    @Override
    public void show() {

        Gdx.app.log(TAG, "show");

        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewport.apply();

        stageBugs = new StageBugs(viewport, batch);

        stageExitButtons = new StageExitButtons(gameManager, viewport, batch);

        stageHelp = new StageHelp(gameManager, viewport, batch);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stageExitButtons);
        inputMultiplexer.addProcessor(stageBugs);
        inputMultiplexer.addProcessor(stageHelp);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageBugs.act();
        stageBugs.draw();

        stageExitButtons.act();
        stageExitButtons.draw();

        stageHelp.act();
        stageHelp.draw();



    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, false);
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide");

        stageBugs.dispose();
        stageExitButtons.dispose();
        stageHelp.dispose();

        batch.dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
    }

}
