package trainingdatapreparer;

import biai.models.TrainingData;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by I319033 on 2015-08-03.
 */
public interface TrainingDataPreparer
{
    TrainingData readTrainingDataFromFile(String filename) throws IOException;

    void  normalizeTrainingData(TrainingData dataToNormalize);
}
