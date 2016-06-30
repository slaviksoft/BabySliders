package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Slavik on 04.08.2015.
 */
public class PauseActor extends Actor {

    private Sprite finger1;
    private Sprite finger2;
    private Label text;

    public PauseActor(TextureAtlas atlas, Actor help1, Actor help2){

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/gothic.fnt"));

        finger1 = new Sprite(atlas.findRegion("finger1"));
        finger2 = new Sprite(atlas.findRegion("finger1"));

        finger1.setOriginCenter();
        finger1.setRotation(45);
        finger1.setPosition(help1.getX() + help1.getWidth() - finger1.getWidth() / 3, help1.getY() - finger1.getHeight() + finger1.getHeight() / 3);

        finger2.setOriginCenter();
        finger2.setRotation(-135);
        finger2.setPosition(help2.getX() - finger2.getWidth() + finger2.getWidth() / 3, help2.getY() + help2.getHeight() - finger2.getHeight() / 3);

        setTouchable(Touchable.disabled);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.MAGENTA;

        text = new Label("Press to exit", labelStyle);
        text.setPosition(Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2, Align.center);
        text.setColor(Color.BLUE);

        float dur = 0.3f;
        Interpolation inter = Interpolation.fade;

        addAction(
                Actions.repeat(5,
                        Actions.sequence(
                                Actions.fadeIn(dur, inter),
                                Actions.fadeOut(dur, inter)
                        )
                )
        );
        addAction(Actions.fadeIn(dur, inter));
        addAction(Actions.after(Actions.removeActor()));

    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        finger1.draw(batch, getColor().a);
        finger2.draw(batch, getColor().a);
        text.draw(batch, parentAlpha);

    }
}
