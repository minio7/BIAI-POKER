package biai.testdatapreparer.impl;

import biai.models.TestData;
import biai.testdatapreparer.TestDataPreparer;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I319033 on 2015-08-03.
 */
public class DefaultTestDataPreparer implements TestDataPreparer
{

    @Override
    public TestData readTestDataFromFile(String filename) throws IOException
    {
        TestData testData = new TestData();
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));

        String header = fileReader.readLine();

        String [] headerColumns = header.split(",");
        int inputSize = headerColumns.length-1;
        testData.setInputSize(inputSize);

        List<double[]> inputList = new ArrayList<>();
        List<double[]> outputList = new ArrayList<>();
        List<long[]> output2List = new ArrayList<>();
        String row;

        while((row=fileReader.readLine()) != null)
        {
            String[] rowColumns = row.split(",");
            double[] inputRow = new double[testData.getInputSize()];
            double[] outputRow = new double[2];
            for(int i=0;i<testData.getInputSize();++i)
            {
                inputRow[i] = Double.parseDouble(rowColumns[i+1]);
            }
            outputRow[0] = Double.parseDouble(rowColumns[0]);
            outputRow[1] = 0.0;

            inputList.add(inputRow);
            outputList.add(outputRow);
        }
        double [][] inputTable = new double[inputList.size()][];
        double [][] outputTable = new double[outputList.size()][];
        for(int i=0;i<inputList.size();++i)
        {
            inputTable[i]=inputList.get(i);
            outputTable[i]= new double[] {outputList.get(i)[1]};
            long longValue = Math.round(outputList.get(i)[1]);
            output2List.add(new long[]{Math.round(outputList.get(i)[0]),longValue});
        }
        testData.setInputData(inputTable);
        testData.setOutputData(outputTable);
        testData.setOutputList(output2List);

        return testData;
    }

    @Override
    public void normalizeInputTestData(TestData testDataToNormalize)
    {
        NormalizedField normS = new NormalizedField(NormalizationAction.Normalize,
                null,13,1,1,-1);

        NormalizedField normC = new NormalizedField(NormalizationAction.Normalize,
                null,4,1,1,-1);

        NormalizedField normO = new NormalizedField(NormalizationAction.Normalize,
                null,10,0,1,-1);
        for(int i= 0 ; i<testDataToNormalize.getInputData().length;++i)
        {
            for(int j=0; j<testDataToNormalize.getInputSize();++j)
            {
                testDataToNormalize.getInputData()[i][j] = j%2 ==0 ? normC.normalize(testDataToNormalize.getInputData()[i][j])
                        : normS.normalize(testDataToNormalize.getInputData()[i][j]);
            }
        }
    }

    @Override
    public void retriveOutputTestData(TestData testDataToRetrive)
    {
        double findMin = 20.0;
        double findMax = -1.0;

        for(int i= 0 ;i<testDataToRetrive.getOutputData().length;++i)
        {
            if(testDataToRetrive.getOutputData()[i][0] > findMax)
                findMax =testDataToRetrive.getOutputData()[i][0];
            if(testDataToRetrive.getOutputData()[i][0] <findMin)
                findMin = testDataToRetrive.getOutputData()[i][0];
        }
        double interval = findMax-findMin;
        for(int i = 0; i<testDataToRetrive.getOutputData().length;++i)
        {
            testDataToRetrive.getOutputData()[i][0] -=findMin;
            testDataToRetrive.getOutputData()[i][0] /=interval;
            testDataToRetrive.getOutputData()[i][0] = Math.round(testDataToRetrive.getOutputData()[i][0]*9);
            testDataToRetrive.getOutputList().get(i)[1]=Math.round(testDataToRetrive.getOutputData()[i][0]);
        }
    }

    @Override
    public void saveTofile(String filename, List<long[]> outputListToSave) throws IOException
    {
        FileWriter writer = new FileWriter(filename);
        String header = "id,hand\n";
        writer.append(header);
        for(int i =0; i < outputListToSave.size();++i)
        {
            writer.append(outputListToSave.get(i)[0]+","+outputListToSave.get(i)[1]+"\n");
        }

        writer.flush();
        writer.close();

    }
}
