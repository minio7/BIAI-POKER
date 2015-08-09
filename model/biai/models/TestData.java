package biai.models;

import java.util.List;

/**
 * Created by I319033 on 2015-08-03.
 */
public class TestData
{
    private double [][] inputData;
    private double[][] outputData;
    private List<long[]> outputList;
    private int inputSize;

    public double[][] getInputData()
    {
        return inputData;
    }

    public void setInputData(double[][] inputData)
    {
        this.inputData = inputData;
    }

    public double[][] getOutputData()
    {
        return outputData;
    }

    public void setOutputData(double[][] outputData)
    {
        this.outputData = outputData;
    }

    public List<long[]> getOutputList()
    {
        return outputList;
    }

    public void setOutputList(List<long[]> outputList)
    {
        this.outputList = outputList;
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
