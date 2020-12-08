package flashcards;

public class FlashCard {
    private final String term;
    private final String definition;
    private int wrongCount;

    public FlashCard(String term, String answer, int wrongCount) {
        this.term = term;
        this.definition = answer;
        this.wrongCount = wrongCount;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean checkAnswer(String answer) {
        boolean result = answer.equals(this.definition);
        if (!result) {
            wrongCount++;
        }
        return answer.equals(this.definition);
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void reset() {
        this.wrongCount = 0;
    }
}
