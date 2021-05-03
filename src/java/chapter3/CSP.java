package chapter3;

import java.util.*;

public class CSP<V, D> {
    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            }
            constraints.get(variable).add(constraint);
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    public Map<V, D> backtrackingSearch(Map<V, D> assignment) {
        //assignment is complete if every variable is assigned (base case)
        if (assignment.size() == variables.size()) {
            return assignment;
        }
        // get the first variable in the CSP but not in the assignment
        V unassigned = variables.stream().filter(v -> !assignment.containsKey(v)).findFirst().orElse(null);
        //look through every domain value of the first unassigned variable
        for (D value : domains.get(unassigned)) {
            //shallow copy of assignment that we can change
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            // if we're still consistent, we recurse (continue)
            if (consistent(unassigned, localAssignment)) {
                Map<V, D> result = backtrackingSearch(localAssignment);
                //if we didn't find the result, we end up backtracking
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    //helper for backtrackingSearch when nothing known yet
    public Map<V, D> backtrackingSearch() {
        return backtrackingSearch(new HashMap<>());
    }
}
