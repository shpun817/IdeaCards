import java.util.ArrayList;
import java.util.HashMap;

public class Utility {

    static String getTypeByHolder(char holder) {
        switch(holder) {
            case 'A':
                return "Adjective";
            case 'N':
                return "Noun";
            case 'V':
                return "Verb";
        }
        return null;
    }

    static HashMap<String, CardPile> loadPiles() {
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card("Adjective", "Happy"));
        cards.add(new Card("Noun", "Dog"));
        cards.add(new Card("Verb", "Kills"));

        HashMap<String, ArrayList<Card>> typeToCards = new HashMap<>();
        for (Card card : cards) {
            String type = card.getType();
            if (!typeToCards.containsKey(type))
                typeToCards.put(type, new ArrayList<>());
            typeToCards.get(type).add(card);
        }

        HashMap<String, CardPile> piles = new HashMap<>();
        for (String type : typeToCards.keySet()) {
            CardPile pile = new CardPile(type);
            for (Card card : typeToCards.get(type)) {
                pile.addCard(card);
            }
            piles.put(type, pile);
        }

        return piles;
    }
}
