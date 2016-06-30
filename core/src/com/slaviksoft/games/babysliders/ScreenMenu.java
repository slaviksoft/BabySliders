package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import custom.BackgroundActor;
import custom.Constants;
import custom.GameManager;
import custom.ScreenCustom;


/**
 * Created by Slavik on 17.07.2015.
 */
public class ScreenMenu extends ScreenCustom {

    private BitmapFont font;
	private TextureAtlas atlasUI;

	private Table menuTable;
	private TextButton buttonExit;
    private TextButton buttonPlay;
	private Button buttonOptions;
    private TextButton.TextButtonStyle textButtonStyle;

	private BackgroundActor background;

    public ScreenMenu(GameManager gameManager) {
        super(gameManager);
		atlasUI = new TextureAtlas("ui/buttons-pack.atlas");
		font = new BitmapFont(Gdx.files.internal("font/gothic.fnt"));
    }

    @Override
    public void initActors(){

		Gdx.input.setCatchBackKey(false);

		background = new BackgroundActor(new Texture("background/background_menu.jpg"), Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		stage.addActor(background);

		NinePatchDrawable buttonUp = new NinePatchDrawable(atlasUI.createPatch("menu_button_up"));
		NinePatchDrawable buttonDown = new NinePatchDrawable(atlasUI.createPatch("menu_button_down"));
		SpriteDrawable knobSprite = new SpriteDrawable(atlasUI.createSprite("slider"));

		Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
		buttonStyle.down = new SpriteDrawable(atlasUI.createSprite("options_down"));
		buttonStyle.up = new SpriteDrawable(atlasUI.createSprite("options"));

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.down = buttonDown;
		textButtonStyle.up = buttonUp;
		textButtonStyle.pressedOffsetX = 3;
		textButtonStyle.pressedOffsetY = -3;
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.MAGENTA;

		buttonPlay = new TextButton("Play", textButtonStyle);
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
				gameManager.gotoGame();

			}
		});

		buttonExit = new TextButton("Quit", textButtonStyle);
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
				Gdx.app.exit();
			}
		});

		buttonOptions = new Button(buttonStyle);
		buttonOptions.setWidth(Constants.WORLD_WIDTH * 0.2f);
		buttonOptions.setHeight(Constants.WORLD_WIDTH * 0.2f);
		buttonOptions.setPosition(Constants.WORLD_WIDTH - buttonOptions.getWidth() * 1.1f, Constants.WORLD_HEIGHT - buttonOptions.getHeight() * 1.1f);
		buttonOptions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.input.vibrate(Constants.CLICK_VIBRATE_DURATION);
				gameManager.gotoOptions();
			}
		});


		menuTable = new Table();
		menuTable.center();
		menuTable.setFillParent(true);

		menuTable.setDebug(Constants.DEBUG);

		menuTable.add(buttonPlay).width(Constants.WORLD_WIDTH * 0.7f).height(Constants.WORLD_HEIGHT * 0.2f);
		menuTable.row();
		menuTable.add(buttonExit).width(Constants.WORLD_WIDTH * 0.7f).height(Constants.WORLD_HEIGHT * 0.2f);

		stage.addActor(buttonOptions);

		stage.addActor(menuTable);

    }

	@Override
	protected void update() {

	}

}
