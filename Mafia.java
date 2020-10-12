import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Mafia extends Player {

    Scanner in = new Scanner(System.in);

    public Mafia(int _playerNumber, boolean _isUser, Game _theGame) {
        super(_playerNumber, _isUser, _theGame);
        HP = 2500;
    }


    @Override
    public int specialMove(ArrayList<Player> alivePlayers, ArrayList<Player> mafiaPlayers) {
        int mafiaChoice = -3;
        boolean mafiaValid = false;
        while(!mafiaValid) {
            try {
                System.out.print("Choose a Target: ");
                mafiaChoice = in.nextInt();
            }//This holds player number
            catch(InputMismatchException e){
                System.out.println("Enter a valid integer for player");
                in.nextLine();
            }
            for (Player alivePlayer : alivePlayers) {
                if(alivePlayer.getPlayerNumber() == mafiaChoice && (alivePlayer instanceof Mafia)){
                    System.out.print("You cannot choose a Mafia. ");
                }
                else if(alivePlayer.getPlayerNumber() == mafiaChoice) {
                    int selectedPlayerHP = alivePlayer.getHP();//Will be subtracted as and when given to the mafia
                    int mafiaPlayersLeft = theGame.getNumberOfMafia();
                    Collections.sort(mafiaPlayers,new MafiaHPSort());//Sorts Mafia in ascending order of HP
                    for(Player mafiaPlayer : mafiaPlayers){
                        if(mafiaPlayer.getHP()>(selectedPlayerHP/mafiaPlayersLeft)){
                            mafiaPlayer.setHP(mafiaPlayer.getHP()-(selectedPlayerHP/mafiaPlayersLeft));
                            selectedPlayerHP-=(selectedPlayerHP/mafiaPlayersLeft);
                            mafiaPlayersLeft-=1;
                        }
                        else{
                            selectedPlayerHP-=mafiaPlayer.getHP();
                            mafiaPlayer.setHP(0);
                            mafiaPlayersLeft-=1;
                        }
                    }
                    alivePlayer.setHP(selectedPlayerHP);
                    mafiaValid = true;
                    break;
                }
            }
        }
        return mafiaChoice;
    }
}

