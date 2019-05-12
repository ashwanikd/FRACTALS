package TURTLEGEOMETRY;

import CHAOSGAME.Edge;
import CHAOSGAME.Triangle;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Test extends Frame implements GLEventListener {

    int ITERATIONS = 500;
    static GLCanvas canvas;
    static GLCapabilities capabilities;
    static GLProfile profile;
    static FPSAnimator animator;
    Vector v;

    LinkedList<Vector> vectors;
    public Test(double x1,double y1,double distance,double deltaD,double angle,double deltaA,int iterations){
        ITERATIONS = iterations;
        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                animator.stop();
                System.exit(0);
            }
        });

        v = new Vector(new Point(x1,y1,0),new Point(x1,y1,0));
        this.distance = distance;
        this.turn = angle;
        this.deltaD = deltaD;
        this.deltaA = deltaA;
        vectors = new LinkedList<>();
        vectors.add(new Vector(new Point(v.p1.x,v.p1.y,v.p1.z),new Point(v.p2.x,v.p2.y,v.p2.z)));

        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        animator = new FPSAnimator(canvas,300,true);
        this.add(canvas);
        this.setSize(800,800);
        this.setVisible(true);
        animator.start();
    }
    double turn;
    double angle=0,deltaD,deltaA,distance;

    void nextLine(){
        turn(distance,angle);
        distance+=deltaD;
        angle+=deltaA;
        angle+=turn;
    }

    void turn(double distance,double angle){
        v.p1.x = v.p2.x;
        v.p1.y = v.p2.y;
        v.p2.x += distance*Math.cos(angle);
        v.p2.y += distance*Math.sin(angle);
        vectors.add(new Vector(new Point(v.p1.x,v.p1.y,v.p1.z),new Point(v.p2.x,v.p2.y,v.p2.z)));
    }

    void turnLeft(double distance,double theta){
        double x1,y1,x2,y2;
        x1 = distance*Math.sin(theta)*v.y()/v.mod();
        if(v.y()!=0) {
            y1 = -v.x()*x1/v.y();
        }else{
            y1 = Math.sqrt(Math.pow(distance*Math.sin(theta),2)-Math.pow(x1,2));
        }
        if((x1*v.y()-y1*v.x())>0){
            x1=-x1;
            y1=-y1;
        }
        x2 = -v.x()*distance*Math.cos(theta)/v.mod();
        y2 = -v.y()*distance*Math.cos(theta)/v.mod();

        double x = x1+x2,y = y1+y2;
        x += v.p2.x;
        y += v.p2.y;

        v.p1.x = v.p2.x;
        v.p1.y = v.p2.y;
        v.p2.x = x;
        v.p2.y = y;

        vectors.add(new Vector(new Point(v.p1.x,v.p1.y,v.p1.z),new Point(v.p2.x,v.p2.y,v.p2.z)));

    }

    void turnRight(double distance,double theta){
        double x1,y1,x2,y2;
        x1 = distance*Math.sin(theta)*v.y()/v.mod();
        if(v.y()!=0) {
            y1 = -v.x()*x1/v.y();
        }else{
            y1 = Math.sqrt(Math.pow(distance*Math.sin(theta),2)-Math.pow(x1,2));
        }
        if((x1*v.y()-y1*v.x())<0){
            x1=-x1;
            y1=-y1;
        }
        x2 = -v.x()*distance*Math.cos(theta)/v.mod();
        y2 = -v.y()*distance*Math.cos(theta)/v.mod();

        double x = x1+x2,y = y1+y2;
        x += v.p2.x;
        y += v.p2.y;

        v.p1.x = v.p2.x;
        v.p1.y = v.p2.y;
        v.p2.x = x;
        v.p2.y = y;

        vectors.add(new Vector(new Point(v.p1.x,v.p1.y,v.p1.z),new Point(v.p2.x,v.p2.y,v.p2.z)));
    }

    TextRenderer renderer;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(gl.GL_DEPTH_BUFFER_BIT|gl.GL_COLOR_BUFFER_BIT);
        //gl.glPointSize(10);
        gl.glBegin(GL2.GL_LINES);

        Vector v;
        Iterator iterator = vectors.iterator();
        while(iterator.hasNext()){
            v = (Vector) iterator.next();
            gl.glVertex3d(v.p1.x,v.p1.y,v.p1.z);
            gl.glVertex3d(v.p2.x,v.p2.y,v.p2.z);
        }

        gl.glEnd();

        if(ITERATIONS==0)
            animator.stop();
        nextLine();
        ITERATIONS--;
        gl.glLoadIdentity();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
