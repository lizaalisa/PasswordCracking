/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordcracking;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Hina
 */
public class Slave implements Runnable {

    private static MessageDigest messageDigest;
    public static final String MESSAGE_DIGEST_ALGORITHM = "SHA";
    private static final Logger LOGGER = Logger.getLogger("passwordCracker");
//    private final String username;
//    private final String password;
    private String word;
    

    @Override
    public void run() {
        
        //take() = ;
        
}
}