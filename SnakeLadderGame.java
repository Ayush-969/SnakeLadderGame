import java.util.*;

class SnakeLadderGame {
    private static final int BOARD_SIZE = 100;
    private static final int[] SNAKES = {16, 47, 49, 56, 62, 64, 87, 93, 95, 98};
    private static final int[] LADDERS = {1, 4, 9, 21, 28, 36, 51, 71, 80};
    
    private Map<String, Integer> players;
    private Random dice;
    
    public SnakeLadderGame(String[] playerNames) {
        players = new HashMap<>();
        for (String name : playerNames) {
            players.put(name, 0);
        }
        dice = new Random();
    }
    
    private int rollDice() {
        return dice.nextInt(6) + 1;
    }
    
    private boolean isSnake(int position) {
        return Arrays.stream(SNAKES).anyMatch(snake -> snake == position);
    }
    
    private boolean isLadder(int position) {
        return Arrays.stream(LADDERS).anyMatch(ladder -> ladder == position);
    }
    
    private void movePlayer(String playerName, int steps) {
        int currentPosition = players.get(playerName);
        int newPosition = currentPosition + steps;
        
        if (newPosition > BOARD_SIZE) {
            newPosition = currentPosition;
        } else if (isSnake(newPosition)) {
            newPosition -= steps;
        } else if (isLadder(newPosition)) {
            newPosition += steps;
        }
        
        players.put(playerName, newPosition);
    }
    
    private boolean hasPlayerWon(String playerName) {
        return players.get(playerName) == BOARD_SIZE;
    }
    
    public void play() {
        List<String> playerNames = new ArrayList<>(players.keySet());
        int currentPlayerIndex = 0;
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String currentPlayer = playerNames.get(currentPlayerIndex);
            System.out.println(currentPlayer + ", it's your turn. Press enter to roll the dice.");
            scanner.nextLine();
            
            int steps = rollDice();
            System.out.println(currentPlayer + " rolled a " + steps);
            
            movePlayer(currentPlayer, steps);
            
            int currentPosition = players.get(currentPlayer);
            System.out.println(currentPlayer + " moved to position " + currentPosition);
            
            if (hasPlayerWon(currentPlayer)) {
                System.out.println(currentPlayer + " has won the game!");
                break;
            }
            
            currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();
        }
        
        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        
        String[] playerNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            playerNames[i] = scanner.nextLine();
        }
        
        SnakeLadderGame game = new SnakeLadderGame(playerNames);
        game.play();
        
        scanner.close();
    }
}
