public class line {

    private String color;
    private int trains;
    private int points;
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
}
