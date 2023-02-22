package activationFuncs;

import org.ejml.data.DMatrixRMaj;

public class SoftMax implements ActivationFunc {

    public SoftMax() {
    }

    public DMatrixRMaj​ activationFunction(DMatrixRMaj​ intermediates) {

        double denominator = 0;

        int size = intermediates.getNumElements();

        DMatrixRMaj​ nodeValues = new DMatrixRMaj​(new double[size]);

        for (int i = 0; i < size; i++) {

            denominator += java.lang.Math.exp(intermediates.get(i));

        }

        for (int i = 0; i < size; i++) {

            nodeValues.set(i, java.lang.Math.exp(intermediates.get(i)) / denominator);
        }

        return nodeValues;

    }

    public DMatrixRMaj​ activationFunctionDerivative(DMatrixRMaj​ intermediates) {

        int size = intermediates.getNumElements();

        DMatrixRMaj​ nodeValues = new DMatrixRMaj​(new double[size]);

        DMatrixRMaj​ nodes = activationFunction(intermediates);

        double placeholder;

        for (int i = 0; i < size; i++) {

            placeholder = 0;

            for (int i2 = 0; i2 < size; i2++) {

                if (i == i2) {

                    placeholder += nodes.get(i) * (1 - nodes.get(i2));

                } else if (i != i2) {

                    placeholder += -1 * nodes.get(i) * nodes.get(i2);

                }

            }

            nodeValues.set(i, placeholder);

        }

        return nodeValues;

    }

}
