import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utility {

    static String wordsPath = null;

    static boolean needUpdatePath(String path) {
        if (wordsPath != null && wordsPath.equals(path)) return false;
        wordsPath = path;
        return true;
    }

    static String getTypeByHolder(char holder) {
        switch(holder) {
            case 'A':
            case 'a':
                return "Adjective";
            case 'N':
            case 'n':
                return "Noun";
            case 'V':
            case 'v':
                return "Verb";
        }
        return null;
    }

    static ArrayList<Card> loadCardsFromFile() {
        if (wordsPath == null) return null;
        ArrayList<Card> cards = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(wordsPath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            Map<String, List<String>> map = (Map)obj;
            for (String type : map.keySet()) {
                List<String> words = map.get(type);
                for (String word : words)
                    cards.add(new Card(type, word));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cards;
    }

    static HashMap<String, CardPile> loadPiles() {
        ArrayList<Card> cards = new Utility().loadCardsFromFile();
        if (cards == null) return null;

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
