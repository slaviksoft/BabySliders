package custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by Slavik on 17.07.2015.
 */
public class BackgroundActor extends Actor {

    private Sprite sprite;

    public BackgroundActor(Texture texture, float width, float height){
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, getColor().a);
    }
}
