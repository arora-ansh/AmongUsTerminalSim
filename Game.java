import java.util.*;

public class Game {

    Scanner in = new Scanner(System.in);
    Random rand = new Random();

    private int numberOfPlayers = -1;
    private int numberOfCommoners;
    private int numberOfDetectives;
    private int numberOfHealers;
    private int numberOfMafia;
    private int numberOfActivePlayers;

    private Player userPlayer;//Player character
    private final int userPlayerNumber;//Player number
    private boolean userAlive;

    private boolean mafiaIdentified;

    private ArrayList<Player> gamePlayers;
    private ArrayList<Player> alivePlayers;
    private ArrayList<Player> mafiaPlayers;

    GenericList<Mafia> mafiaGenericList;
    GenericList<Detective> detectiveGenericList;
    GenericList<Healer> healerGenericList;
    GenericList<Commoner> commonerGenericList;


    public Game() throws InputMismatchException{
        System.out.println("Welcome to Among Us");
        while(numberOfPlayers<6) {
            try {
                System.out.print("Enter Number of Players: ");
                numberOfPlayers = in.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Enter Number Greater than or equal to 6");
                in.nextLine();
            }
        }

        gamePlayers = new ArrayList<>(numberOfPlayers);

        ArrayList<Integer> charNumberGiver= new ArrayList<>(numberOfPlayers);
        for(int i=1;i<=numberOfPlayers;i++){
            charNumberGiver.add(i);
        }

        numberOfDetectives = numberOfPlayers/5;
        numberOfMafia = numberOfPlayers/5;
        numberOfHealers = numberOfPlayers/10;
        numberOfCommoners = numberOfPlayers - numberOfMafia - numberOfDetectives - numberOfHealers;
        numberOfActivePlayers = numberOfPlayers;

        int randomNumberGen = rand.nextInt(numberOfPlayers);
        userPlayerNumber = randomNumberGen+1;
        charNumberGiver.remove(randomNumberGen);
        userAlive = true;

        int userCharChoice=-1;
        while(userCharChoice>5 || userCharChoice<1) {
            try {
                System.out.println("Choose a Character\n1) Impostor\n2) Detective\n3) Healer\n4) Commoner\n5) Assign Randomly");
                userCharChoice = in.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Enter integer between 1 and 5, both inclusive");
                in.nextLine();
            }
            if (userCharChoice == 1) {
                userPlayer = new Mafia(userPlayerNumber,true,this);
            } else if (userCharChoice == 2) {
                userPlayer = new Detective(userPlayerNumber,true,this);
            } else if (userCharChoice == 3) {
                userPlayer = new Healer(userPlayerNumber, true,this);
            } else if (userCharChoice == 4) {
                userPlayer = new Commoner(userPlayerNumber, true,this);
            } else if (userCharChoice == 5) {
                int randomCharChoice = rand.nextInt(4);
                if (randomCharChoice == 0) {
                    userPlayer = new Mafia(userPlayerNumber, true,this);
                } else if (randomCharChoice == 1) {
                    userPlayer = new Detective(userPlayerNumber, true,this);
                } else if (randomCharChoice == 2) {
                    userPlayer = new Healer(userPlayerNumber, true,this);
                } else {
                    userPlayer = new Commoner(userPlayerNumber, true,this);
                }
            }
        }
        gamePlayers.add(userPlayer);
        System.out.println("You are "+userPlayer.toString());
        //While loop that runs until charNumberGiver doesn't get empty
        //This loop allots players with different player numbers and as different characters of the game
        int a = numberOfMafia;
        int b = numberOfHealers;
        int c = numberOfDetectives;
        int d = numberOfCommoners;
        if(userPlayer instanceof Mafia) a-=1;
        else if(userPlayer instanceof Healer) b-=1;
        else if(userPlayer instanceof Detective) c-=1;
        else if(userPlayer instanceof Commoner) d-=1;
        while(charNumberGiver.size()>0){
            int randomChoice = rand.nextInt(charNumberGiver.size());
            int playerChoice = rand.nextInt(4);
            if(playerChoice==0 && a>0){
                a -= 1;
                gamePlayers.add(new Mafia(charNumberGiver.get(randomChoice), false,this));
                charNumberGiver.remove(randomChoice);
            } else if(playerChoice==1 && b>0){
                b -= 1;
                gamePlayers.add(new Healer(charNumberGiver.get(randomChoice), false,this));
                charNumberGiver.remove(randomChoice);
            } else if(playerChoice==2 && c>0) {
                c -= 1;
                gamePlayers.add(new Detective(charNumberGiver.get(randomChoice), false,this));
                charNumberGiver.remove(randomChoice);
            } else if(playerChoice==3 && d>0) {
                d -= 1;
                gamePlayers.add(new Commoner(charNumberGiver.get(randomChoice), false,this));
                charNumberGiver.remove(randomChoice);
            }
        }
        //Use a sort to sort players according to
        gamePlayers.sort(new PlayerNumberComparator());
        alivePlayers = (ArrayList<Player>) gamePlayers.clone();


        mafiaGenericList = new GenericList<>();
        detectiveGenericList = new GenericList<>();
        healerGenericList = new GenericList<>();
        commonerGenericList = new GenericList<>();

        for(Player gamePlayer : gamePlayers) {
            if(gamePlayer instanceof Mafia) mafiaGenericList.add((Mafia) gamePlayer);
            else if(gamePlayer instanceof Detective) detectiveGenericList.add((Detective) gamePlayer);
            else if(gamePlayer instanceof Healer) healerGenericList.add((Healer) gamePlayer);
            else if(gamePlayer instanceof Commoner) commonerGenericList.add((Commoner) gamePlayer);
        }

        mafiaPlayers = new ArrayList<>();
        for(int i=0;i<mafiaGenericList.size();i++){
                mafiaPlayers.add(mafiaGenericList.get(i));
        }

        //DISPLAY OTHER CHARACTERS OF USER'S CLASS
        if(userPlayer instanceof Mafia){
            System.out.print("You are an impostor. Other impostors are [ ");
            for(int i=0;i<mafiaGenericList.size();i++){
                if(!mafiaGenericList.get(i).equals(userPlayer)){
                    System.out.print(mafiaGenericList.get(i).toString()+" ");
                }
            }
            System.out.println("]");
        }
        else if(userPlayer instanceof Detective){
            System.out.print("You are a detective. Other detectives are [ ");
            for(int i=0;i<detectiveGenericList.size();i++){
                if(!detectiveGenericList.get(i).equals(userPlayer)){
                    System.out.print(detectiveGenericList.get(i).toString()+" ");
                }
            }
            System.out.println("]");
        }
        else if(userPlayer instanceof Healer){
            System.out.print("You are a healer. Other healers are [ ");
            for(int i=0;i<healerGenericList.size();i++){
                if(!healerGenericList.get(i).equals(userPlayer)){
                    System.out.print(healerGenericList.get(i).toString()+" ");
                }
            }
            System.out.println("]");
        }
        else if(userPlayer instanceof Commoner){
            System.out.print("You are a commoner. Other commoners are [ ");
            for(int i=0;i<commonerGenericList.size();i++){
                if(!commonerGenericList.get(i).equals(userPlayer)){
                    System.out.print(commonerGenericList.get(i).toString()+" ");
                }
            }
            System.out.println("]");
        }
        //displayAlivePlayers();
        //displayGamePlayers();
        gameRound();
    }

