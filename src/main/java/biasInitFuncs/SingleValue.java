package biasInitFuncs;

import org.ejml.data.DMatrixRMaj;

public class SingleValue implements InitFunc {

    private final double chosenValue;

    public SingleValue(double chosenValue) {

        this.chosenValue = chosenValue;
    }

    public DMatrixRMaj​ init(DMatrixRMaj​ matrix) {
        
        matrix.fill​(chosenValue);
        
       return matrix;
    }
}
