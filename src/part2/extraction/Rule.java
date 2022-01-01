package part2.extraction;

import java.util.Set;

public class Rule {

    Set<String> leftSide, rightSide;
    double confidence;

    public Rule(Set<String> leftSide, Set<String> rightSide, double confidence) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return leftSide + " => " + rightSide;
    }
}
