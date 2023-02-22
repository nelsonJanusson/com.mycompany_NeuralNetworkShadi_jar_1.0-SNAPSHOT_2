
package weightInitFuncs;

import org.ejml.data.DMatrixRMaj;
import biasInitFuncs.InitFunc;
import java.util.Random;

public class NewXavier implements InitFunc {

    public NewXavier() {
    }

    public DMatrixRMaj​ init(DMatrixRMaj​ matrix) {
        
        Random r = new Random();
        
        int size = matrix.getNumElements();
        
        double standardDeviation =  1 / java.lang.Math.sqrt(matrix.getNumCols());
                
        for (int i = 0; i < size; i++) {
            
            matrix.set(i, r.nextGaussian() * standardDeviation);
            
        }
        
        return matrix;
    }
}