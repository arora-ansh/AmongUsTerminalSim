import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Detective extends Player {

    Scanner in = new Scanner(System.in);

    public Detective(int _playerNumber,boolean _isUser,Game _theGame) {
        super(_playerNumber, _isUser,_theGame);
        HP = 800;
    }

    @Override
    public int specialMove(ArrayList<Player> alivePlayers, ArrayList<Player> mafiaPlayers) {
        int detectiveChoice = -2;
        boolean detectiveValid = false;
        while(!detectiveValid) {
            try {
                System.out.print("Choose a player to Test: ");
                detectiveChoice = in.nextInt();//This holds player number
            }
            catch(InputMismatchException e){
                System.out.println("Enter a valid integer for player");
                in.nextLine();
            }
            for (Player alivePlayer : alivePlayers) {
                if(alivePlayer.getPlayerNumber() == detectiveChoice && (alivePlayer instanceof Detective)){
                    System.out.print("You cannot test a Detective. ");
                }
                else if(alivePlayer.getPlayerNumber() == detectiveChoice) {
                    detectiveValid = true;
                    if(alivePlayer instanceof Mafia){
                        System.out.println(alivePlayer.toString()+" is Mafia.");
                        theGame.setMafiaIdentified(true);
                    }
                    else{
                        System.out.println(alivePlayer.toString()+" is not a Mafia.");
                    }
                    break;
                }
            }
        }
        return detectiveChoice;
    }
}
