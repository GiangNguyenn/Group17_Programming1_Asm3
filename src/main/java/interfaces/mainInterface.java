package interfaces;

import java.io.FileNotFoundException;

/**
 * define method signatures of load data and write data to csv files features
 */
public interface mainInterface {

    void loadData();

    void writeData() throws FileNotFoundException;
}
