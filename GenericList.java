import java.util.ArrayList;

public class GenericList <Type> {

    private ArrayList<Type> playerArray;

    public GenericList(){
        playerArray = new ArrayList<Type>();
    }

    public void add(Type o1){
        playerArray.add(o1);
    }

    public Type get(int index){
        return playerArray.get(index);
    }

    public int size(){
        return playerArray.size();
    }

}
