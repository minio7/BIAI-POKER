package biai.main.execute;


import biai.models.TestData;
import biai.models.TrainingData;
import biai.neuralnet.NeuralNet;
import biai.testdatapreparer.TestDataPreparer;
import biai.testdatapreparer.impl.DefaultTestDataPreparer;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import trainingdatapreparer.TrainingDataPreparer;;
import trainingdatapreparer.impl.DefaultTrainingDataPreparer;

import javax.annotation.Resource;

public class NeuralNetLife
{
    @Autowired
    private  NeuralNet neuralNet;


    public  void executeNeuralNetLife()
    {
        try
        {
            System.out.println("Hello");


            BasicNetwork cardNetwork = neuralNet.createBasicNetwork(3, new int[]{10, 155, 1});

            TrainingDataPreparer trainingDataPreparer= new DefaultTrainingDataPreparer();
            TrainingData trainingData = trainingDataPreparer.readTrainingDataFromFile("C:/train.csv");
            trainingDataPreparer.normalizeTrainingData(trainingData);
            MLDataSet trainingSet = neuralNet.createDataSet(trainingData.getInputArray(),
                    trainingData.getOutputArray());

            neuralNet.trainNeuralNet(cardNetwork, trainingSet, 700, 0.002);

            TestDataPreparer testDataPreparer = new DefaultTestDataPreparer();
            TestData testData = testDataPreparer.readTestDataFromFile("C:/test.csv");
            testDataPreparer.normalizeInputTestData(testData);
            MLDataSet testSet = neuralNet.createDataSet(testData.getInputData(), testData.getOutputData());

            neuralNet.testNeuralNet(cardNetwork, testSet, testData.getOutputData());

            testDataPreparer.retriveOutputTestData(testData);
            testDataPreparer.saveTofile("C:/submission.csv",testData.getOutputList());

            Encog.getInstance().shutdown();

        } catch (Exception en){
            System.out.print(en);
            return;
        }
        return;
    }

    @Required
    public void setNeuralNet(NeuralNet neuralNet) {
        this.neuralNet = neuralNet;
    }

}
