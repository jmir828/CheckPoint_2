
        
        /*******************************************************************************
 * file: Program2.java author: Josue Miramontes 
 * class: CS 445
 * 
 * assignment: Checkpoint 1 
 * date last modified: 11/5/2015
 * 
 * purpose: 
 *
 ******************************************************************************/
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {
    private Vector3f position = null; //3d vector to store the camera's position in
    private Vector3f lPosition = null;
    private float yaw = -100.0f;         //the rotation around the Y axis of the camera
    private float pitch = 30.0f;       //the rotation around the X axis of the camera
    private float angle = 0;
    private Chunk chunk = new Chunk(0,0,0);

    // constructor: FPCameraController
    // purpose: initialize an instance of FPCameraController given the provided.
    public FPCameraController(float x, float y, float z) {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(-30, -40, 0);
        lPosition = new Vector3f(0,0,0);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }

    // method: yaw
    // purpose: increment the camera's current yaw rotation
    public void yaw(float amount) {
        //increment the yaw by the amount param
        yaw += amount;
    }
    
    // method: pitch
    // purpose: increment the camera's current yaw rotation
    public void pitch(float amount) {
        //increment the pitch by the amount param
        pitch -= amount;
    }

    // method: walkForward
    // purpose: moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: walkBackwards
    // purpose: moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }

    // method: strafeLeft
    // purpose: strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: strafeRight
    // purpose: strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: moveUp
    // purpose: moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance) { position.y -= distance;
    }

    // method: moveDown
    // purpose: moves the camera down
    public void moveDown(float distance) { position.y += distance; }

    // method: lookThrough
    // purpose: translates and rotate the matrix so that it looks through the 
    // camera this does basically what gluLookAt() does
    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f); //roatate the pitch around the X axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);   //roatate the yaw around the Y axis
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
    // method gameLoop: 
    // purpose: This method is the input controller. Based on input taken
    // from the keyboard, this method will adjust the camera within the program
    // accordingly and re-render the movement.
    public void gameLoop() {
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;                //length of frame
        float lastTime = 0.0f;          // when the last frame was
        long time = 0;
        camera.yaw = -180; //Postitons Camera
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        Mouse.setGrabbed(true);         //hide the mouse

        // keep looping till the display window is closed the ESC key is down
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            //distance in mouse movement from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement from the last getDY() call.
            dy = Mouse.getDY();
            
            //controll camera yaw from x movement fromt the mouse
            camera.yaw(dx * mouseSensitivity);
            //controll camera pitch from y movement fromt the mouse
            camera.pitch(dy * mouseSensitivity);
           
            //when passing in the distance to move
            //we times the movementSpeed with dt this is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
                camera.walkForward(movementSpeed);

            if (Keyboard.isKeyDown(Keyboard.KEY_S))     //move backwards
                camera.walkBackwards(movementSpeed);

            if (Keyboard.isKeyDown(Keyboard.KEY_A))     //strafe left 
                camera.strafeLeft(movementSpeed);

            if (Keyboard.isKeyDown(Keyboard.KEY_D))     //strafe right 
                camera.strafeRight(movementSpeed);

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) //move up 
                camera.moveUp(movementSpeed);

            if (Keyboard.isKeyDown(Keyboard.KEY_E))     // move down
                camera.moveDown(movementSpeed);

            glLoadIdentity();       // set the modelview matrix back to the identity
            camera.lookThrough();   // look through the camera before you draw anything
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            chunk.render();               //you would draw your scene here.
            Display.update();       //draw the buffer to the screen
            Display.sync(60);
        }
        Display.destroy();
    }

}

