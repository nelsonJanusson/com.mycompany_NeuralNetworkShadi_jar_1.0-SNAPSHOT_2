package lossFuncs;

import org.ejml.data.DMatrixRMaj;

public interface LossFunc {

    public DMatrixRMaj​ getErrorComponent(DMatrixRMaj​ outputNodes, DMatrixRMaj​ optimalOutput);

    public void setBatchSize(double batchSize);

}
