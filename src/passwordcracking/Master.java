package passwordcracking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hina
 */
public class Master implements Runnable {

    private BufferedReader dictionary = null;
    private Queue<String> allDictionaryEntries = null;
    private String[] allEntries;

    public Master(Buffer buffer) {
        allDictionaryEntries = new LinkedList<String>();
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

    private void readDictionary() throws IOException, FileNotFoundException {
        FileReader fileReader = new FileReader("webster-dictionary.txt");
        final BufferedReader dictionary = new BufferedReader(fileReader);
        int i = 0;
        while (true) {
            final String dictionaryEntry = dictionary.readLine();
            allDictionaryEntries.add(dictionaryEntry);
            allEntries[i] = dictionaryEntry;
            i++;
            if (dictionaryEntry == null) {
                break;
            }
        }
    }

    public Queue<String> getAllDictionaryEntries() {
        return allDictionaryEntries;
    }

    public String[] getAllEntries() {
        return allEntries;
    }
}
