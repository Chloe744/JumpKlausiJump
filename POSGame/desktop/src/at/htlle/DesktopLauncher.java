package at.htlle;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
/**
 * =======================================================================
 * Desktop Launcher for "Jump Klausi Jump".
 *
 * This class sets up the configuration for the LibGDX game and starts
 * the game on the desktop platform.
 *
 * @author Karo Wieser
 * Version: Pre-Pre Alpha
 * Date: [29.03.23]
 * =======================================================================
 */
public class DesktopLauncher {
	public static void main(String[] arg) {

		// Create new configuration instance
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Set foreground FPS to 60
		config.setForegroundFPS(60);

		// Set the window title to "Drop"
		config.setTitle("JumpKlausiJump");

		// Set the display mode to fullscreen
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		// Use Vsync to sync display with monitor refresh rate
		config.useVsync(true);

		// Create new instance of the game and start it
		new Lwjgl3Application(new Start(), config);
	}
}
