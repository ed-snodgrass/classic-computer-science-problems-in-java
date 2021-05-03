package chapter3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueensConstraint extends Constraint<Integer, Integer> {
    private List<Integer> columns;

    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }

    public static void main(String[] args) {
        List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        Map<Integer, List<Integer>> rows = new HashMap<>();
        for (Integer column : columns) {
            rows.put(column, List.of(1, 2, 3, 4, 5, 6, 7, 8));
        }
        CSP<Integer, Integer> csp = new CSP<>(columns, rows);
        csp.addConstraint(new QueensConstraint(columns));
        Map<Integer, Integer> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found");
        } else {
            System.out.println(solution);
        }
    }

    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        for(Map.Entry<Integer, Integer> item : assignment.entrySet()) {
            int queen1Column = item.getKey();
            int queen1Row = item.getValue();
            for (int queen2Column = queen1Column + 1; queen2Column <= columns.size(); queen2Column++) {
                if (assignment.containsKey(queen2Column)) {
                    int queen2Row = assignment.get(queen2Column);
                    if (queen1Row == queen2Row) {
                        return false;
                    }
                    if (Math.abs(queen1Row - queen2Row) == Math.abs(queen1Column - queen2Column)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
