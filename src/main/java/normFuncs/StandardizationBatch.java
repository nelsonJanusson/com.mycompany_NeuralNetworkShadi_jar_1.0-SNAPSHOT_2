package normFuncs;

import java.math.*;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class StandardizationBatch implements NormFunc {

    public StandardizationBatch() {

    }
    //sets the mean to zero and standard deviation to 1 
    public DMatrixRMaj[] normalize(DMatrixRMaj[] matrices) {
        
        BigDecimal mean = new BigDecimal("0");
        BigDecimal variance = new BigDecimal("0");
        BigDecimal populationSize = BigDecimal.valueOf((long) matrices.length);
        
        populationSize = populationSize.multiply(BigDecimal.valueOf((long) matrices[0].getNumElements()));

        for (DMatrixRMaj matrix : matrices) {

            mean = mean.add(BigDecimal.valueOf((long) CommonOps_DDRM.elementSum​(matrix)));

        }

        mean = mean.divide(populationSize, 20, RoundingMode.CEILING);

        DMatrixRMaj placeholder = new DMatrixRMaj​(new double[matrices[0].getNumElements()]);

        for (DMatrixRMaj matrix : matrices) {

            placeholder.set(matrix.copy());
            CommonOps_DDRM.add​(placeholder, -mean.longValue());

            for (int i = 0; i < matrix.getNumElements(); i++) {

                variance = variance.add(BigDecimal.valueOf((long) Math.pow(matrix.get(i), 2)));

            }

        }

        variance = variance.divide(populationSize, 20, RoundingMode.CEILING);
        variance = variance.sqrt(new MathContext​(20, RoundingMode.CEILING) );

        DMatrixRMaj​[] newMatrices = new DMatrixRMaj[matrices.length];

        for (int i = 0; i < matrices.length; i++) {

            newMatrices[i]=(matrices​[i]).copy();
            CommonOps_DDRM.add​(newMatrices[i], -mean.longValue());
            CommonOps_DDRM.divide​(newMatrices[i], variance.longValue());
        }
        return newMatrices;

    }

}
