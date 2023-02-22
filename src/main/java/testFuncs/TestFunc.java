package testFuncs;

import org.ejml.data.DMatrixRMaj;

public interface TestFunc {
    
    public void enterTestSample(DMatrixRMaj​ outputNodes, DMatrixRMaj​ optimalOutput);
    
    public String getQuality();
    
}
