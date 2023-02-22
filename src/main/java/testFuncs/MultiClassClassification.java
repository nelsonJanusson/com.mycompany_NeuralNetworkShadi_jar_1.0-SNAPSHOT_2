package testFuncs;

import org.ejml.data.DMatrixRMaj;

public class MultiClassClassification implements TestFunc {

    private double numberOfTestSamples;
    private double numberOfCorrectOutcomes;

    public MultiClassClassification() {
        
        this.numberOfTestSamples = 0;
        this.numberOfCorrectOutcomes = 0;
    }

    public void enterTestSample(DMatrixRMaj​ outputNodes, DMatrixRMaj​ optimalOutput) {
       
        numberOfTestSamples++;
        
        double currentMax = outputNodes.get(0);
        
        int adressOfMax = 0;
        
        int numberOfOutputs = outputNodes.getNumElements();
        
        for(int i = 1; i<numberOfOutputs; i ++){
            
            if (outputNodes.get(i)>currentMax){
                
                currentMax = outputNodes.get(i);
                
                adressOfMax = i;
                
            }
            
        }
        
        if(optimalOutput.get(adressOfMax)==1){
            
            numberOfCorrectOutcomes++;       
            
        }       
        
    }

    public String getQuality() {
        
        return ((numberOfCorrectOutcomes / numberOfTestSamples) * 100 + " Percent of classifications were correct");
    }
}
