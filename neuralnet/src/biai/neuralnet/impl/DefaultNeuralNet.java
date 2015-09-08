package biai.neuralnet.impl;

import biai.neuralnet.NeuralNet;
import org.encog.engine.network.activation.*;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class DefaultNeuralNet implements NeuralNet
{
    @Override
    public BasicNetwork createBasicNetwork(int layersNum, int[] layersSizes)
    {
        BasicNetwork basicNetwork = new BasicNetwork();
        basicNetwork.addLayer(new BasicLayer(null, true, layersSizes[0]));

        for(int i=1; i < layersNum; ++i)
        {
            basicNetwork.addLayer(new BasicLayer(new ActivationTANH(),true,layersSizes[i]));
            System.out.println(layersSizes[i]);
        }
        basicNetwork.getStructure().finalizeStructure();
        basicNetwork.reset();

        return basicNetwork;
    }

    @Override
    public MLDataSet createDataSet(double[][] input, double[][] output)
    {
       return new BasicMLDataSet(input,output);
    }

    @Override
    public void trainNeuralNet(BasicNetwork networkToTrain, MLDataSet trainingData,int maxEpochs,double maxError)
    {
        final ResilientPropagation propagation = new ResilientPropagation(networkToTrain,trainingData);
        int epoch =1;
        do{
            propagation.iteration();
        //    System.out.println("epoch: "+epoch+++" error: "+propagation.getError());
            epoch++;
        }while(propagation.getError()>maxError && epoch<maxEpochs);

        System.out.println("epochs done: " + epoch + " error: " + propagation.getError());
        propagation.finishTraining();
    }

    @Override
    public void testNeuralNet(BasicNetwork networkToTest, MLDataSet dataToTest,double[][] outputToWrite)
    {
        int index = 0;
        System.out.println("START TESTING");
        for(MLDataPair pair : dataToTest)
        {
            final MLData output = networkToTest.compute(pair.getInput());
            outputToWrite[index][0] =output.getData(0);
            index++;
        }

        System.out.println("DONE TESTING");
    }

}
