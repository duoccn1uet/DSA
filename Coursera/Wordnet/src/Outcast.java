import java.util.Objects;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDistance = 0;
        for (String nounA : nouns) {
            int distance = 0;
            for (String nounB : nouns)
                if (!Objects.equals(nounA, nounB))
                    distance += wordnet.distance(nounA, nounB);
            if (maxDistance < distance) {
                maxDistance = distance;
                outcast = nounA;
            }
        }
        return outcast;
    }
}
