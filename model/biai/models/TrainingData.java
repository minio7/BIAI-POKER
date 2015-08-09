package biai.models;
/**
 * Created by I319033 on 2015-08-03.
 */
public class TrainingData
{
    private double[][] inputArray;
    private double[][] outputArray;
    private int inputSize;

    public double[][] getInputArray()
    {
        return inputArray;
    }

    public void setInputArray(double[][] inputArray)
    {
        this.inputArray = inputArray;
    }

    public double[][] getOutputArray()
    {
        return outputArray;
    }

    public void setOutputArray(double[][] outputArray)
    {
        this.outputArray = outputArray;
    }

    public int getInputSize()
    {
        return inputSize;
    }

    public void setInputSize(int inputSize)
    {
        this.inputSize = inputSize;
    }
}
