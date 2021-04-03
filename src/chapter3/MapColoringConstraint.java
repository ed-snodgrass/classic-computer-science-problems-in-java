package chapter3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapColoringConstraint extends Constraint<String, String> {
    public static final String WESTERN_AUSTRALIA = "Western Australia";
    public static final String NORTHERN_TERRITORY = "Northern Territory";
    public static final String SOUTH_AUSTRALIA = "South Australia";
    public static final String QUEENSLAND = "Queensland";
    public static final String NEW_SOUTH_WALES = "New South Wales";
    public static final String VICTORIA = "Victoria";
    public static final String TASMANIA = "Tasmania";
    private final String place1;
    private final String place2;

    public MapColoringConstraint(String place1, String place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        //if either place is not in the assignment then it is not yet possible for their colors to be conflicting
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }
        //check that the color of place1 is not the same as the color of place2
        return !assignment.get(place1).equals(assignment.get(place2));
    }

    public static void main(String[] args) {
        List<String> variables = List.of(WESTERN_AUSTRALIA, NORTHERN_TERRITORY, SOUTH_AUSTRALIA, QUEENSLAND, NEW_SOUTH_WALES, VICTORIA, TASMANIA);
        Map<String, List<String>> domains = new HashMap<>();
        for (String variable : variables) {
            domains.put(variable, List.of("red", "green", "blue"));
        }
        CSP<String, String> csp = new CSP<>(variables, domains);
        csp.addConstraint(new MapColoringConstraint(WESTERN_AUSTRALIA, NORTHERN_TERRITORY));
        csp.addConstraint(new MapColoringConstraint(WESTERN_AUSTRALIA, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(NORTHERN_TERRITORY, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(NORTHERN_TERRITORY, QUEENSLAND));
        csp.addConstraint(new MapColoringConstraint(SOUTH_AUSTRALIA, VICTORIA));
        csp.addConstraint(new MapColoringConstraint(SOUTH_AUSTRALIA, QUEENSLAND));
        csp.addConstraint(new MapColoringConstraint(NEW_SOUTH_WALES, QUEENSLAND));
        csp.addConstraint(new MapColoringConstraint(NEW_SOUTH_WALES, SOUTH_AUSTRALIA));
        csp.addConstraint(new MapColoringConstraint(NEW_SOUTH_WALES, VICTORIA));
        csp.addConstraint(new MapColoringConstraint(TASMANIA, VICTORIA));

        Map<String, String> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found");
        } else {
            System.out.println(solution);
        }
    }
}
