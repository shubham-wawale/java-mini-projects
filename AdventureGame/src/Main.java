import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdventureGame adventureGame = new AdventureGame();
        adventureGame.play("road");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            String direction = scanner.nextLine().toUpperCase().trim().substring(0, 1);
            if(direction.equals("Q")) break;
            adventureGame.move(direction);
        }

    }
}