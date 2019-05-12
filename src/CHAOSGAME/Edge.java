package CHAOSGAME;

public class Edge {
    double x1,y1,x2,y2;
    Edge(double x1,double y1,double x2,double y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    double distance(){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
