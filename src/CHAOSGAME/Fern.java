package CHAOSGAME;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class Fern extends Frame implements GLEventListener {

    double size = 0.2;

    GLProfile profile;
    GLCanvas canvas;
    GLCapabilities capabilities;
    FPSAnimator animator;
    GL2 gl;

    LinkedList<Coordinates> points = new LinkedList<>();
    Coordinates bufferpoint;

    public Fern(){
        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        animator = new FPSAnimator(canvas,300,true);

        bufferpoint = new Coordinates(0,0);
        points.add(bufferpoint);

        this.setVisible(true);
        this.add(canvas);
        this.setSize(d.height,d.height);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        //gl.glClear(gl.GL_DEPTH_BUFFER_BIT|gl.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3d(0,1,0);
        gl.glVertex3d(size*bufferpoint.x,size*bufferpoint.y-1.01,0);

        /*
        for(int i=0;i<points.size();i++){
            gl.glVertex3d(points.get(i).x,points.get(i).y,0);
        }*/

        addPoint();
        if(iter>100000){
            animator.stop();
        }
        gl.glEnd();
        gl.glFlush();
    }
    int iter=0;
    void addPoint(){
        double random = Math.random();
        double temp,a,b,c,d,e,f;
        if(random < 0.85){
            a = 0.85;
            b = 0.04;
            c = -0.04;
            d = 0.85;
            e = 0;
            f = 1.60;
        }else if(random < 0.92){
            a = 0.2;
            b = -0.26;
            c = 0.23;
            d = 0.22;
            e = 0;
            f = 1.60;
        }else if(random<0.99){
            a = -0.15;
            b = 0.28;
            c = 0.26;
            d = 0.24;
            e = 0;
            f = 0.44;
        }else {
            a = 0;
            b = 0;
            c = 0;
            d = 0.16;
            e = 0;
            f = 0;
        }
        temp = bufferpoint.x;
        bufferpoint.x = (a*bufferpoint.x)+(b*bufferpoint.y)+e;
        bufferpoint.y = (c*temp)+(d*bufferpoint.y)+f;
        //bufferpoint.y-=1.5;
        points.add(new Coordinates(size*bufferpoint.x,size*bufferpoint.y-1));

        System.out.println("                                                                                                        "+iter++);
    }

    double euler(double x1,double y1,double x2,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    Coordinates xy2polar(double x,double y){
        Coordinates c = new Coordinates(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)),Math.atan2(y,x));
        return c;
    }

    Coordinates polar2xy(double r,double theta){
        Coordinates c = new Coordinates(r*Math.cos(theta),r*Math.sin(theta));
        return c;
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
