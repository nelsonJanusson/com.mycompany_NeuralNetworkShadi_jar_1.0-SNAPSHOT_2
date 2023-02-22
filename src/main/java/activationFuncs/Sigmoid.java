package activationFuncs;

import org.ejml.data.DMatrixRMaj;

public class Sigmoid implements ActivationFunc {

    public Sigmoid() {
    }

    public DMatrixRMaj​ activationFunction(DMatrixRMaj​ intermediates) {

        int size = intermediates.getNumElements();

        DMatrixRMaj​ nodeValues = new DMatrixRMaj​(new double[size]);

        for (int i = 0; i < size; i++) {

            nodeValues.set(i, (double) 1 / (1 + java.lang.Math.exp(-intermediates.get(i))));

        }

        return nodeValues;
        
        
    }

    public DMatrixRMaj​ activationFunctionDerivative(DMatrixRMaj​ intermediates) {

        double placeholder;
        int size = intermediates.getNumElements();
        DMatrixRMaj​ nodeValues = activationFunction(intermediates);
        for (int i = 0; i < size; i++) {
            placeholder = (double) nodeValues.get(i) * (1 - nodeValues.get(i));
            nodeValues.set(i, placeholder);
        }
        return nodeValues;

    }
}
