package datasetCons;

import java.io.IOException;

public interface DatasetCon {

    public DataPackage[] getTrainingSet() throws IOException;

    public DataPackage[] getTestSet() throws IOException;

    public int pixelsInImage();
}
