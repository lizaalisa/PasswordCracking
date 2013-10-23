package passwordcracking;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Hina
 */
public class PasswordCrackMain {

    private static final int SLAVE_COUNT = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Buffer buffer = new Buffer();
        pool.execute(new Master(buffer));
        for (int i = 0; i < SLAVE_COUNT; i++) {
            pool.execute(new Slave());
        }
        pool.shutdown();
    }
}
