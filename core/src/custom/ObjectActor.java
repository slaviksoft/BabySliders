package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.Random;

/**
 * Created by Slavik on 21.07.2015.
 */
public class ObjectActor extends Actor {

    protected final String TAG = getClass().getSimpleName();

    final private float ROTATE_DURATION = 0.5f;
    private Random random = new Random();
    private Animation animation;
    private Animation animationShadow;
    private float elapsedTime;
    private float moveDuration;
    private ObjectState state;
    private float size = 1.0f;

    public ObjectActor(TextureAtlas atlas) {

        state = new ObjectState();

        animation = new Animation(1f/3f, atlas.getRegions());
        animation.setPlayMode(Animation.PlayMode.LOOP);

        setWidth(animation.getKeyFrames()[0].getRegionWidth());
        setHeight(animation.getKeyFrames()[0].getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        addListener(new BallListener());

        setPosition(getRandomPosX(), getRandomPosY());
        setRotation(90);

        setDebug(Constants.DEBUG);

    }

    public void start(){

        doRotateMoveUp();
    }

    public States getState() {
        return  state.currState;
    }

    public void setMoveDuration(float moveDuration) {
        this.moveDuration = moveDuration;
    }

    private int getRandomPosX(){
        return random.nextInt((int) (Constants.WORLD_WIDTH - getWidth()));
    }

    private int getRandomPosY(){
        return random.nextInt((int) (Constants.WORLD_HEIGHT - getHeight()));
    }

    private Vector2 getNewVector(float newX, float newY){
        return new Vector2(newX - getX(), newY - getY());
    }

    private Action customRotateAction(float angle){
        state.setRotating();
        return Actions.rotateTo(angle, Math.abs(angle - getRotation()) * ROTATE_DURATION / 180);
    }

    private Action customMove(float newX, float newY){

        float duration = moveDuration * getNewVector(newX, newY).len() / Constants.WORLD_HEIGHT;

        boolean isUp = newY - getY() >= 0;

        if (isUp) state.setMovingUp(); else state.setMovingDown();

        return Actions.moveTo(newX, newY, duration);
    }

    private Action customMoveUp(){
        return new Action() {
            @Override
            public boolean act(float delta) {
                doRotateMoveUp();
                return true;
            }
        };
    }

    private Action customMoveDown(){
        return new Action() {
            @Override
            public boolean act(float delta) {
                doRotateMoveDown();
                return true;
            }
        };
    }

    private Action customScale(float scale){
        state.setScaling();
        return Actions.scaleTo(scale, scale, 0.1f);
    }

    private Action customPanicStart(){

        state.setPanic();
        return new Action() {
            @Override
            public boolean act(float delta) {
                animation.setFrameDuration(1f / 20f);
                animation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
                animationShadow.setFrameDuration(1f / 20f);
                animationShadow.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
                return true;
            }
        };
    }

    private Action customPanicStop(){
        return new Action() {
            @Override
            public boolean act(float delta) {
                animation.setFrameDuration(1f / 3f);
                animation.setPlayMode(Animation.PlayMode.LOOP);
                animationShadow.setFrameDuration(1f / 20f);
                animationShadow.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
                if (state.getLastMovingState() == States.MOVING_UP) doRotateMoveUp(); else doRotateMoveDown();
                return true;
            }
        };
    }

    private void doRotateMoveUp(){

        float newX = getRandomPosX();
        float newY = Constants.WORLD_HEIGHT - getHeight();
        float newAngle = getNewVector(newX, newY).angle();

        addAction(
                Actions.sequence(
                        customRotateAction(newAngle),
                        customMove(newX, newY),
                        customMoveDown()
                )
        );
    }

    private void doRotateMoveDown(){

        float newX = getRandomPosX();
        float newY = 0;
        float newAngle = getNewVector(newX, newY).angle();

        addAction(
                Actions.sequence(
                        customRotateAction(newAngle),
                        customMove(newX, newY),
                        customMoveUp()
                )
        );

    }

    public void setShadow(TextureAtlas atlas) {

        animationShadow = new Animation(1f/3f, atlas.getRegions());
        animationShadow.setPlayMode(Animation.PlayMode.LOOP);

        setWidth(animationShadow.getKeyFrames()[0].getRegionWidth());
        setHeight(animationShadow.getKeyFrames()[0].getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

    }

    public void setSize(float size) {
        this.size = size;
        setScale(size, size);
    }


    class BallListener extends InputListener {

        private Vector2 startDelta;

        private int pointer = -1;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            if (this.pointer >= 0) return false;

            this.pointer = pointer;

            Vector2 stageCoords = localToStageCoordinates(new Vector2(x, y));
            startDelta = stageCoords.cpy().sub(new Vector2(getX(), getY()));

            clearActions();

            ObjectActor.this.toFront();

            addAction(customScale( size*Constants.BUG_PANIC_SCALE));
            addAction(customPanicStart());

            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            this.pointer = -1;

            addAction(customScale(size));
            addAction(customPanicStop());

        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {

            Vector2 stageCoords = localToStageCoordinates(new Vector2(x, y));
            Vector2 delta = stageCoords.sub(startDelta).cpy();

            setPosition(delta.x, delta.y);

        }

    }

    class ObjectState{

        private States lastMovingState;
        private States currState;

        public ObjectState(){
            lastMovingState = States.NULL;
            currState = States.NULL;
        }

        public void setState(States newState){
            if (currState == States.MOVING_DOWN || currState == States.MOVING_UP) lastMovingState = currState;
            currState = newState;
            Gdx.app.log(TAG, "state = " + newState);
        }

        public States getLastMovingState() {
            return lastMovingState == States.NULL ? States.MOVING_UP: lastMovingState;
        }

        public void setRotating(){
            setState(States.ROTATING);
        }

        public void setMovingUp(){
            setState(States.MOVING_UP);
        }

        public void setMovingDown(){
            setState(States.MOVING_DOWN);
        }

        public void setPanic(){
            setState(States.PANIC);
        }

        public void setScaling(){
            setState(States.SCALING);
        }

    }

    public enum States{
        NULL,
        ROTATING,
        MOVING_UP,
        MOVING_DOWN,
        SCALING,
        PANIC

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion textureRegion = animation.getKeyFrame(elapsedTime);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void drawShadow(Batch batch){

        float dx = 0;
        float dy = 0;
        if (getState() == States.PANIC){
            dx = getWidth()*0.1f;
            dy = -getHeight()*0.1f;
        }
        TextureRegion textureRegion = animationShadow.getKeyFrame(elapsedTime);
        batch.draw(textureRegion, getX()+dx, getY()+dy, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX()*Constants.SHADOW_SCALE, getScaleY()*Constants.SHADOW_SCALE, getRotation());

    }

}
