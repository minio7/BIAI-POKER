package biai.trainingdatapreparer.impl;

import biai.models.TrainingData;
import biai.trainingdatapreparer.TrainingDataPreparer;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I319033 on 2015-08-03.
 */
public class DefaultTrainingDataPreparer implements TrainingDataPreparer
{
    @Override
    public TrainingData readTrainingDataFromFile(String filename) throws IOException
    {
        TrainingData trainingData = new TrainingData();
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));

        String header = fileReader.readLine();
        String [] headerColumns = header.split(",");
        int inputSize = headerColumns.length-1;

        trainingData.setInputSize(inputSize);
        List<double[]> inputList = new ArrayList<>();
        List<double[]> outputList = new ArrayList<>();

        String rowData;
        while ((rowData = fileReader.readLine()) != null)
        {
            String [] rowFields = rowData.split(",");
            double [] inputRow = new double[inputSize];

            for(int i =0 ; i < inputSize ;++i)
            {
                inputRow[i] = Double.parseDouble(rowFields[i]);
            }
            inputList.add(inputRow);
            outputList.add(new double[] {Double.parseDouble(rowFields[inputSize])});
        }

        double [][] inputTable = new double[inputList.size()][];
        double[][] outputTable = new double[outputList.size()][];

        for(int i=0;i<inputList.size();++i)
        {
            inputTable[i] = inputList.get(i);
            outputTable[i] = outputList.get(i);
        }
        trainingData.setInputArray(inputTable);
        trainingData.setOutputArray(outputTable);

        return  trainingData;
    }

    @Override
    public void normalizeTrainingData(TrainingData dataToNormalize)
    {
        NormalizedField normS = new NormalizedField(NormalizationAction.Normalize,
                null,13,1,1,-1);

        NormalizedField normC = new NormalizedField(NormalizationAction.Normalize,
                null,4,1,1,-1);

        NormalizedField normO = new NormalizedField(NormalizationAction.Normalize,
                null,9,0,1,-1);
        for(int i= 0 ; i<dataToNormalize.getInputArray().length;++i)
        {
            dataToNormalize.getOutputArray()[i][0] = normO.normalize(dataToNormalize.getOutputArray()[i][0]);
            for(int j=0; j<dataToNormalize.getInputSize();++j)
            {
                dataToNormalize.getInputArray()[i][j] = j%2 ==0 ? normC.normalize(dataToNormalize.getInputArray()[i][j])
                        : normS.normalize(dataToNormalize.getInputArray()[i][j]);
            }
        }
    }

}
