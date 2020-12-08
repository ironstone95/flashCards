package flashcards;

import java.util.Scanner;

public class Practice {
    public static void start(Scanner scanner, Deck deck, int size) {
        deck.getRandomCards(size).forEach(flashCard -> {
            System.out.println("Print the definition of \"" + flashCard.getTerm() + "\"");
            String answer = scanner.nextLine();
            if (flashCard.checkAnswer(answer)) {
                System.out.println("Correct!");
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("Wrong. The right answer is \"").append(flashCard.getDefinition()).append("\"");
                FlashCard foundCard = deck.getByDefinition(answer);
                if (foundCard != null) {
                    builder.append(", but your definition is correct for \"").append(foundCard.getTerm()).append("\"");
                }
                builder.append(".");
                System.out.println(builder.toString());
            }
        });
    }
}
