import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new JumpingJackGame();
		LwjglApplication launcher = new LwjglApplication(myGame, "Jumping Jack Game", 800, 640);
	}
}
