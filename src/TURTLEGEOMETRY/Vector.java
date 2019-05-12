package TURTLEGEOMETRY;

public class Vector {
    Point p1;
    Point p2;

    Vector(Point p1,Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public double mod(){
        double modulus = Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2)+Math.pow(p1.z-p2.z,2));
        return modulus;
    }

    double x(){
        return (p2.x-p1.x);
    }

    double y(){
        return (p2.y-p1.y);
    }

    double z(){
        return (p2.z-p1.z);
    }

}
