import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IdeationSystem {
    HashMap<String, CardPile> cardPiles;
    String pattern;

    private String result;

    IdeationSystem() {
        cardPiles = Utility.loadPiles();
        pattern = "ANV";
    }

    boolean cardPilesReady() {
        if (cardPiles != null)
            return true;
        else
            return false;
    }

    HashMap<String, CardPile> getCardPiles() {
        return cardPiles;
    }

    void reloadCardPiles() {
        cardPiles = Utility.loadPiles();
    }

    boolean setPattern(String p) {
        if (patternIsLegal(p)) {
             pattern = p;
             return true;
        } else
            return false;
    }

    boolean patternIsLegal(String p) {
        int length = p.length();
        if (length <= 0) return false;
        for (int i = 0; i < length; ++i) {
            char c = p.charAt(i);
            switch(c) {
                case 'A':
                case 'N':
                case 'V':
                case 'a':
                case 'n':
                case 'v':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    void addPile(CardPile pile) {
        if (cardPiles.containsKey(pile.getType())) {
            System.out.println("Pile with the same type already exists!");
            return;
        }
        cardPiles.put(pile.getType(), pile);
    }

    String generateResult() {
        result = "";
        int patternLength = pattern.length();
        Random random = new Random();
        for (int i = 0; i < patternLength; ++i) {
            String currType = Utility.getTypeByHolder(pattern.charAt(i));
            if (currType == null) continue;
            ArrayList<Card> pile = cardPiles.get(currType).getCards();
            if (pile != null) {
                Card card = pile.get(random.nextInt(pile.size()));
                result += card.getWord();
            } else {
                continue;
            }
            result += " ";
        }
        return (result = result.strip());
    }

    void showResult() {
        System.out.println(result);
    }

    void addWord(String type, String word) {
        if (!cardPiles.containsKey(type))
            cardPiles.put(type, new CardPile(type));
        cardPiles.get(type).addCard(new Card(type, word));
        return;
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.callLaunch(args);
    }

}
