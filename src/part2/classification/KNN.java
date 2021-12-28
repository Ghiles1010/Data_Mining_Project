package part2.classification;

import common.Dataset;

public class KNN extends BaseClassification{

    private int k;

    public KNN(Dataset dataset, int k) {
        super(dataset);
        this.k = k;
    }

    public KNN(String path, int k) {
        super(path);
        this.k = k;
    }
}
