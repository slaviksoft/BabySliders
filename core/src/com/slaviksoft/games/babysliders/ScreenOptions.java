package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import custom.BackgroundActor;
import custom.Constants;
import custom.GameManager;
import custom.ScreenCustom;

/**
 * Created by Slavik on 27.07.2015.
 */
public class ScreenOptions extends ScreenCustom {

    private final TextureAtlas atlasUI;
    private final BitmapFont font;

    public ScreenOptions(GameManager gameManager) {
        super(gameManager);
        atlasUI = new TextureAtlas("ui/buttons-pack.atlas");
        font = new BitmapFont(Gdx.files.internal("font/gothic.fnt"));
    }

    @Override
    protected void initActors() {

        Gdx.input.setCatchBackKey(true);

        BackgroundActor background = new BackgroundActor(new Texture("background/background_menu.jpg"), Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage.addActor(background);

        NinePatchDrawable buttonDown = new NinePatchDrawable(atlasUI.createPatch("menu_button_down"));
        NinePatchDrawable buttonUp = new NinePatchDrawable(atlasUI.createPatch("menu_button_up"));
        NinePatchDrawable buttonChecked = new NinePatchDrawable(atlasUI.createPatch("menu_button_checked"));

        SpriteDrawable knobSprite = new SpriteDrawable(atlasUI.createSprite("slider"));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.MAGENTA;

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = buttonDown;
        sliderStyle.background = knobSprite;

        final Label labelSound = new Label(appPreferences.getSoundVolumeFormatted(), labelStyle);
        labelSound.setAlignment(Align.center);

        final Label labelSoundText = new Label("Sound", labelStyle);
        labelSound.setAlignment(Align.center);

        final Slider sliderVolume = new Slider(0, 100, 10f, false, sliderStyle);
        sliderVolume.setValue(appPreferences.getSoundVolume());
        sliderVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                appPreferences.setSoundVolume(sliderVolume.getValue());
                labelSound.setText(appPreferences.getSoundVolumeFormatted());
            }
        });

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.down = new SpriteDrawable(atlasUI.createSprite("options_back_down"));
        buttonStyle.up = new SpriteDrawable(atlasUI.createSprite("options_back"));

        Button buttonBack = new Button(buttonStyle);
        buttonBack.setWidth(Constants.WORLD_WIDTH * 0.2f);
        buttonBack.setHeight(Constants.WORLD_WIDTH * 0.2f);
        buttonBack.setPosition(buttonBack.getWidth() * 0.1f, buttonBack.getHeight());
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
                gameManager.gotoMenu();
            }
        });

        // radiobuttons

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.down = buttonDown;
        textButtonStyle.up = buttonUp;
        textButtonStyle.checked = buttonChecked;
        textButtonStyle.pressedOffsetX = 3;
        textButtonStyle.pressedOffsetY = -3;
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.MAGENTA;

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setMaxCheckCount(1);

        TextButton levels[] = new TextButton[Constants.LEVELS_COUNT];
        for (int i = 0; i < Constants.LEVELS_COUNT; i++) {
            levels[i] = new TextButton(Constants.LEVELS_NAME[i], textButtonStyle);
            levels[i].addListener(new levelButtonListener(i));
            levels[i].setName("level"+i);

            buttonGroup.add(levels[i]);
        }

        int level = appPreferences.getBugsLevel();
        buttonGroup.setChecked(Constants.LEVELS_NAME[level]);

        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.top();
        menuTable.setDebug(Constants.DEBUG);

        //sound

        menuTable.add(labelSoundText).width(Constants.WORLD_WIDTH * 0.5f).height(Constants.WORLD_HEIGHT * 0.1f);
        menuTable.add(labelSound).width(Constants.WORLD_WIDTH * 0.2f).height(Constants.WORLD_HEIGHT * 0.1f);
        menuTable.row();
        menuTable.add(sliderVolume).width(Constants.WORLD_WIDTH * 0.7f).height(Constants.WORLD_HEIGHT * 0.1f).colspan(2).padBottom(Constants.WORLD_HEIGHT * 0.1f);
        menuTable.row();

        //level
        for (int i = 0; i < Constants.LEVELS_COUNT; i++) {
            menuTable.add(levels[i]).width(Constants.WORLD_WIDTH * 0.7f).height(Constants.WORLD_HEIGHT * 0.1f).colspan(2);
            menuTable.row();
        }

        stage.addActor(menuTable);

        stage.addActor(buttonBack);

    }

    @Override
    protected void update() {}

    public class levelButtonListener extends ClickListener{

        private int level;

        public levelButtonListener(int level){
            super();
            this.level = level;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
            appPreferences.setBugsLevel(level);
        }
    }

}
