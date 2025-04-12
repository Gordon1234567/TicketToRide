public class line {

    // This class will contain all the info the game need for individual train lines

    // The color of cards needed
    private String color;
    // the number of cards needed
    private int trains;
    // the number of points the line is worth
    private int points;
    // whether the line has been filled or not
    private boolean full;

    public line(String Color, int Trains){
        color = Color;
        trains = Trains;
        if(Trains == 1){points = 1;}
        else if(Trains == 2){points = 2;}
        else if(Trains == 3){points = 4;}
        else if(Trains == 4){points = 7;}
        else if(Trains == 5){points = 10;}
        else if(Trains == 6){points = 15;}
        full = false;
    }

    public int getTrains(){
        return trains;
    }

    public int getPoints(){
        return points;
    }

    public String getColor(){
        return color;
    }

    public boolean getFull(){
        return full;
    }

    public void setFull(){
        if (full){
            full = false;
        }else{
            full = true;
        }
    }

}
