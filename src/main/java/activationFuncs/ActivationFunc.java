package activationFuncs;

import org.ejml.data.DMatrixRMaj;

public interface ActivationFunc {

    public DMatrixRMaj​ activationFunction(DMatrixRMaj​ intermediates);

    public DMatrixRMaj​ activationFunctionDerivative(DMatrixRMaj​ intermediates);
    
}
