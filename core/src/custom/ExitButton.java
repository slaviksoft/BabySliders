package custom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * Created by Slavik on 21.07.2015.
 */
public class ExitButton extends Button {

    protected final String TAG = getClass().getSimpleName();
    private TextureRegion region;

    public ExitButton(TextureRegion region, float x, float y){
        this.region = region;
        setPosition(x, y);
        setWidth(region.getRegionWidth());
        setHeight(region.getRegionHeight());
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

}
