package chapter3;

import java.util.*;
import java.util.stream.Collectors;

public class WordSearchConstraint extends Constraint<String, List<WordGrid.GridLocation>> {
    public WordSearchConstraint(List<String> words) {
        super(words);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {
        //combine all GridLocations into one giant List
        List<WordGrid.GridLocation> allLocations = assignment.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        //a Set will eliminate duplicates using equals()
        Set<WordGrid.GridLocation> allLocationsSet = new HashSet<>(allLocations);
        //if there are any duplicate grid locations then there is an overlap
        return allLocations.size() == allLocationsSet.size();
        // TODO make it ok for duplicate locations iff the values at the locations are equal
    }

    public static void main(String[] args) {
        WordGrid wordGrid = new WordGrid(9, 9);
        List<String> words = List.of("MATTHEW", "JOE", "MARY", "SARAH", "SALLY");
        //generate domains for all words
        Map<String, List<List<WordGrid.GridLocation>>> domains = new HashMap<>();
        for (String word : words) {
            domains.put(word, wordGrid.generateDomain(word));
        }
        CSP<String, List<WordGrid.GridLocation>> csp = new CSP<>(words, domains);
        csp.addConstraint(new WordSearchConstraint(words));
        Map<String, List<WordGrid.GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found");
        } else {
            Random random = new Random();
            for (Map.Entry<String, List<WordGrid.GridLocation>> item : solution.entrySet()) {
                String word = item.getKey();
                List<WordGrid.GridLocation> locations = item.getValue();
                //random reverse have the time
                if (random.nextBoolean()) {
                    Collections.reverse(locations);
                }
                wordGrid.mark(word, locations);
            }
            System.out.println(wordGrid);
        }
    }
}
