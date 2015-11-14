/*******************************************************************************
 * file: Program2.java author: Josue Miramontes 
 * class: CS 445
 * 
 * assignment: Checkpoint 1 
 * date last modified: 11/5/2015
 * 
 * purpose: The purpose of this program is to initialize the display needed to
 *          run the rest of the program. 
 *
 ******************************************************************************/
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class CheckPt_2 {
    private static FPCameraController fp;
    private DisplayMode displayMode = new DisplayMode(640,480);

    // method: start
    // purpose: this method acts as a psuedo-driver for the program. It calls
    // some the methods that provide critical information and initialization 
    // for the program.
    public void start() {
        try {
            createWindow();
            initGL();
            fp.gameLoop();//render();
            } catch (Exception e) { e.printStackTrace(); }
    }
    
    // method: createWindow
    // purpose: this method initializes and provides the size of the window for
    // use throughtout the remainder of the program. The Keyboard is then linked
    // to the program in order to give additional functionality at the users
    // request (i.e. press 'ESC' key to terminate program)
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();

        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }

        Display.setDisplayMode(displayMode);
        Display.setTitle("Rotating Cube - CheckPoint 1");
        Display.create();
    }
    
    // method: initGL
    // purpose: this method provides additional internal details for the window, 
    // such as the mode to be used, background color and how to display the
    // pixels being mapped to the display window. coordinate system set to origin.
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
        displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        // For storing Block Data
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        // For Texture Mapping
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
    }
    
    // method: main
    // purpose: this method creates an instance of ThreeDCube and calls the start
    // method, initiating the bulk of the program
    public static void main(String[] args) {
        fp = new FPCameraController(0f,0f,0f);
        CheckPt_2 basic = new CheckPt_2();
        basic.start();
    }
    
}