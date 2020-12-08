package flashcards;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {
    private final Map<String, FlashCard> termMap;
    private final Map<String, FlashCard> definitionMap;

    public Deck() {
        termMap = new HashMap<>();
        definitionMap = new HashMap<>();
    }

    public boolean termExists(String term) {
        return termMap.containsKey(term);
    }

    public boolean definitionExists(String definition) {
        return definitionMap.containsKey(definition);
    }

    public void put(String term, String definition, int wrongCount) {
        FlashCard card = new FlashCard(term, definition, wrongCount);
        definitionMap.remove(definition);
        termMap.remove(term);
        termMap.put(term, card);
        definitionMap.put(definition, card);
    }

    public boolean remove(String term) {
        if (termMap.containsKey(term)) {
            FlashCard card = termMap.remove(term);
            definitionMap.remove(card.getDefinition());
            return true;
        }
        return false;
    }


    public FlashCard getByDefinition(String definition) {
        return definitionMap.get(definition);
    }

    public Collection<FlashCard> getRandomCards(int size) {
        if (size < size()) {
            Set<FlashCard> cards = new HashSet<>();
            List<String> keyList = new ArrayList<>(termMap.keySet());
            while (cards.size() < size) {
                String term = keyList.get((int) (Math.random() * keyList.size()));
                cards.add(termMap.get(term));
            }
            return new ArrayList<>(cards);
        }
        return getAllCards();
    }

    public Collection<FlashCard> getAllCards() {
        return termMap.values();
    }

    public int size() {
        return termMap.size();
    }

    public List<FlashCard> getHardestCards() {
        List<FlashCard> foundCards = new ArrayList<>();
        int maxWrong = Integer.MIN_VALUE;
        for (FlashCard card : termMap.values().stream().filter(card -> card.getWrongCount() > 0).collect(Collectors.toList())) {
            if (card.getWrongCount() > maxWrong) {
                foundCards.clear();
                foundCards.add(card);
                maxWrong = card.getWrongCount();
            } else if (card.getWrongCount() == maxWrong) {
                foundCards.add(card);
            }
        }
        return foundCards;
    }

    public void resetStats() {
        termMap.values().forEach(FlashCard::reset);
    }
}
