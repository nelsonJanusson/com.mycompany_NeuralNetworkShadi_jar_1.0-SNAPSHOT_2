package neuralNetwork;

import org.ejml.dense.row.mult.MatrixVectorMult_DDRM;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.data.DMatrixRMaj;
import activationFuncs.ActivationFunc;
import biasInitFuncs.InitFunc;

public class NeuralNetworkLayer {

    private final ActivationFunc activationFunc;
    private DMatrixRMaj​ intermediates;
    private DMatrixRMaj​ biases;
    private DMatrixRMaj​ weights;
    private DMatrixRMaj​ biasChangeVector;
    private DMatrixRMaj​ weightChangeVector;
    private DMatrixRMaj​ error;
    private DMatrixRMaj​ lastLayerOutput;
    private final double learningRateBiases;
    private final double learningRateWeights;

    public NeuralNetworkLayer(
            int layerSize,
            int lastLayerSize,
            ActivationFunc activationFunc,
            InitFunc weightInitFunc,
            InitFunc biasInitFunc,
            double learningRateBiases,
            double learningRateWeights
    ) {

        this.activationFunc = activationFunc;
        intermediates = new DMatrixRMaj​(layerSize, 1);
        biases = biasInitFunc.init(new DMatrixRMaj​(layerSize, 1));
        weights = weightInitFunc.init(new DMatrixRMaj​(layerSize, lastLayerSize));
        biasChangeVector = new DMatrixRMaj​(layerSize, 1);
        weightChangeVector = new DMatrixRMaj​(layerSize, lastLayerSize);
        error = new DMatrixRMaj​(layerSize, 1);
        lastLayerOutput = new DMatrixRMaj​(lastLayerSize, 1);
        this.learningRateBiases = learningRateBiases;
        this.learningRateWeights = learningRateWeights;

    }

    public void forwardFeeding(DMatrixRMaj​ lastLayerOutput) {

        MatrixVectorMult_DDRM.mult​(weights, lastLayerOutput, intermediates);

        CommonOps_DDRM.addEquals(intermediates, biases);

    }

    public void forwardPropagation(DMatrixRMaj​ lastLayerOutput) {

        this.lastLayerOutput = lastLayerOutput;

        MatrixVectorMult_DDRM.mult​(weights, lastLayerOutput, intermediates);

        CommonOps_DDRM.addEquals(intermediates, biases);
    }

    public DMatrixRMaj getNodes() {

        return activationFunc.activationFunction(intermediates.copy());

    }

    public void backPropagation(DMatrixRMaj​ nextLayerErrorComponent) {

        setError(nextLayerErrorComponent);

        updateChangeVectors();

    }

    private void setError(DMatrixRMaj​ nextLayerErrorComponent) {

        CommonOps_DDRM.elementMult​(nextLayerErrorComponent, activationFunc.activationFunctionDerivative(intermediates.copy()), error);

    }

    private void updateChangeVectors() {

        CommonOps_DDRM.addEquals​(biasChangeVector, error);

        DMatrixRMaj tempWeights = new DMatrixRMaj(weights);

        tempWeights.fill(1);

        CommonOps_DDRM.multRows​(error.getData(), tempWeights);

        CommonOps_DDRM.multCols​(tempWeights, lastLayerOutput.getData());

        CommonOps_DDRM.addEquals(weightChangeVector, tempWeights);
        
    }
    

    public DMatrixRMaj​ getErrorComponent() {

        DMatrixRMaj transposedWeights = new DMatrixRMaj(weights.getNumCols(), weights.getNumRows());

        CommonOps_DDRM.transpose​(weights, transposedWeights);

        DMatrixRMaj errorComponent = new DMatrixRMaj(weights.getNumCols(), 1);

        MatrixVectorMult_DDRM.mult​(transposedWeights, error, errorComponent);

        return errorComponent;

    }

    public void updateValues() {

        CommonOps_DDRM.scale​(learningRateBiases, biasChangeVector);
        CommonOps_DDRM.scale​(learningRateWeights, weightChangeVector);
        CommonOps_DDRM.subtractEquals(biases, biasChangeVector);
        CommonOps_DDRM.subtractEquals(weights, weightChangeVector);
        biasChangeVector.zero();
        weightChangeVector.zero();
    }

    public void showLayer() {

        System.out.println("nodes");
        getNodes().print();
        System.out.println();
        System.out.println();

        System.out.println("intermediates");
        intermediates.print();
        System.out.println();
        System.out.println();

        System.out.println("biases");
        biases.print();
        System.out.println();
        System.out.println();

        System.out.println("weights");
        weights.print();
        System.out.println();
        System.out.println();

        System.out.println("biasChangeVector");
        biasChangeVector.print();
        System.out.println();
        System.out.println();

        System.out.println("weightChangeVector");
        weightChangeVector.print();
        System.out.println();
        System.out.println();

        System.out.println("error");
        error.print();
        System.out.println();
        System.out.println();

        System.out.println("lastLayerOutput");
        lastLayerOutput.print();
        System.out.println();
        System.out.println();

    }
}
