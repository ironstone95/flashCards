package flashcards;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CardIO {
    private final File file;
    private final Deck deck;

    public CardIO(Deck deck, String filePath) {
        this.deck = deck;
        this.file = new File("./" + filePath);
    }

    public boolean fileNotFound() {
        return !(file.exists() && file.isFile());
    }

    public int loadFromFile() {
        int loaded = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length == 3) {
                    deck.put(line[0], line[1], Integer.parseInt(line[2]));
                    loaded++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loaded;
    }

    public int saveToFile() {
        AtomicInteger saved = new AtomicInteger();
        try (PrintWriter pw = new PrintWriter(file)) {
            deck.getAllCards().forEach(card -> {
                pw.printf("%s,%s,%d\n", card.getTerm(), card.getDefinition(), card.getWrongCount());
                saved.addAndGet(1);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved.get();
    }
}
