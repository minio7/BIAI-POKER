import biai.models.TestData;
import biai.models.TrainingData;
import biai.testdatapreparer.TestDataPreparer;
import biai.testdatapreparer.impl.DefaultTestDataPreparer;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import trainingdatapreparer.TrainingDataPreparer;;
import trainingdatapreparer.impl.DefaultTrainingDataPreparer;

/**
 * Created by I319033 on 2015-08-03.
 */
public class Program
{
    public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
            { 0.0, 1.0 }, { 1.0, 1.0 } };

    public static double XOR_IDEAL[][] = { { 0.5 }, { 0.0 }, { 0.0 }, { 1.0 } };

    public static void main(String [] args)
    {
        try
        {
            System.out.println("Hello");
            NeuralNet neuralNet = new DefaultNeuralNet();

            BasicNetwork cardNetwork = neuralNet.createBasicNetwork(3, new int[]{10, 155, 1});

            TrainingDataPreparer trainingDataPreparer= new DefaultTrainingDataPreparer();
            TrainingData trainingData = trainingDataPreparer.readTrainingDataFromFile("C:/train.csv");
            trainingDataPreparer.normalizeTrainingData(trainingData);
            MLDataSet trainingSet = neuralNet.createDataSet(trainingData.getInputArray(),
                    trainingData.getOutputArray());

            neuralNet.trainNeuralNet(cardNetwork, trainingSet, 7000, 0.002);

            TestDataPreparer testDataPreparer = new DefaultTestDataPreparer();
            TestData testData = testDataPreparer.readTestDataFromFile("C:/test.csv");
            testDataPreparer.normalizeInputTestData(testData);
            MLDataSet testSet = neuralNet.createDataSet(testData.getInputData(),testData.getOutputData());

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

}
