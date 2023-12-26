import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Card> deck = Card.getStandardDeck();
        Card.printDeck(deck);

        Collections.shuffle(deck);
        Card.printDeck(deck, "Shuffled deck", 4);

        interface SortingComparator<T> extends Comparator<T> {
            int secondParameter(T c1, T c2);
        }

        var sortingCards = new SortingComparator<Card>(){
            @Override
            public int compare(Card c1, Card c2){
                int result = Integer.compare(c1.rank(), c2.rank());
                if(result==0){
                    return secondParameter(c1, c2);
                }
                return result;
            }

            @Override
            public int secondParameter(Card c1, Card c2) {
                return c1.suit().compareTo(c2.suit());
            }
        };

        var sortingAlgo = Comparator.comparing(Card::rank).thenComparing(Card::suit);

        Collections.sort(deck, sortingCards);
        Card.printDeck(deck, "Sorted deck", 13);
    }
}