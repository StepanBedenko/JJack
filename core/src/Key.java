import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Key extends BaseActor{

    public Key(float x, float y, Stage s){
        super(x,y,s);
        loadTexture("items/key.png");

        rotateBy(10);

        SequenceAction tilt = Actions.sequence(
                Actions.rotateBy(-20, 0.5f),
                Actions.rotateBy(20,0.5f));

        addAction(Actions.forever(tilt));
    }
}
