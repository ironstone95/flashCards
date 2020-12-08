package flashcards;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Logger {
    private StringBuilder stringBuilder = new StringBuilder();
    private final PrintStream printStream = new PrintStream(System.out);

    public void append(String... strings) {
        for (String string : strings) {
            stringBuilder.append(string);
        }
        stringBuilder.append("\n");
    }

    public void append(int print) {
        append(String.valueOf(print));
    }

    public String getLog() {
        return stringBuilder.toString();
    }

    public void print(String print) {
        printStream.println(print);
        append(print);
    }


    public String clear() {
        String returnVal = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        return returnVal;
    }

    public void saveLog(String filePath) {
        try (PrintWriter pw = new PrintWriter("./" + filePath)) {
            print("The log has been saved.");
            pw.print(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
