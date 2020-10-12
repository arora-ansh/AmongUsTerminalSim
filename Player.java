import java.util.ArrayList;
import java.util.InputMismatchException;

public abstract class Player {

    protected int HP;
    protected int playerNumber;
    protected boolean isAlive; //true = alive, false = dead
    protected int playerVotes;
    protected boolean isUser;
    Game theGame;

    public Player(int _playerNumber,boolean _isUser, Game _theGame) {
        playerNumber = _playerNumber;
        playerVotes = 0;
        isAlive = true;
        isUser = _isUser;
        theGame = _theGame;
    }

    public abstract int specialMove(ArrayList<Player> alivePlayers, ArrayList<Player> mafiaPlayers) throws InputMismatchException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return HP == player.HP &&
                playerNumber == player.playerNumber &&
                isUser == player.isUser &&
                theGame.equals(player.theGame);
    }

    @Override
    public String toString() {
        return "Player"+playerNumber;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        isAlive = alive;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getPlayerVotes() {
        return playerVotes;
    }

    public void setPlayerVotes(int playerVotes) {
        this.playerVotes = playerVotes;
    }

}
