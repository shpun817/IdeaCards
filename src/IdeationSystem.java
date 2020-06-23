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
        result = null;
    }

    void setPattern(String p) {
        pattern = p;
    }

    void addPile(CardPile pile) {
        if (cardPiles.containsKey(pile.getType())) {
            System.out.println("Pile with the same type already exists!");
            return;
        }
        cardPiles.put(pile.getType(), pile);
    }

    void generateResult() {
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
        result = result.strip();
        return;
    }

    void showResult() {
        System.out.println(result);
    }

    public static void main() {
        IdeationSystem is = new IdeationSystem();
        is.generateResult();
        is.showResult();
    }

}
