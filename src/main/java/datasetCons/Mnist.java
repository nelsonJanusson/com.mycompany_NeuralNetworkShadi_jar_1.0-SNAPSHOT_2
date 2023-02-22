package datasetCons;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.ejml.data.DMatrixRMaj;
import normFuncs.NormFunc;

public class Mnist implements DatasetCon {

    private final NormFunc normalizingFunc;

    public Mnist(NormFunc normalizingFunc) {

        this.normalizingFunc = normalizingFunc;

    }

    public DataPackage[] getTestSet() throws IOException {

        DMatrixRMaj​[] imageTestDataArray = new DMatrixRMaj[10000];

        DMatrixRMaj​ imageTestData = new DMatrixRMaj​(new double[784]);

        FileInputStream fileInputTestImages = new FileInputStream(System.getProperty("user.dir") + "\\MnistData\\t10k-images.idx3-ubyte");

        BufferedInputStream inputTestImages = new BufferedInputStream(fileInputTestImages);

        inputTestImages.skip(16);

        for (int i1 = 0; i1 < 10000; i1++) {

            for (int i2 = 0; i2 < 784; i2++) {

                imageTestData.set(i2, inputTestImages.read());

            }

            imageTestDataArray​[i1] = imageTestData.copy();

        }
        inputTestImages.close();

        imageTestDataArray​ = normalizingFunc.normalize(imageTestDataArray​);

        DMatrixRMaj​ labelTestData = new DMatrixRMaj​(new double[10]);

        DataPackage[] testSet = new DataPackage[10000];

        FileInputStream fileInputTestLabels = new FileInputStream(System.getProperty("user.dir") + "\\MnistData\\t10k-labels.idx1-ubyte");

        BufferedInputStream inputTestLabels = new BufferedInputStream(fileInputTestLabels);

        inputTestLabels.skip(8);

        for (int i = 0; i < 10000; i++) {

            labelTestData.zero();

            labelTestData.set(inputTestLabels.read(), 1);
            testSet[i] = new DataPackage(imageTestDataArray​[i].copy(), labelTestData.copy());

        }
        inputTestLabels.close();

        return testSet;
    }

    public DataPackage[] getTrainingSet() throws IOException {

        DMatrixRMaj​[] imageTrainingDataArray = new DMatrixRMaj[60000];
        DMatrixRMaj​ imageTrainData = new DMatrixRMaj​(new double[784]);

        FileInputStream fileInputTrainingImages = new FileInputStream(System.getProperty("user.dir") + "\\MnistData\\train-images.idx3-ubyte");

        BufferedInputStream inputTrainingImages = new BufferedInputStream(fileInputTrainingImages);

        inputTrainingImages.skip(16);

        for (int i1 = 0; i1 < 60000; i1++) {

            for (int i2 = 0; i2 < 784; i2++) {

                imageTrainData.set(i2, inputTrainingImages.read());
            }

            imageTrainingDataArray[i1] = imageTrainData.copy();

        }

        inputTrainingImages.close();

        imageTrainingDataArray = normalizingFunc.normalize(imageTrainingDataArray);

        DMatrixRMaj​ labelTrainingData = new DMatrixRMaj​(new double[10]);
        DataPackage[] trainingSet = new DataPackage[60000];

        FileInputStream fileInputTrainingLabels = new FileInputStream(System.getProperty("user.dir") + "\\MnistData\\train-labels.idx1-ubyte");

        BufferedInputStream inputTrainingLabels = new BufferedInputStream(fileInputTrainingLabels);
        inputTrainingLabels.skip(8);

        for (int i = 0; i < 60000; i++) {

            labelTrainingData.zero();

            labelTrainingData.set(inputTrainingLabels.read(), 1);

            trainingSet[i] = new DataPackage(imageTrainingDataArray[i].copy(), labelTrainingData.copy());
        }
        inputTrainingLabels.close();
        return trainingSet;
    }

    public int pixelsInImage() {
        return 784;//this should be read from the file instead of hardcoded. Temp solution #TODO
    }
}
