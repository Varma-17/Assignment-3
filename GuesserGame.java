import java.util.*;
/*
 Author : Varma Thallapelly
 Enhancements : 
	1) I placed the range for choosing number.
	2) I made the game for as many as players to play.
	3) I eliminated the players those who guessed wrong and made to play for the rest of the won players
	   until one remains.
*/


// Guesser
class Guesser
{
	int guessNum;

	int guessNum()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Guesser kindly guess the number in the range 1 to 10 : ");
		guessNum=sc.nextInt();
		if(guessNum < 1 || guessNum > 10)
		{
			System.out.println("Invalid number, I request Guesser to guess again in the range 1 to 10 only :");
			guessNum = sc.nextInt();
		}
		return guessNum;
	}
}


// Player
class Player
{
    int playerId;
	int guessNum;

    Player(int playerId)
    {
        this.playerId = playerId;
    }

	int guessNum()
	{
        Scanner sc=new Scanner(System.in);
        System.out.println("Player-" + playerId + " kindly guess the number in the range 1 to 10 :");
        guessNum=sc.nextInt();
		if(guessNum < 1 || guessNum > 10)
		{
			System.out.println("Invalid number, I request Player-" + playerId + " to guess again in the range 1 to 10 only :");
			guessNum = sc.nextInt();
		}
        return guessNum;
	}
}


// Umpire
class Umpire
{
	int numFromGuesser;

    int noOfPlayers=0;

	Player[] players;

	Scanner sc = new Scanner(System.in);

	// map to store the result of won players
	static LinkedHashMap<String,Integer> map = new LinkedHashMap<>();

	// tempMap to trace all the players.
	LinkedHashMap<String,Integer> tempMap = new LinkedHashMap<>();

	void setNoOfPlayers(int noOfPlayers)
	{
		this.noOfPlayers = noOfPlayers;
	}

	void playLeague()
	{
		players = new Player[noOfPlayers];

        for (int i = 0; i < noOfPlayers; i++) {
            this.players[i] = new Player(i+1);
			this.map.put("Player-"+(i+1),0);
			this.tempMap.put("Player-"+(i+1),0);
        }
	}

	void play(int noOfPlayers, LinkedHashMap tempMap)
	{
		this.noOfPlayers = noOfPlayers;
		this.tempMap = tempMap;
		players = new Player[noOfPlayers];

        for (int i = 0; i < noOfPlayers; i++) {
            this.players[i] = new Player(i+1);
        }
	}
	// Collecting number from guesser
	void collectNumFromGuesser()
	{
		Guesser g=new Guesser();
		numFromGuesser=g.guessNum();
	}
	
	// Collecting numbers from players and storing it in LinkedHashMap
	void collectNumFromPlayers()
	{
		for (int i = 0; i < noOfPlayers; i++) {
			if(tempMap.get("Player-"+(i+1)) != 99){
				int number = players[i].guessNum();
				map.put("Player-"+(i+1),number);
				tempMap.put("Player-"+(i+1),number);
			}
        }
		System.out.println("Final report of all the players : ");
        System.out.println(map);
	}
	LinkedHashMap compare()
	{
        // elimination
        for (int i = 1; i <= noOfPlayers; i++){
            if(!(tempMap.get("Player-"+(i)) == numFromGuesser))
            {
				map.remove("Player-"+(i));
                tempMap.put("Player-"+(i),99);// choosing random number for eliminated players
            }
        }
		System.out.println();
		System.out.println("All the won players (those who guessed correctly) are : ");
        System.out.println(map);
		System.out.println();
        return tempMap;
	}
	int getSize()
	{
		return map.size();
	}

	String finalResult()
	{
		String finalPlayer = "";
		for(String key:map.keySet())
		{
			finalPlayer = key;
		}
		return finalPlayer;
	}
}


public class GuesserGamerProject {

	public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);
            int noOfPlayers=0;
            System.out.println("Enter no.of Players");
            noOfPlayers = sc.nextInt();

			// Beginning stage
			Umpire u=new Umpire();
			u.setNoOfPlayers(noOfPlayers);
			u.playLeague();
			u.collectNumFromGuesser();
			u.collectNumFromPlayers();

            LinkedHashMap<String,Integer> result = new LinkedHashMap<>();
            result = u.compare();
			
			int size = u.getSize();

            while(size!=1)
            {
				System.out.println("I request all the won players to play again :)");
				System.out.println();
                Umpire u1 = new Umpire();
				u1.play(result.size(),result);
                u1.collectNumFromGuesser();
                u1.collectNumFromPlayers();
                result = u1.compare();
				size = u1.getSize();
            }
			Umpire u2 = new Umpire();
			System.out.println("And the Winner is : " + u2.finalResult());
	}

}