    //CREATE ALIVE PLAYERS ARRAY

    private void gameRound() throws InputMismatchException{
        int roundNumber = 1;
        while(numberOfMafia>0 && (numberOfActivePlayers-numberOfMafia)>numberOfMafia){
            System.out.println("Round "+roundNumber+":");
            displayAlivePlayers();
//Comments here
/*
            for(Player alivePlayer:alivePlayers){
                System.out.println(alivePlayer.toString()+" HP"+alivePlayer.getHP());
            }
*/
            int mafiaChoice = -3;
            int detectiveChoice = -2;
            int healerChoice = -1;
            mafiaIdentified = false;
            //ADD SPECIFIC CHECKS WHETHER A PLAYER IS ALIVE OR NOT
            //Main round gameplay, in all cases if user is specific character, ask them
            //Mafia chooses player to die
            boolean mafiaValid = false;
            if(userPlayer instanceof Mafia && userAlive){
                mafiaChoice = userPlayer.specialMove(alivePlayers,mafiaPlayers);
            }
            else{
                while(!mafiaValid) {
                    mafiaChoice = rand.nextInt(numberOfPlayers);//This holds player number
                    for (Player alivePlayer : alivePlayers) {
                        if(alivePlayer.getPlayerNumber() == mafiaChoice && !(alivePlayer instanceof Mafia)) {
                            int selectedPlayerHP = alivePlayer.getHP();//Will be subtracted as and when given to the mafia
                            int mafiaPlayersLeft = numberOfMafia;
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
                System.out.println("Impostors have chosen their target.");
            }
            //Detective chooses player to test(iff detectives alive)
            boolean detectiveValid = false;
            if(userPlayer instanceof Detective && userAlive){
                detectiveChoice = userPlayer.specialMove(alivePlayers,mafiaPlayers);
            }
            else if(numberOfDetectives>0){
                while(!detectiveValid) {
                    detectiveChoice = rand.nextInt(numberOfPlayers);//This holds player number
                    for (Player alivePlayer : alivePlayers) {
                        if(alivePlayer.getPlayerNumber() == detectiveChoice && !(alivePlayer instanceof Detective)) {
                            detectiveValid = true;
                            if(alivePlayer instanceof Mafia){
                                //System.out.println(alivePlayer.toString()+" is Mafia.");
                                mafiaIdentified = true;
                            }
                            break;
                        }
                    }
                }
                System.out.println("Detectives have chosen a player to test.");
            }
            else{
                System.out.println("Detectives have chosen a player to test.");
            }
            //Healer chooses player to heal according to HP Rules(iff healers alive)
            boolean healerValid = false;
            if(userPlayer instanceof Healer && userAlive){
                healerChoice = userPlayer.specialMove(alivePlayers,mafiaPlayers);
            }
            else if(numberOfHealers>0){
                while(!healerValid) {
                    healerChoice = rand.nextInt(numberOfPlayers);//This holds player number
                    for (Player alivePlayer : alivePlayers) {
                        if(alivePlayer.getPlayerNumber() == healerChoice && !(alivePlayer instanceof Healer)) {
                            alivePlayer.setHP(alivePlayer.getHP()+500);
                            healerValid = true;
                            break;
                        }
                    }
                }
                System.out.println("Healers have chosen someone to heal.");
            }
            else{
                System.out.println("Healers have chosen someone to heal.");
            }
            System.out.println("--End of actions--");
//Comments here
/*
            System.out.println("Mafia Chose: "+mafiaChoice);
            System.out.println("Detective Chose: "+detectiveChoice);
            System.out.println("Healer Chose: "+healerChoice);
*/
            //Perform check on player that mafia chose, is their HP down to 0. If yes set them dead(after checking healed, obviously)
            //MAKE THE HP SYSTEM
            if(mafiaChoice>0  && mafiaChoice!=healerChoice){
//                System.out.println("A PLAYER SHOULD DIE.");
                Player playerRemoved = null;
                for(Player alivePlayer : alivePlayers){
                    //System.out.println(alivePlayer.toString()+" HP"+alivePlayer.getHP());
                    if(mafiaChoice==alivePlayer.getPlayerNumber() && alivePlayer.getHP()==0){
                        System.out.println(alivePlayer.toString()+" has died.");
                        if(alivePlayer.equals(userPlayer)){
                            userAlive = false;
                        }
                        if(alivePlayer instanceof Healer){
                            numberOfHealers-=1;
                        }
                        else if(alivePlayer instanceof Detective){
                            numberOfDetectives-=1;
                        }
                        //alivePlayers.remove(alivePlayer);
                        playerRemoved = alivePlayer;
                        numberOfActivePlayers-=1;
                        break;
                    }
                }
                if(playerRemoved != null) {
                    alivePlayers.remove(playerRemoved);
                }
            }

            //Check detectives vote. If it was on mafia, mafia automatically voted out and no voting round needed(iff detectives alive)
            if(mafiaIdentified){
                Player playerRemoved = null;
                for(Player alivePlayer: alivePlayers){
                    if(detectiveChoice==alivePlayer.getPlayerNumber()){
                        System.out.println(alivePlayer.toString()+" has been voted out. Detective identified Impostor successfully.");
                        if(alivePlayer.equals(userPlayer)){
                            userAlive = false;
                        }
                        Player toBeRemoved = null;
                        for(Player mafiaPlayer : mafiaPlayers){
                            if(detectiveChoice == mafiaPlayer.getPlayerNumber()){
                                toBeRemoved = mafiaPlayer;
                            }
                        }
                        if(toBeRemoved!=null) {
                            mafiaPlayers.remove(toBeRemoved);
                        }
                        //alivePlayers.remove(alivePlayer);
                        playerRemoved = alivePlayer;
                        numberOfMafia-=1;
                        numberOfActivePlayers-=1;
                    }
                }
                if(playerRemoved != null) {
                    alivePlayers.remove(playerRemoved);
                }
            }

            //Voting Round. All players vote randomly and user is asked for vote too. Maintain ArrayList
            votingRound();
            // Voting round ends

            alivePlayers.sort(new PlayerNumberComparator());
            //If votes are tied, repeat voting round
            System.out.println("--End of round "+ roundNumber +"--\n");
            if(numberOfMafia<1){
                playersWon();
            }
            else if(numberOfActivePlayers-numberOfMafia<=numberOfMafia){
                mafiaWon();
            }
            roundNumber+=1;
        }
    }

    private void votingRound() throws InputMismatchException{
        if(!mafiaIdentified){
            boolean playerEjected = false;
            while(!playerEjected) {
                for (Player alivePlayer : alivePlayers) {
                    if (alivePlayer.equals(userPlayer)) {
                        int userVoteChoice = -1;
                        boolean voted = false;
                        while(userVoteChoice<0 || userPlayer.getPlayerNumber()==userVoteChoice) {
                            try {
                                System.out.print("Select a person to vote out: ");
                                userVoteChoice = in.nextInt();
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Enter a valid integer for player.");
                                in.nextLine();
                            }
                            for (Player p1 : alivePlayers) {
                                if (p1.getPlayerNumber() == userVoteChoice) {//Basically if the selected player is there in alive, voted will become true
                                    voted = true;
                                    break;
                                }
                            }
                            if(!voted){//and if not successfully voted, the player will have to vote again
                                System.out.println("Dead Player selected, vote again.");
                                userVoteChoice=-1;
                            }
                        }
                        for (Player p1 : alivePlayers) {
                            if (p1.getPlayerNumber() == userVoteChoice) {
                                p1.setPlayerVotes(p1.getPlayerVotes() + 1);
                            }
                        }
                    } else {
                        int i = rand.nextInt(alivePlayers.size());
                        alivePlayers.get(i).setPlayerVotes(alivePlayers.get(i).getPlayerVotes() + 1);
                    }
                }
                alivePlayers.sort(new PlayerVoteComparator());
/*
                    for(Player alivePlayer : alivePlayers){
                        System.out.println(alivePlayer.toString()+" "+alivePlayer.getPlayerVotes()+" votes");
                    }
*/
                if (alivePlayers.get(0).getPlayerVotes() > alivePlayers.get(1).getPlayerVotes()) {
                    System.out.println(alivePlayers.get(0).toString() + " has been voted out.");
                    int x = alivePlayers.get(0).getPlayerNumber();
                    if (alivePlayers.get(0) instanceof Mafia) {
                        numberOfMafia -= 1;
                        mafiaPlayers.removeIf(mafiaPlayer -> x == mafiaPlayer.getPlayerNumber());
                    }
                    else if(alivePlayers.get(0) instanceof Healer){
                        numberOfHealers-=1;
                    }
                    else if(alivePlayers.get(0) instanceof Detective){
                        numberOfDetectives-=1;
                    }
                    numberOfActivePlayers -= 1;
                    if(alivePlayers.get(0).equals(userPlayer)){
                        userAlive = false;
                    }
                    alivePlayers.remove(0);
                    playerEjected = true;
                }
                for(Player p1 : alivePlayers){
                    p1.setPlayerVotes(0);
                }
            }
        }
    }


    private void playersWon(){
        System.out.println("Game Over.\nThe Impostors have Lost.");
        displayGamePlayers();
    }

    private void mafiaWon(){
        System.out.println("Game Over.\nThe Impostors have Won.");
        displayGamePlayers();
    }
    //This function to be displayed at the start of every round
    private void displayAlivePlayers(){
        int aliveCount = 0;
        for (Player alivePlayer : alivePlayers) {
            if (alivePlayer.getIsAlive()) {
                aliveCount += 1;
            }
        }
        System.out.print(aliveCount+" Players are Remaining: ");
        for (Player alivePlayer : alivePlayers) {
            if (alivePlayer.getIsAlive()) {
                System.out.print(alivePlayer.toString() + " ");
            }
        }
        System.out.println("are alive.");
    }

    //This function to be displayed at the end of game to show who was who
    private void displayGamePlayers() {

        for(int i=0;i<mafiaGenericList.size();i++) {
            System.out.print(mafiaGenericList.get(i).toString()+" ");
            if(mafiaGenericList.get(i).equals(userPlayer)) {
                System.out.print("[User] ");
            }
        }
        System.out.println("were Impostors");

        for(int i=0;i<detectiveGenericList.size();i++) {
            System.out.print(detectiveGenericList.get(i).toString()+" ");
            if(detectiveGenericList.get(i).equals(userPlayer)){
                System.out.print("[User] ");
            }
        }
        System.out.println("were Detectives");

        for(int i=0;i<healerGenericList.size();i++) {
            System.out.print(healerGenericList.get(i).toString()+" ");
            if(healerGenericList.get(i).equals(userPlayer)){
                System.out.print("[User] ");
            }
        }
        System.out.println("were Healers");

        for(int i=0;i<commonerGenericList.size();i++) {
            System.out.print(commonerGenericList.get(i).toString()+" ");
            if(commonerGenericList.get(i).equals(userPlayer)){
                System.out.print("[User] ");
            }
        }
        System.out.println("were Commoners");

    }

    public int getNumberOfMafia() {
        return numberOfMafia;
    }

    public void setMafiaIdentified(boolean mafiaIdentified) {
        this.mafiaIdentified = mafiaIdentified;
    }
}
