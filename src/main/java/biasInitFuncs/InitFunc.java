package biasInitFuncs;

import org.ejml.data.DMatrixRMaj;

public interface InitFunc {

    public DMatrixRMaj​ init(DMatrixRMaj​ matrix);

}
