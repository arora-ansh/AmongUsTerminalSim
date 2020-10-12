import java.util.Comparator;

public class PlayerVoteComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return o2.getPlayerVotes()-o1.getPlayerVotes();
    }
}
