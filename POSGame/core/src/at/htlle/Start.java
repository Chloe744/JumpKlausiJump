package at.htlle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * =======================================================================
 * Main class for "Jump Klausi Jump".
 *
 * This class extends the LibGDX Game class and sets up the game's
 * SpriteBatch and BitmapFont. It also creates the main menu screen and
 * manages rendering and disposing of game assets.
 *
 * @author Karo Wieser
 * Version: Pre-Pre Alpha
 * Date: [29.03.23]
 * =======================================================================
 */
public class Start extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
