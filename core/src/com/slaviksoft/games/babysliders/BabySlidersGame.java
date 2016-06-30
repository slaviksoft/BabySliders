package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import custom.GameManager;
import custom.IAdRequestHandler;

public class BabySlidersGame extends Game{

	private final String TAG = getClass().getSimpleName();
	private GameManager gameManager;
	//private IAdRequestHandler adRequestHandler;

	public BabySlidersGame(){
		gameManager = new GameManager(this);
	}

	public BabySlidersGame(IAdRequestHandler adRequestHandler){
		gameManager = new GameManager(this, adRequestHandler);
	}

	@Override
	public void create () {
		Gdx.app.log(TAG, "create");
		//gameManager.gotoMenu();
		gameManager.gotoSplash();
	}


}
