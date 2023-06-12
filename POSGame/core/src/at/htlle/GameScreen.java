package at.htlle;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
/**
 * ===========================================================================
 * Main menu screen of the game.
 *
 * This screen is displayed when the game starts and provides options to start
 * the game or adjust settings.
 *
 * @author Karo Wieser
 * Version: Pre-Pre Alpha
 * Date: [29.03.23]
 * ===========================================================================
 */
public class GameScreen implements Screen {

    final Start game;

    Texture dropImage;
    Texture klausi;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle klausiRec;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    boolean paused = false;
    float x = Gdx.graphics.getWidth();
    float y = Gdx.graphics.getHeight();
    float jumpHeight = 75;
    float jumpSpeed = 1;
    float jumpTime = 1;
    float jumpOffset;

    public GameScreen(final Start game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        klausi = new Texture(Gdx.files.internal("Klausi64x64.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, x, y);

        // create a Rectangle to logically represent klausi
        klausiRec = new Rectangle();
        klausiRec.x = x / 2 - 64 / 2; // center klausi horizontally
        klausiRec.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        klausiRec.width = 64;
        klausiRec.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, x - 64);
        raindrop.y = y - 64;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            paused = !paused;
        }

        if (paused) {
            // Clear the screen with a gray color
            ScreenUtils.clear(0.5f, 0.5f, 0.5f, 0);
            rainMusic.stop();
            // Display a "PAUSED" message
            game.batch.begin();
            //TODO fix das mit dem String du kecheck, ka mach entweder Bild Raus oder find raus wie du die mitte des Strings erreichst
            game.font.draw(game.batch, "PAUSED", x/2, y/2);
            game.batch.end();
        }else{
            rainMusic.play();
            // clear the screen with a dark blue color. The
            // arguments to clear are the red, green
            // blue and alpha component in the range [0,1]
            // of the color to be used to clear the screen.
            ScreenUtils.clear(0, 0, 0.2f, 1);

            // tell the camera to update its matrices.
            camera.update();

            // tell the SpriteBatch to render in the
            // coordinate system specified by the camera.
            game.batch.setProjectionMatrix(camera.combined);

            // begin a new batch and draw the bucket and
            // all drops
            game.batch.begin();
            game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, y-64);
            game.batch.draw(klausi, klausiRec.x, klausiRec.y);
            for (Rectangle raindrop : raindrops) {
                game.batch.draw(dropImage, raindrop.x, raindrop.y);
            }
            game.batch.end();

            // process user input
            if (Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                klausiRec.x = touchPos.x - 64 / 2;
            }
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                klausiRec.x -= 400 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                klausiRec.x += 400 * Gdx.graphics.getDeltaTime();

            //make Klausi jump with a sin curve
            jumpTime += Gdx.graphics.getDeltaTime();
            double w = 2 * Math.PI * jumpSpeed;
            jumpOffset = (float) ((float) jumpHeight + Math.sin(jumpTime * w) * jumpHeight);

            // Update klausi position
            klausiRec.y = 20 + jumpOffset;

            // make sure klausi stays within the screen bounds
            if (klausiRec.x < 0)
                klausiRec.x = 0;
            if (klausiRec.x > x - 64)
                klausiRec.x = x - 64;

            // check if we need to create a new raindrop
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
                spawnRaindrop();

            // move the raindrops, remove any that are beneath the bottom edge of
            // the screen or that hit the bucket. In the later case we play back
            // a sound effect as well.
            Iterator<Rectangle> iter = raindrops.iterator();
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next();
                raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
                if (raindrop.y + 64 < 0)
                    iter.remove();
                if (raindrop.overlaps(klausiRec)) {
                    dropsGathered++;
                    dropSound.play();
                    iter.remove();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        klausi.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
