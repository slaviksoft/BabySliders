package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.slaviksoft.games.babysliders.appPreferences;

import java.util.Random;


/**
 * Created by Slavik on 06.08.2015.
 */
public class StageBugs extends Stage {

    private final TextureAtlas atlasBeetleShadow;
    private SoundManager soundManager;
    private TextureAtlas atlasBeetleRed;
    private TextureAtlas atlasBeetleBlue;
    private TextureAtlas atlasBeetleGreen;
    private Texture textureBackground;
    private BackgroundActor background;
//    private boolean paused;

    public StageBugs(Viewport viewport, Batch batch) {

        super(viewport, batch);

        atlasBeetleRed = new TextureAtlas("textures/beetle-red-pack.atlas");
        atlasBeetleBlue = new TextureAtlas("textures/beetle-blue-pack.atlas");
        atlasBeetleGreen = new TextureAtlas("textures/beetle-green-pack.atlas");
        atlasBeetleShadow = new TextureAtlas("textures/beetle-shadow-pack.atlas");

        textureBackground = new Texture("background/green_grass.jpg");

        soundManager = new SoundManager();
        soundManager.playMusic();

        initActors();

    }

    @Override
    public void act() {

//        if (paused){
//            return;
//        }

        super.act();

        boolean haveActiveActions = false;
        for (Actor obj: getActors()){
            if (obj instanceof ObjectActor)
                if (((ObjectActor)obj).getState() == ObjectActor.States.PANIC) haveActiveActions = true;
        }

        if (haveActiveActions) soundManager.play();
                            else soundManager.stop();
    }

    @Override
    public void draw() {

        getCamera().update();
        Batch batch = getBatch();
        if (batch != null) {
            batch.setProjectionMatrix(getCamera().combined);
            batch.begin();

            background.draw(batch, 1);

            for (Actor obj:this.getActors()) {
                if(obj instanceof ObjectActor) ((ObjectActor)obj).drawShadow(batch);
            }

            getRoot().draw(batch, 1);

            batch.end();
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        soundManager.stopMusic();
        atlasBeetleBlue.dispose();
        atlasBeetleGreen.dispose();
        atlasBeetleRed.dispose();
        textureBackground.dispose();

    }




    private void initActors() {

        Gdx.input.setCatchBackKey(true);

        background = new BackgroundActor(textureBackground, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        randomBug rndBug = new randomBug();
        float moveDuration = Constants.MOVE_DURATION_MIN;

        int level = appPreferences.getBugsLevel();

        for (int i = 0; i < Constants.BUG_COUNT[level]; i++) {

            rndBug.nextBug();

            ObjectActor obj = new ObjectActor(rndBug.atlas);
            obj.setName(rndBug.name);
            obj.setShadow(atlasBeetleShadow);
            obj.setSize(Constants.BUG_SIZE[level]);
            obj.setMoveDuration(moveDuration);
            obj.start();
            addActor(obj);

            moveDuration+=Constants.MOVE_DURATION_DELTA;
            if (moveDuration > Constants.MOVE_DURATION_MAX) moveDuration = Constants.MOVE_DURATION_MIN;

        }

    }


    private class randomBug{
        public String name;
        public TextureAtlas atlas;

        private Random random;

        public randomBug(){
            random = new Random();
        }

        public void nextBug(){

            int rnd = random.nextInt(3);

            if(rnd == 0){
                name = "red";
                atlas = atlasBeetleRed;
            }else if(rnd == 1){
                name = "blue";
                atlas = atlasBeetleBlue;
            }else if(rnd == 2){
                name = "green";
                atlas = atlasBeetleGreen;
            }
        }

    }



}

