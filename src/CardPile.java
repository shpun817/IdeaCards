import java.util.ArrayList;

public class CardPile {
    String type;
    ArrayList<Card> cards;

    CardPile(String t) {
        type = t;
        cards = new ArrayList<>();
    }

    void addCard(Card card) {
        if (!card.type.equals(type)) {
            System.out.println("Type mismatch happens for pile typed " + type + " and card " + card.getWord());
            return;
        }
        cards.add(card);
    }

    String getType() {
        return type;
    }

    ArrayList<Card> getCards() {
        return cards;
    }
}
