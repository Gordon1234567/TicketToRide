
public class rout implements cardInterface {
    private int point1;
    private int point2;
    private int points;

    public rout(int point1, int point2, int points){
        this.point1 = point1;
        this.point2 = point2;
        this.points = points;
    }

    public int getPoint1(){
        return point1;
    }

    public int getPoints() {
        return points;
    }

    public int getPoint2(){
        return point2;
    }


}
