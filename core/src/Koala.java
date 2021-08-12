import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Koala extends BaseActor{
    private Animation stand;
    private Animation walk;
    private Animation jump;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;
    private float jumpSpeed;

    private BaseActor belowSensor;

    public Koala(float x, float y, Stage s){
        super(x,y,s);

        jump = loadTexture("koala/jump.png");

        stand = loadTexture("koala/stand.png");

        String[] walkFileNames = {"koala/walk-1.png","koala/walk-2.png","koala/walk-3.png","koala/walk-2.png"};

        walk = loadAnimationFromFiles(walkFileNames, 0.2f,true);

        maxHorizontalSpeed = 150;
        walkAcceleration = 300;
        walkDeceleration = 300;
        gravity = 900;
        maxVerticalSpeed = 1000;

        jumpSpeed = 450;
        setBoundaryPolygon(6);
        belowSensor = new BaseActor(0,0, s);
        belowSensor.loadTexture("white.png");
        belowSensor.setSize(this.getWidth() - 8, 8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(true);


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

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);
        accelerationVec.set(0,0);

        alignCamera();
        boundToWorld();

        if(this.isOnSolid()){
            belowSensor.setColor(Color.GREEN);
            if(velocityVec.x == 0)
                setAnimation(stand);
            else
                setAnimation(walk);
        }
        else{
            belowSensor.setColor(Color.RED);
            setAnimation(jump);
        }

        if(velocityVec.x > 0)
            setScaleX(1);
        if(velocityVec.x < 0)
            setScaleX(-1);

        belowSensor.setPosition(getX() + 4, getY() - 8);

    }

    public boolean belowOverlaps(BaseActor actor){
        return belowSensor.overlaps(actor);
    }

    public boolean isOnSolid(){
        for(BaseActor actor : BaseActor.getList(getStage(),"Solid")) {
            Solid solid = (Solid) actor;
            if (belowOverlaps(solid) && solid.isEnabled())
                return true;
        }
            return false;
    }

    public void jump(){
        velocityVec.y = jumpSpeed;
    }

    public boolean isFalling(){
        return(velocityVec.y < 0);
    }

    public boolean isJumping(){
       return (velocityVec.y > 0);
    }

    public void spring(){
        velocityVec.y = 1.5f * jumpSpeed;
    }

}
