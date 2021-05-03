package chapter2.search.controlled_passage;

import chapter2.search.GenericSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ControlledPassage {
    /*
    Chapter 2.3
    The book uses a Missionaries/Cannibals comparison which I find troubling. As if to suggest that indigenous peoples
    are dangerous and `missionaries`... well you get it. I'm changing this to FunctionalCitizens/ProudBoys.
     */

    private static final int MAX_NUM = 3;
    private final int westBankFunctionalCitizens;
    private final int westBankProudBoys;
    private final int eastBankFunctionalCitizens;
    private final int eastBankProudBoys;
    private final boolean boatOnWestBank;

    public ControlledPassage(int functionalCitizens, int proudBoys, boolean boatOnWestBank) {
        westBankFunctionalCitizens = functionalCitizens;
        westBankProudBoys = proudBoys;
        eastBankFunctionalCitizens = MAX_NUM - functionalCitizens;
        eastBankProudBoys = MAX_NUM - proudBoys;
        this.boatOnWestBank = boatOnWestBank;
    }

    public boolean goalTest() {
        return isLegal() && eastBankFunctionalCitizens == MAX_NUM && eastBankProudBoys == MAX_NUM;
    }

    public boolean isLegal() {
        if (westBankFunctionalCitizens < westBankProudBoys && westBankFunctionalCitizens > 0) {
            return false;
        }
        if (eastBankFunctionalCitizens < eastBankProudBoys && eastBankFunctionalCitizens > 0) {
            return false;
        }
        return true;
    }

    public static List<ControlledPassage> successors(ControlledPassage controlledPassage) {
        List<ControlledPassage> possiblePassages = new ArrayList<>();
        int i = MAX_NUM - 1;
        if (controlledPassage.boatOnWestBank) {
            while (i > 0) {
                if (controlledPassage.westBankFunctionalCitizens > i - 1) {
                    possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens - i, controlledPassage.westBankProudBoys, false));
                }
                if (controlledPassage.westBankProudBoys > i - 1) {
                    possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens, controlledPassage.westBankProudBoys - i, false));
                }
                i--;
            }
            if (controlledPassage.westBankProudBoys > 0 && controlledPassage.westBankFunctionalCitizens > 0) {
                possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens - 1, controlledPassage.westBankProudBoys - 1, false));
            }
        } else {
            while (i > 0) {
                if (controlledPassage.eastBankFunctionalCitizens > i - 1) {
                    possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens + i, controlledPassage.westBankProudBoys, true));
                }
                if (controlledPassage.eastBankProudBoys > i - 1) {
                    possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens, controlledPassage.westBankProudBoys + i, true));
                }
                i --;
            }
            if (controlledPassage.eastBankProudBoys > 0 && controlledPassage.eastBankFunctionalCitizens > 0) {
                possiblePassages.add(new ControlledPassage(controlledPassage.westBankFunctionalCitizens + 1, controlledPassage.westBankProudBoys + 1, true));
            }
        }
        possiblePassages.removeIf(Predicate.not(ControlledPassage::isLegal));
        return possiblePassages;
    }

    public static void displaySolution(List<ControlledPassage> path) {
        if (path.size() == 0) {
            return;
        }
        ControlledPassage oldState = path.get(0);
        System.out.println(oldState);
        for (ControlledPassage currentState : path.subList(1, path.size())) {
            if (currentState.boatOnWestBank) {
                System.out.printf(
                        "%d functional citizens and %d proud boys moved from the east bank to the west bank.%n",
                        oldState.eastBankFunctionalCitizens - currentState.eastBankFunctionalCitizens,
                        oldState.eastBankProudBoys - currentState.eastBankProudBoys
                );
            } else {
                System.out.printf("%d functional citizens and %d proud boys moved from the west bank to the east bank.%n",
                        oldState.westBankFunctionalCitizens - currentState.westBankFunctionalCitizens,
                        oldState.westBankProudBoys - currentState.westBankProudBoys
                );
            }
            System.out.println(currentState);
            oldState = currentState;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlledPassage that = (ControlledPassage) o;
        return westBankFunctionalCitizens == that.westBankFunctionalCitizens && westBankProudBoys == that.westBankProudBoys && eastBankFunctionalCitizens == that.eastBankFunctionalCitizens && eastBankProudBoys == that.eastBankProudBoys && boatOnWestBank == that.boatOnWestBank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(westBankFunctionalCitizens, westBankProudBoys, eastBankFunctionalCitizens, eastBankProudBoys, boatOnWestBank);
    }

    @Override
    public String toString() {
        return String.format(
                "On the west bank there are %d functional citizens and %d proud boys.%n"
                        + "On the east bank there are %d functional citizens and %d proud boys.%n"
                        + "The boat is on the %s bank.%n"
                        + "********************",
                westBankFunctionalCitizens, westBankProudBoys, eastBankFunctionalCitizens, eastBankProudBoys,
                boatOnWestBank ? "west" : "east"
        );
    }

    public static void main(String[] args) {
        ControlledPassage start = new ControlledPassage(MAX_NUM, MAX_NUM, true);
        GenericSearch.Node<ControlledPassage> solution = GenericSearch.breadthFirstSearch(start, ControlledPassage::goalTest, ControlledPassage::successors);
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            List<ControlledPassage> path = GenericSearch.nodeToPath(solution);
            displaySolution(path);
        }
    }
}
