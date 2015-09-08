package biai.main.execute;

import biai.models.TestData;
import biai.models.TrainingData;
import biai.neuralnet.NeuralNet;
import biai.testdatapreparer.TestDataPreparer;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.PropertySource;
import biai.trainingdatapreparer.TrainingDataPreparer;;

import javax.annotation.Resource;
import java.util.Properties;

@PropertySource(value = "claspath:/neutralnet.properties")
public class NeuralNetLife
{
    @Autowired
    private  NeuralNet neuralNet;

    @Resource(name = "neuralNetProperties")
    private Properties properties;

    @Autowired
    private TrainingDataPreparer trainingDataPreparer;

    @Autowired
    private TestDataPreparer testDataPreparer;

    public  void executeNeuralNetLife()
    {
        try
        {
            System.out.println(properties.getProperty("welcomeMessage"));
            int layerNumber = Integer.parseInt(properties.getProperty("layerNumber"));
            String layerSizesString = properties.getProperty("layerSizes");
            String[] layerSizesArr = layerSizesString.split(",");
            int[] layerSizesInt = new int[layerSizesArr.length];

            for(int i=0;i<layerSizesInt.length;++i)
                layerSizesInt[i] = Integer.parseInt(layerSizesArr[i]);

            BasicNetwork cardNetwork = neuralNet.createBasicNetwork(layerNumber, layerSizesInt);

            TrainingData trainingData = trainingDataPreparer.readTrainingDataFromFile(properties.getProperty("inputFile"));
            trainingDataPreparer.normalizeTrainingData(trainingData);
            MLDataSet trainingSet = neuralNet.createDataSet(trainingData.getInputArray(),
                    trainingData.getOutputArray());

            int maxEpochs = Integer.parseInt(properties.getProperty("maxEpochs"));
            double minError = Double.parseDouble(properties.getProperty("minError"));
            neuralNet.trainNeuralNet(cardNetwork, trainingSet, maxEpochs, minError);

            TestData testData = testDataPreparer.readTestDataFromFile(properties.getProperty("testFile"));
            testDataPreparer.normalizeInputTestData(testData);
            MLDataSet testSet = neuralNet.createDataSet(testData.getInputData(), testData.getOutputData());

            neuralNet.testNeuralNet(cardNetwork, testSet, testData.getOutputData());

            testDataPreparer.retriveOutputTestData(testData);
            testDataPreparer.saveTofile(properties.getProperty("outputFile"), testData.getOutputList());

            Encog.getInstance().shutdown();

        } catch (Exception en){
            System.out.print(en);
            return;
        }
        return;
    }

    @Required
    public void setNeuralNet(final NeuralNet neuralNet) {
        this.neuralNet = neuralNet;
    }

    @Required
    public void setTrainingDataPreparer(final TrainingDataPreparer trainingDataPreparer) {
        this.trainingDataPreparer = trainingDataPreparer;
    }

    @Required
    public void setTestDataPreparer(final TestDataPreparer testDataPreparer) {
        this.testDataPreparer = testDataPreparer;
    }
}
