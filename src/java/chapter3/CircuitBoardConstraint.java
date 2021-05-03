package chapter3;

import java.util.*;
import java.util.stream.Collectors;

import static chapter3.CircuitBoard.*;

public class CircuitBoardConstraint extends Constraint<Component, List<GridLocation>> {
    public CircuitBoardConstraint(List<Component> components) {
        super(components);
    }

    @Override
    public boolean satisfied(Map<Component, List<GridLocation>> assignment) {
        //combine all GridLocations into one giant List
        List<GridLocation> allLocations = assignment.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        //a Set will eliminate duplicates using equals()
        Set<GridLocation> allLocationsSet = new HashSet<>(allLocations);
        //if there are any duplicate grid locations then there is an overlap
        return allLocations.size() == allLocationsSet.size();
    }

    public static void main(String[] args) {
        CircuitBoard circuitBoard = new CircuitBoard(4, 4);
        List<Component> components = List.of(
//                new Component(1, 2),,
                new Component(3, 3),
//                new Component(2, 2),
                new Component(1, 1)
//                new Component(1, 1)
        );
        Map<Component, List<List<GridLocation>>> domains = new HashMap<>();
        for (Component component : components) {
            domains.put(component, circuitBoard.generateDomain(component));
        }

        CSP<Component, List<GridLocation>> csp = new CSP<>(components, domains);
        csp.addConstraint(new CircuitBoardConstraint(components));
        Map<Component, List<GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            for (Map.Entry<Component, List<GridLocation>> item : solution.entrySet()) {
                List<GridLocation> locations = item.getValue();
                circuitBoard.mark(locations);
            }
            System.out.println(circuitBoard);
        }
    }
}
