package lossFuncs;

import org.ejml.data.DMatrixRMaj;

public class LogLossAdvanced implements LossFunc {
    
    private double batchSize;

    public LogLossAdvanced() {

    }

    public void setBatchSize(double batchSize) {
        
        this.batchSize = batchSize;
        
    }

    public DMatrixRMaj​ getErrorComponent(DMatrixRMaj​ outputNodes, DMatrixRMaj​ optimalOutput) {

        int size = outputNodes.getNumElements();

        DMatrixRMaj​ errorComponent = new DMatrixRMaj​(new double[size]);

        double placeholder;

        for (int i = 0; i < size; i++) {

            placeholder = (optimalOutput.get(i) / outputNodes.get(i)) + ((optimalOutput.get(i) - 1) / (i - outputNodes.get(i)));
            placeholder = (placeholder *-1 / batchSize);
            errorComponent.set(i, placeholder);
        }

        return errorComponent;
    }

}
