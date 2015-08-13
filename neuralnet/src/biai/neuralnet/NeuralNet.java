package biai.neuralnet;

import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;

/**
 * Created by I319033 on 2015-08-03.
 */

public interface NeuralNet
{
    BasicNetwork createBasicNetwork(int layersNum, int[] layersSizes);

    MLDataSet createDataSet(double[][]input, double[][] output);

    void trainNeuralNet(BasicNetwork networkToTrain, MLDataSet trainingData, int maxEpochs,double maxError);

    void testNeuralNet(BasicNetwork networkToTest, MLDataSet dataToTest, double[][] outputToWrite);
}
