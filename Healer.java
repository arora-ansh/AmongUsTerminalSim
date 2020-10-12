import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Healer extends Player {

    Scanner in = new Scanner(System.in);

    public Healer(int _playerNumber, boolean _isUser,Game _theGame) {
        super(_playerNumber, _isUser, _theGame);
        HP = 800;
    }

    @Override
    public int specialMove(ArrayList<Player> alivePlayers, ArrayList<Player> mafiaPlayers) {
        int healerChoice = -1;
        boolean healerValid = false;
        while(!healerValid) {
            try {
                System.out.print("Choose a Player to Heal: ");
                healerChoice = in.nextInt();//This holds player number
            }
            catch(InputMismatchException e){
                System.out.println("Enter a valid integer for player");
                in.nextLine();
            }
            for (Player alivePlayer : alivePlayers) {
                if(alivePlayer.getPlayerNumber() == healerChoice && (alivePlayer instanceof Healer)){
                    System.out.print("You cannot choose a Healer. ");
                }
                else if(alivePlayer.getPlayerNumber() == healerChoice) {
                    //HEALER ACTIONS HERE WHOLE HP THING
                    alivePlayer.setHP(alivePlayer.getHP()+500);
                    healerValid = true;
                    break;
                }
            }
        }
        return healerChoice;
    }
}
