package CHAOSGAME;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChaosGame3 extends Frame implements GLEventListener {

    GLProfile profile;
    GLCanvas canvas;
    GLCapabilities capabilities;
    FPSAnimator animator;
    GL2 gl;

    Coordinates bufferpoint;
    Coordinates[] vertices;
    double deltaD;
    double r=0.5;
    int n;
    COLOR c = new COLOR();
    double rotrad;

    void initializeVertices(){
        for(int i=0;i<n;i++){
            vertices[i] = new Coordinates(r*Math.cos(((2*Math.PI*(double)(i))/n)+(Math.PI/2)),r*Math.sin(((2*Math.PI*(double)(i))/n)+(Math.PI/2)));
        }
    }

    public ChaosGame3(int numvertices,double deltaD,double rotation){
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

        vertices = new Coordinates[numvertices];
        n = numvertices;
        this.deltaD = deltaD;
        initializeVertices();
        bufferpoint = new Coordinates(0,0);
        c.r=0;c.g=0;c.b=0;
        rotrad = rotation;

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
    double wl;
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        //gl.glClear(gl.GL_DEPTH_BUFFER_BIT|gl.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3d(c.r,c.g,c.b);
        gl.glVertex3d(bufferpoint.x,bufferpoint.y,0);

        /*
        for(int i=0;i<points.size();i++){
            gl.glVertex3d(points.get(i).x,points.get(i).y,0);
        }
        for(int i=0;i<n;i++){
            gl.glVertex3d(vertices[i].x,vertices[i].y,0);
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
        int random = (int)(Math.random()*n);
        double x = bufferpoint.x+(vertices[random].x-bufferpoint.x)*deltaD;
        double y = bufferpoint.y+(vertices[random].y-bufferpoint.y)*deltaD;
        x-=vertices[random].x;
        y-=vertices[random].y;
        Coordinates c = xy2polar(x,y);
        c.y+=rotrad;
        c = polar2xy(c.x,c.y);
        x = c.x+vertices[random].x;
        y = c.y+vertices[random].y;
        double d = euler(x,y,bufferpoint.x,bufferpoint.y);
        bufferpoint.x=x;
        bufferpoint.y=y;
        double wl = 400+(300*d/r);
        wl2color(wl);
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

    void wl2color(double wl){

        if(wl>=400 && wl<=440){
            c.r = -1*((wl-440)/40);
            c.g = 0;
            c.b = 1;
        }else if(wl>=440 && wl<=490){
            c.r = 0;
            c.g = (wl-440)/50;
            c.b = 1;
        }else if(wl>=490 && wl<=510){
            c.r = 0;
            c.g = 1;
            c.b = -1*((wl-510)/20);
        }else if(wl>=510 && wl<=580){
            c.r = (wl-510)/70;
            c.g = 1;
            c.b = 0;
        }else if(wl>=580 && wl<=645){
            c.r = 1;
            c.g = -1*((wl-645)/65);
            c.b = 0;
        }else if(wl>=645 && wl<=700){
            c.r = 1;
            c.g = 0;
            c.b = 0;
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
