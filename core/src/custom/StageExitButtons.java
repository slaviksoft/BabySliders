package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Slavik on 06.08.2015.
 */
public class StageExitButtons extends Stage {

    private ExitButton exit1;
    private ExitButton exit2;

    private TextureAtlas atlasImages;
    private GameManager gameManager;

    public StageExitButtons(GameManager gameManager, Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        this.gameManager = gameManager;
        atlasImages = new TextureAtlas("textures/img-pack.atlas");
        initActors();
    }

    private void initActors(){

        TextureRegion regionGreen = atlasImages.findRegion("circle_green");
        TextureRegion regionYellow = atlasImages.findRegion("circle_yellow");

        exit1 = new ExitButton(regionGreen, 20, Constants.WORLD_HEIGHT - regionGreen.getRegionHeight() - 20);
        exit2 = new ExitButton(regionYellow, Constants.WORLD_WIDTH - regionYellow.getRegionWidth() - 20, 20);
        addActor(exit1);
        addActor(exit2);

    }

    @Override
    public void act() {
        super.act();

        if(exit1.isPressed() && exit2.isPressed()){
            Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
            gameManager.gotoMenu();
        }
    }

}
