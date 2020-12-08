package flashcards;

import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final Deck deck = new Deck();
    private final Logger logger = new Logger();
    private String outputFile;

    private ConsoleInterface() {

    }

    private void start() {
        Program:
        while (true) {
            showMenu();
            String action = scanner.nextLine();
            logger.append(action);
            switch (action) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importDeck();
                    break;
                case "export":
                    exportDeck();
                    break;
                case "ask":
                    ask();
                    break;
                case "exit":
                    exit();
                    break Program;
                case "log":
                    log();
                    break;
                case "hardest card":
                    hardestCards();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                default:
                    logger.print("Wrong input.");
            }
        }
    }

    private void showMenu() {
        logger.print("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\n");
    }

    private void addCard() {
        logger.print("The card:");
        String term = scanner.nextLine().trim();
        logger.append(term);
        if (deck.termExists(term)) {
            logger.print("The card \"" + term + "\" already exists.");
            return;
        }

        logger.print("The definition of the card:");
        String definition = scanner.nextLine().trim();
        logger.append(definition);
        if (deck.definitionExists(definition)) {
            logger.print("The definition \"" + definition + "\" already exists.");
            return;
        }

        deck.put(term, definition, 0);
        logger.print(String.format("The pair (\"%s\":\"%s\") has been added.", term, definition));
    }

    private void removeCard() {
        logger.print("Which card?");
        String term = scanner.nextLine();
        logger.append(term);
        if (deck.remove(term)) {
            logger.print("The card has been removed.");
        } else {
            logger.print("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    private void importDeck() {
        logger.print("File name:");
        String fileName = scanner.nextLine();
        logger.append(fileName);
        CardIO cardIO = new CardIO(deck, fileName);
        if (cardIO.fileNotFound()) {
            logger.print("File not found.");
            return;
        }
        int loadedCard = cardIO.loadFromFile();
        logger.print(String.format("%d cards have been loaded.", loadedCard));
    }

    private void importDeck(String fileName) {
        CardIO cardIO = new CardIO(deck, fileName);
        if (cardIO.fileNotFound()) {
            logger.print("File not found.");
            return;
        }
        int loadedCard = cardIO.loadFromFile();
        logger.print(String.format("%d cards have been loaded.", loadedCard));
    }

    private void exportDeck() {
        logger.print("File name:");
        String fileName = scanner.nextLine();
        logger.append(fileName);
        CardIO cardIO = new CardIO(deck, fileName);
        int savedCard = cardIO.saveToFile();
        logger.print(String.format("%d cards have been saved", savedCard));
    }

    private void exportDeck(String fileName) {
        CardIO cardIO = new CardIO(deck, fileName);
        int savedCard = cardIO.saveToFile();
        logger.print(String.format("%d cards have been saved", savedCard));
    }

    private void ask() {
        logger.print("How many times to ask?");
        try {
            int size = Integer.parseInt(scanner.nextLine());
            logger.append(size);
            Practice.start(scanner, deck, size);
        } catch (NumberFormatException e) {
            logger.print("You must enter a number.");
        }
    }

    private void exit() {
        if (outputFile != null) {
            exportDeck(outputFile);
        }
        logger.print("Bye bye!");
    }

    private void log() {
        logger.print("File name: ");
        String fileName = scanner.nextLine();
        logger.append(fileName);
        logger.saveLog(fileName);
    }

    private void hardestCards() {
        List<FlashCard> cards = deck.getHardestCards();
        if (cards.isEmpty()) {
            logger.print("There are no cards with errors.");
        } else if (cards.size() == 1) {
            String out = String.format("The hardest card is \"%s\". You have %d errors answering it.", cards.get(0).getTerm(), cards.get(0).getWrongCount());
            logger.print(out);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("The hardest cards are ");
            for (int i = 0; i < cards.size(); i++) {
                builder.append("\"").append(cards.get(i).getTerm()).append("\"");
                if (i < cards.size() - 1) {
                    builder.append(", ");
                } else {
                    builder.append(". ");
                }
            }
            builder.append("You have ").append(cards.get(0).getWrongCount()).append(" errors answering them.");
            logger.print(builder.toString());
        }
    }

    private void resetStats() {
        deck.resetStats();
        logger.print("Card statistics have been reset.");
    }


    public static void run(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        String inputFile = null;
        String outputFile = null;
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-import")) {
                inputFile = args[i + 1];
            } else if (args[i].equals("-export")) {
                outputFile = args[i + 1];
            }
        }
        if (inputFile != null) {
            consoleInterface.importDeck(inputFile);
        }
        if (outputFile != null) {
            consoleInterface.outputFile = outputFile;
        }
        consoleInterface.start();
    }

}
