package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Slavik on 17.07.2015.
 */
public abstract class ScreenCustom implements Screen {

    protected final String TAG = getClass().getSimpleName();
    protected GameManager gameManager;

    protected SpriteBatch batch;
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Stage stage;

    protected ScreenCustom(GameManager gameManager){
        Gdx.app.log(TAG, "create");
        this.gameManager = gameManager;
    }

    abstract protected void initActors();
    abstract protected void update();

    @Override
    public void show() {

        Gdx.app.log(TAG, "show");

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport, batch);
        stage.setDebugAll(Constants.DEBUG);

        initActors();

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide");

        stage.dispose();
        batch.dispose();
    }

    @Override
    public void dispose() {

    }



}
