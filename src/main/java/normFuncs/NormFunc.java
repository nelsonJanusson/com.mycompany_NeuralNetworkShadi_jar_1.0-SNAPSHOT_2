package normFuncs;

import org.ejml.data.DMatrixRMaj;

public interface NormFunc {
    
    public DMatrixRMaj[] normalize(DMatrixRMaj[] matrix);
    
}
