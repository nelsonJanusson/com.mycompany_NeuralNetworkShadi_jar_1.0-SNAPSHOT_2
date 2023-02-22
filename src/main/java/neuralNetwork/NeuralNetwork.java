package neuralNetwork;

import datasetCons.DataPackage;
import java.io.IOException;
import datasetCons.DatasetCon;
import activationFuncs.ActivationFunc;
import biasInitFuncs.InitFunc;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import testFuncs.TestFunc;
import lossFuncs.LossFunc;

public class NeuralNetwork {

    private final NeuralNetworkLayer[] networkLayers;                                
    private final LossFunc lossFunc;
    private final TestFunc testFunc;
    private final DatasetCon dataSetCon;
    private final int netWorkSize;

    public NeuralNetwork(
            int[] networkLayersDescription,
            double[] learningRateBiases,
            double[] learningRateWeights,
            ActivationFunc[] activationFuncs,
            InitFunc[] weightInitFuncs,
            InitFunc[] biasInitFuncs,
            LossFunc lossFunc,
            TestFunc testFunc,
            DatasetCon dataSetCon) {
        this.lossFunc = lossFunc;
        this.testFunc = testFunc;
        this.dataSetCon = dataSetCon;
        netWorkSize = networkLayersDescription.length;
        networkLayers = new NeuralNetworkLayer[networkLayersDescription.length];
        networkLayers[0] = new NeuralNetworkLayer(
                networkLayersDescription[0],
                dataSetCon.pixelsInImage(),
                activationFuncs[0],
                weightInitFuncs[0],
                biasInitFuncs[0],
                learningRateBiases[0],
                learningRateWeights[0]
        );

        for (int i = 1; i < networkLayersDescription.length; i++) {
            networkLayers[i] = new NeuralNetworkLayer(
                    networkLayersDescription[i],
                    networkLayersDescription[i - 1],
                    activationFuncs[i],
                    weightInitFuncs[i],
                    biasInitFuncs[i],
                    learningRateBiases[i],
                    learningRateWeights[i]
            );
        }
    }

    public void stochasticTraining(int batchSize, int epochs) throws IOException {

        List<DataPackage> trainingSet = Arrays.asList(dataSetCon.getTrainingSet());

        for (int i = 0; i < epochs; i++) {

            System.out.println("epoch" + i);

            Collections.shuffle(trainingSet);

            enterEpoch(trainingSet, batchSize);

        }
    }

    private void enterEpoch(List<DataPackage> trainingSet, int batchSize) throws IOException {

        int numberOfBatches = (trainingSet.size() / batchSize);
        
        lossFunc.setBatchSize(batchSize);
        
        for (int i1 = 0; i1 < numberOfBatches; i1++) {
            
            enterBatch(trainingSet.subList(i1 * batchSize, (1 + i1) * batchSize));

        }

        if (trainingSet.size() % batchSize != 0) {
            
            lossFunc.setBatchSize(batchSize);
            
            enterBatch(trainingSet.subList(numberOfBatches * batchSize, trainingSet.size()));
        }

    }

    private void enterBatch(List<DataPackage> batch) throws IOException {

        for (DataPackage dataPackage : batch) {

            enterTrainingSample(dataPackage);

        }

        updateNetworkValues();

    }

    private void enterTrainingSample(DataPackage dataPackage) {

        forwardPropagation(dataPackage);

        backPropagation(dataPackage);

    }

    private void forwardPropagation(DataPackage dataPackage) {

        networkLayers[0].forwardPropagation(dataPackage.getData());

        for (int i = 1; i < netWorkSize; i++) {

            networkLayers[i].forwardPropagation(networkLayers[i - 1].getNodes());

        }

    }

    private void backPropagation(DataPackage dataPackage) {

        networkLayers[netWorkSize - 1].backPropagation(lossFunc.getErrorComponent(networkLayers[netWorkSize - 1].getNodes(), dataPackage.getExpectedOutcome()));

        for (int i = netWorkSize - 2; 0 <= i; i--) {

            networkLayers[i].backPropagation(networkLayers[i + 1].getErrorComponent());

        }

    }

    private void updateNetworkValues() {

        for (int i = 0; i < netWorkSize; i++) {

            networkLayers[i].updateValues();

        }
    }

    public void testNetwork() throws IOException {

        DataPackage[] testSet = dataSetCon.getTestSet();
        int testSetSize = testSet.length;

        for (int i = 0; i < testSetSize; i++) {

            enterTestSample(testSet[i]);

            testFunc.enterTestSample(networkLayers[netWorkSize - 1].getNodes(), testSet[i].getExpectedOutcome());
        }
        System.out.println(testFunc.getQuality());
    }

    private void enterTestSample(DataPackage dataPackage) {

        networkLayers[0].forwardFeeding(dataPackage.getData());
        for (int i = 1; i < netWorkSize; i++) {

            networkLayers[i].forwardFeeding(networkLayers[i - 1].getNodes());

        }
    }

    public void showNetwork() {

        for (int i = 0; i < netWorkSize; i++) {
            System.out.println("layer " + i);
            System.out.println();
            System.out.println();

            networkLayers[i].showLayer();
            System.out.println();
            System.out.println();
            System.out.println();

        }

    }

}
