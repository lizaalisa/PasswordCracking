/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordcracking;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author Hina
 */
public class PasswordCrackMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //we create and run threads here
        Runnable master = new Master();
        Runnable slave1 = new Slave();
        Runnable slave2 = new Slave();
        
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(master);
        pool.execute(slave1);
        pool.execute(slave2);
        pool.shutdown();
    }
}
