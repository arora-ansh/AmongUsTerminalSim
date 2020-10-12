import java.util.Comparator;

public class MafiaHPSort implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return o1.getHP() - o2.getHP();
    }
}
