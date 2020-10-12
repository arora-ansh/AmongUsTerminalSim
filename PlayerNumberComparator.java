import java.util.Comparator;

public class PlayerNumberComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPlayerNumber() - o2.getPlayerNumber();
    }
}
