package at.htlle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
/**
 * ===========================================================================
 * Main menu screen of the game.
 *
 * This screen is displayed when the game starts and provides options to start
 * the game or adjust settings.
 *
 * @author Karo Wieser
 * Version: Pre-Pre Alpha
 * Date: [13.04.23]
 * ===========================================================================
 */
public class MainMenuScreen implements Screen {

    final Start game;
    OrthographicCamera camera;
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    private Stage stage;

    // load textures for background image and buttons
    Texture backgroundImage = new Texture(Gdx.files.internal("mainmenu_without_buttons.png"));
    Texture buttonTexture = new Texture(Gdx.files.internal("startbutton.png"));
    Texture buttonPressedTexture = new Texture(Gdx.files.internal("startbuttonPressed.png"));
    Texture optionsButtonTexture = new Texture(Gdx.files.internal("optionsWip.png"));
    Texture optionsButtonPressedTexture = new Texture(Gdx.files.internal("optionsWipPressed.png"));
    Texture quitButtonTexture = new Texture(Gdx.files.internal("quitButtonWip.png"));
    Texture quitButtonPressedTexture = new Texture(Gdx.files.internal("quitButtonWipPressed.png"));

    // create an image button using the button textures
    TextureRegion buttonRegion = new TextureRegion(buttonTexture);
    ImageButton startButton = new ImageButton(new TextureRegionDrawable(buttonRegion),
            new TextureRegionDrawable(new TextureRegion(buttonPressedTexture))) {

        // override hit method to only allow touch events on enabled buttons
        @Override
        public Actor hit(float localX, float localY, boolean touchable) {
            if (touchable && getTouchable() != Touchable.enabled) return null;
            return localX >= 0 && localX < getWidth() && localY >= 0 && localY < getHeight() ? this : null;
        }
    };

    // create an image button using the button textures
    TextureRegion optionsButtonRegion = new TextureRegion(optionsButtonTexture);
    ImageButton optionsButton = new ImageButton(new TextureRegionDrawable(optionsButtonRegion),
            new TextureRegionDrawable(new TextureRegion(optionsButtonPressedTexture))) {
        // override hit method to only allow touch events on enabled buttons
        @Override
        public Actor hit(float localX, float localY, boolean touchable) {
            if (touchable && getTouchable() != Touchable.enabled) return null;
            return localX >= 0 && localX < getWidth() && localY >= 0 && localY < getHeight() ? this : null;
        }
    };
    // create an image button using the button textures
    TextureRegion quitButtonRegion = new TextureRegion(quitButtonTexture);
    ImageButton quitButton = new ImageButton(new TextureRegionDrawable(quitButtonRegion),
            new TextureRegionDrawable(new TextureRegion(quitButtonPressedTexture))) {
        // override hit method to only allow touch events on enabled buttons
        @Override
        public Actor hit(float localX, float localY, boolean touchable) {
            if (touchable && getTouchable() != Touchable.enabled) return null;
            return localX >= 0 && localX < getWidth() && localY >= 0 && localY < getHeight() ? this : null;
        }
    };

    // constructor
    public MainMenuScreen(final Start game) {
        this.game = game;
        // create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, x, y);
    }

    // render method
    @Override
    public void render(float delta) {
        // clear the screen and update camera
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // draw background image
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, x, y);
        game.batch.end();

        // update and draw stage
        stage.act();
        stage.draw();
    }

    // resize method
    @Override
    public void resize(int width, int height) {
    }

    // show method
    @Override
    public void show() {
        // create new stage and set input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // set button position and enable touch events
        float buttonX = x / 3 - buttonRegion.getRegionWidth() / 2;
        float buttonY = y / 4 - buttonRegion.getRegionHeight() / 2;
        startButton.setBounds(buttonX, buttonY, buttonRegion.getRegionWidth(), buttonRegion.getRegionHeight());
        startButton.setTouchable(Touchable.enabled);

        float optionsButtonX = 2 * x / 3 - optionsButtonRegion.getRegionWidth() / 2;
        float optionsButtonY = y / 4 - optionsButtonRegion.getRegionHeight() / 2;
        optionsButton.setBounds(optionsButtonX, optionsButtonY, optionsButtonRegion.getRegionWidth(), optionsButtonRegion.getRegionHeight());
        optionsButton.setTouchable(Touchable.enabled);

        // set button position and enable touch events
        float quitButtonX = x / 2 - quitButtonRegion.getRegionWidth() / 2;
        float quitButtonY = y / 8 - quitButtonRegion.getRegionHeight() / 2;
        quitButton.setBounds(quitButtonX, quitButtonY, quitButtonRegion.getRegionWidth(), quitButtonRegion.getRegionHeight());
        quitButton.setTouchable(Touchable.enabled);

        // add button to stage
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);

        // add click listener to button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // switch to game screen and dispose of this screen
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new MainOptions(game));
                System.out.println("Button!!");
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // exit the game
            }
        });

    }


    // Called when the screen is no longer the current screen for the Game
    @Override
    public void hide() {
        // Do nothing in this implementation
    }

    // Called when the Game is paused
    @Override
    public void pause() {
        // Do nothing in this implementation
    }

    // Called when the Game is resumed from a paused state
    @Override
    public void resume() {
        // Do nothing in this implementation
    }

    // Called when the screen is disposed
    @Override
    public void dispose() {
        // Dispose the textures and the stage to free up memory
        backgroundImage.dispose();
        buttonTexture.dispose();
        buttonPressedTexture.dispose();
        optionsButtonTexture.dispose();
        optionsButtonPressedTexture.dispose();
        quitButtonTexture.dispose();
        quitButtonPressedTexture.dispose();
        stage.dispose();
    }
}