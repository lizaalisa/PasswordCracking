package passwordcracking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hina
 */
public class Master implements Runnable {

    private static List<String> dictionaryEntriesList = null;

    public Master() throws IOException {
        dictionaryEntriesList = readDictionary();
    }

    @Override
    public void run() {
        try {
            final long startTime = System.currentTimeMillis();
            readDictionary();
            final long endTime = System.currentTimeMillis();
            final long usedTime = endTime - startTime;
//            System.out.println(result);
            System.out.println("Used time: " + usedTime / 1000 + " seconds = " + usedTime / 60000.0 + " minutes");
        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> readDictionary() throws IOException, FileNotFoundException {
        List<String> list = new LinkedList<String>();
        FileReader fileReader = new FileReader("webster-dictionary.txt");
        final BufferedReader dictionary = new BufferedReader(fileReader);
        while (true) {
            final String dictionaryEntry = dictionary.readLine();
            list.add(dictionaryEntry);
            if (dictionaryEntry == null) {
                break;
            }
        }
        return list;
    }

    public static List<String> getSubList(int subListNumber) {
        int partSize1 = dictionaryEntriesList.size() / 3;
        int partSize2 = (dictionaryEntriesList.size() - partSize1) / 2;
        List<String> subList1 = dictionaryEntriesList.subList(0, partSize1 - 1);
        List<String> subList2 = dictionaryEntriesList.subList(partSize1, partSize1 + partSize2 - 1);
        List<String> subList3 = dictionaryEntriesList.subList(partSize1 + partSize2, dictionaryEntriesList.size());
        List<String> value = null;
        if (subListNumber == 1) {
            value = subList1;
        }
        if (subListNumber == 2) {
            value = subList2;
        }
        if (subListNumber == 3) {
            value = subList3;
        }

        return value;

    }
}
