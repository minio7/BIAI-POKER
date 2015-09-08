package biai.trainingdatapreparer;

import biai.models.TrainingData;

import java.io.IOException;


public interface TrainingDataPreparer
{
    TrainingData readTrainingDataFromFile(String filename) throws IOException;

    void  normalizeTrainingData(TrainingData dataToNormalize);
}
