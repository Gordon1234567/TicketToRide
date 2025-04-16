//import java.util.ArrayList;
//import java.util.Scanner;
//
///**
// * This will run one turn, asking what the player wants to do either playing trains or picking up cards and trying to do that
// */
//public class Turn {
//
//    private Hand player;
//
//    private Deck deck;
//
//    public Turn(Hand x, Deck deck){
//        player = x;
//        deck = deck;
//    }
//
//    public static void main(String[] args) {
//        Scanner playerIn = new Scanner(System.in);
//
//        System.out.println("1 - Pick up cards" +
//                "\n2 - Place trains" +
//                "\n3 - Check your points" +
//                "\n4 - Check your trains");
//        int choice = playerIn.nextInt();
//
//        if(choice == 1){
//            getCards();
//        }
//
//    }
//    /**
//     * This will ask the player if they want to pick up cards or place trains,
//     * They can also choose to check their points and how many trains they have left
//     * @return
//     */
//
//    /**
//     * This will be given the line that the player wants to play, it will try to play trains there
//     * @param line is the line the player wants to play on
//     */
//    public void playTrain(String line, Hand x){
//
//    }
//
//    /**
//     * This will put more cards in the players hand form the deck
//     */
//    public void getCards(){
//        player.pickupCards();
//    }
//
//    /**
//     * Shows how many points they have
//     */
//    public void checkPoints(Hand x){
//
//    }
//
//    /**
//     * Check how many trains they have
//     */
//    public void checkTrains(Hand x){
//
//    }
//}
