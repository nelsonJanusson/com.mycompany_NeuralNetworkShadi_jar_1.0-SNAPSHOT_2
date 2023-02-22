package datasetCons;

import org.ejml.data.DMatrixRMaj;

public class DataPackage {

    private final DMatrixRMaj​ data;
    private final DMatrixRMaj​ expectedOutcome;

    public DataPackage(DMatrixRMaj​ data, DMatrixRMaj​ expectedOutcome) {

        this.expectedOutcome = expectedOutcome;
        this.data = data;
    }

    public DMatrixRMaj​ getExpectedOutcome() {

        return expectedOutcome.copy();
    }

    public DMatrixRMaj​ getData() {

        return data.copy();
    }
}
