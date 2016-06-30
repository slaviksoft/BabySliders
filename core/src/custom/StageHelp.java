package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Slavik on 12.08.2015.
 */
public class StageHelp extends Stage {

    private final GameManager gameManager;
    private final TextureAtlas atlasImages;

    private Group groupHelp;
    private FingerActor fingerTop;
    private FingerActor fingerDown;

    public StageHelp(GameManager gameManager, Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        this.gameManager = gameManager;
        atlasImages = new TextureAtlas("textures/img-pack.atlas");
        initActors();
    }

    private void initActors(){

        TextureRegion regionFinger = atlasImages.findRegion("finger1");

        fingerTop = new FingerActor(regionFinger, 40, Constants.WORLD_HEIGHT - regionFinger.getRegionHeight() - 40, false);
        fingerDown = new FingerActor(regionFinger, Constants.WORLD_WIDTH - regionFinger.getRegionWidth() - 40, 40, true);

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/gothic.fnt"));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        final Label labelInfo = new Label("PRESS TO EXIT", labelStyle);
        labelInfo.setPosition(0, Constants.WORLD_HEIGHT/2);
        labelInfo.setWidth(getWidth());
        labelInfo.setAlignment(Align.center);

        groupHelp = new Group();
        groupHelp.addActor(fingerTop);
        groupHelp.addActor(fingerDown);
        groupHelp.addActor(labelInfo);

        addFlashAction(groupHelp, 0.5f, 3);
        groupHelp.addAction(Actions.after(Actions.removeActor(groupHelp)));

        addActor(groupHelp);
    }


    private void addFlashAction(Actor actor, float flashPeriod, int flashTimes){

        for (int i = 0; i < flashTimes; i++) {
            actor.addAction(Actions.after(Actions.fadeOut(flashPeriod)));
            actor.addAction(Actions.after(Actions.fadeIn(flashPeriod)));
        }
        actor.addAction(Actions.after(Actions.fadeOut(flashPeriod)));
    }

    private class FingerActor extends Actor{

        TextureRegion region;
        Sprite sprite;
        boolean fliped;

        public FingerActor(TextureRegion region, float x, float y, boolean fliped){
            this.region = region;
            this.fliped = fliped;
            sprite = new Sprite(region);
            sprite.setPosition(x, y);
            sprite.flip(fliped, fliped);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            sprite.draw(batch, parentAlpha);
        }


    }

}
