import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Koala extends BaseActor{
    private Animation stand;
    private Animation walk;

    public Koala(float x, float y, Stage s){
        super(x,y,s);

        stand = loadTexture("stand.png");

        String[] walkFileNames = {"walk-1.png","walk-2.png","walk-3.png","walk-2.png"};

        walk = loadAnimationFromFiles(walkFileNames, 0.2f,true);
    }
}
