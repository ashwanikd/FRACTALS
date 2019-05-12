package CHAOSGAME;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class LMST extends Frame implements GLEventListener{
    double scale = 0.8;
    static GL2 gl;
    double x1 = 0*scale,y1=1*scale;
    double x2 = Math.cos(Math.PI/6)*scale, y2 = -1*Math.sin(Math.PI/6)*scale;
    double x3 = -1*Math.cos(Math.PI/6)*scale, y3 = -1*Math.sin(Math.PI/6)*scale;
    LinkedList<Triangle> queue;
    static GLCanvas canvas;
    LinkedList<Edge> edges;
    static GLCapabilities capabilities;
    static GLProfile profile;
    static FPSAnimator animator;
    public LMST(){
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
        queue = new LinkedList<>();
        edges = new LinkedList<>();
        queue.add(new Triangle(x1,y1,x2,y2,x3,y3));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        animator = new FPSAnimator(canvas,300,true);
        this.add(canvas);
        this.setSize(d);
        this.setVisible(true);
        animator.start();
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
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(gl.GL_DEPTH_BUFFER_BIT|gl.GL_COLOR_BUFFER_BIT);
        //gl.glPointSize(10);
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1,1,1);
        for(int i=0;i<edges.size();i++){
            gl.glVertex3d(edges.get(i).x1,edges.get(i).y1,0);
            gl.glVertex3d(edges.get(i).x2,edges.get(i).y2,0);
        }
        gl.glEnd();
        addPoint();
        gl.glFlush();
    }

    void addPoint(){
        Triangle t1 = queue.getFirst();
        edges.add(new Edge(t1.x1,t1.y1,t1.x2,t1.y2));
        edges.add(new Edge(t1.x1,t1.y1,t1.x3,t1.y3));
        edges.add(new Edge(t1.x3,t1.y3,t1.x2,t1.y2));
        queue.add(new Triangle((t1.x1+t1.x2)/2,(t1.y1+t1.y2)/2,(t1.x1+t1.x3)/2,(t1.y1+t1.y3)/2,t1.x1,t1.y1));
        queue.add(new Triangle((t1.x1+t1.x2)/2,(t1.y1+t1.y2)/2,t1.x2,t1.y2,(t1.x3+t1.x2)/2,(t1.y3+t1.y2)/2));
        queue.add(new Triangle(t1.x3,t1.y3,(t1.x1+t1.x3)/2,(t1.y1+t1.y3)/2,(t1.x3+t1.x2)/2,(t1.y3+t1.y2)/2));
        queue.remove(t1);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

}
