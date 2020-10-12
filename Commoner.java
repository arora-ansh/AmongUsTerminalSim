import java.util.ArrayList;

public class Commoner extends Player {

    public Commoner(int _playerNumber, boolean _isUser, Game _theGame){
        super(_playerNumber,_isUser,_theGame);
        HP = 1000;
    }

    @Override
    public int specialMove(ArrayList<Player> alivePlayers, ArrayList<Player> mafiaPlayers) {
        return 0;
    }
}
