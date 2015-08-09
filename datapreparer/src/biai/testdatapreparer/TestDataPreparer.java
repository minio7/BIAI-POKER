package biai.testdatapreparer;

import biai.models.TestData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by I319033 on 2015-08-03.
 */
public interface TestDataPreparer
{
    TestData readTestDataFromFile(String filename) throws IOException;

    void normalizeInputTestData(TestData testDataToNormalize);

    void retriveOutputTestData(TestData testDataToRetrive);

    void saveTofile(String filename, List<long[]> outputListToSave) throws IOException;
}
