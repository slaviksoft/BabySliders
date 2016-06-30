package custom;

import com.badlogic.gdx.Game;
import com.slaviksoft.games.babysliders.ScreenGame;
import com.slaviksoft.games.babysliders.ScreenMenu;
import com.slaviksoft.games.babysliders.ScreenOptions;
import com.slaviksoft.games.babysliders.ScreenSplash;

/**
 * Created by Slavik on 12.08.2015.
 */
public class GameManager {

    private Game game;
    private IAdRequestHandler adRequestHandler;

    public GameManager(Game game, IAdRequestHandler adRequestHandler){
        this.game = game;
        this.adRequestHandler = adRequestHandler;
    }

    public GameManager(Game game){
        this.game = game;
    }

    public void gotoMenu(){
        if (adRequestHandler != null) adRequestHandler.showAds(true);
        game.setScreen(new ScreenMenu(this));
    }

    public void gotoOptions() {
        if (adRequestHandler != null) adRequestHandler.showAds(true);
        game.setScreen(new ScreenOptions(this));
    }

    public void gotoGame() {
        if (adRequestHandler != null) adRequestHandler.showAds(false);
        game.setScreen(new ScreenGame(this));
    }

    public void gotoSplash(){
        if (adRequestHandler != null) adRequestHandler.showAds(true);
        game.setScreen(new ScreenSplash(this));
    }

}
