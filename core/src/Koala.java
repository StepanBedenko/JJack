import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Koala extends BaseActor{
    private Animation stand;
    private Animation walk;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    public Koala(float x, float y, Stage s){
        super(x,y,s);

        stand = loadTexture("stand.png");

        String[] walkFileNames = {"walk-1.png","walk-2.png","walk-3.png","walk-2.png"};

        walk = loadAnimationFromFiles(walkFileNames, 0.2f,true);

        maxHorizontalSpeed = 100;
        walkAcceleration = 200;
        walkDeceleration = 200;
        gravity = 700;
        maxVerticalSpeed = 1000;
    }

    public void act(float dt){
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            accelerationVec.add(-walkAcceleration,0);


        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            accelerationVec.add(walkAcceleration,0);

        accelerationVec.add(0,-gravity);

        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            float decelerationAmount = walkDeceleration * dt;

            float walkDirection;

            if (velocityVec.x > 0)
                walkDirection = 1;
            else
                walkDirection = -1;

            float walkSpeed = Math.abs(velocityVec.x);

            walkSpeed -= decelerationAmount;

            if(walkSpeed < 0)
                walkSpeed = 0;
            velocityVec.x = walkSpeed * walkDirection;
        }


    }


}
