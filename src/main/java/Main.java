
import weightInitFuncs.NewXavier;
import testFuncs.MultiClassClassification;
import lossFuncs.LogLossAdvanced;
import datasetCons.Mnist;
import activationFuncs.SoftMax;
import activationFuncs.Sigmoid;
import biasInitFuncs.SingleValue;
import normFuncs.StandardizationBatch;
import neuralNetwork.NeuralNetwork;
import java.io.IOException;
import activationFuncs.ActivationFunc;
import biasInitFuncs.InitFunc;

public class Main {

    public static void main(String[] args) throws IOException {

        int[] networkLayersDescription = {50, 30, 10};
        double[] learningRateBiases = {4, 4, 4};
        double[] learningRateWeights = {4, 4, 4};
        ActivationFunc[] activationFunctions = {new Sigmoid(), new Sigmoid(), new SoftMax()};
        InitFunc[] weightInitialisationFunctions = {new NewXavier(), new NewXavier(), new NewXavier()};
        InitFunc[] biasInitialisationFunctions = {new SingleValue(0), new SingleValue(0), new SingleValue(0)};

        NeuralNetwork imageRecogniser = new NeuralNetwork(
                networkLayersDescription,
                learningRateBiases,
                learningRateWeights,
                activationFunctions,
                weightInitialisationFunctions,
                biasInitialisationFunctions,
                new LogLossAdvanced(),
                new MultiClassClassification(),
                new Mnist(new StandardizationBatch())
        );

        imageRecogniser.stochasticTraining(10, 20);
        imageRecogniser.testNetwork();

    }
}
