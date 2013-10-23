package passwordcracking;

import dk.rhs.distributedpasswordcracking.PasswordFileHandler;
import dk.rhs.distributedpasswordcracking.UserInfo;
import dk.rhs.distributedpasswordcracking.UserInfoClearText;
import dk.rhs.util.StringUtilities;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hina
 */
public class Slave implements Runnable {

    private static MessageDigest messageDigest;
    private static final Logger LOGGER = Logger.getLogger("passwordCracker");
    private String dictionaryEntry = null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        try {
            final List<UserInfo> userInfos = PasswordFileHandler.readPasswordFile("passwords.txt");
            final List<UserInfoClearText> result = new ArrayList<UserInfoClearText>();
            final List<UserInfoClearText> partialResult = checkWordWithVariations(dictionaryEntry, userInfos);
            result.addAll(partialResult);
        } catch (IOException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks a single word from a dictionary, against a list of encrypted
     * passwords. Tries different variations on the dictionary entry, like all
     * uppercase, adding digits to the end of the entry, etc.
     *
     * @param dictionaryEntry a single word from a dictionary, i.e. a possible
     * password
     * @param userInfos a list of user information records: username + encrypted
     * password
     */
    static List<UserInfoClearText> checkWordWithVariations(final String dictionaryEntry, final List<UserInfo> userInfos) {
        final List<UserInfoClearText> result = new ArrayList<UserInfoClearText>();

        final String possiblePassword = dictionaryEntry;
        final List<UserInfoClearText> partialResult = checkSingleWord(userInfos, possiblePassword);
        result.addAll(partialResult);

        final String possiblePasswordUpperCase = dictionaryEntry.toUpperCase();
        final List<UserInfoClearText> partialResultUpperCase = checkSingleWord(userInfos, possiblePasswordUpperCase);
        result.addAll(partialResultUpperCase);

        final String possiblePasswordCapitalized = StringUtilities.capitalize(dictionaryEntry);
        final List<UserInfoClearText> partialResultCapitalized = checkSingleWord(userInfos, possiblePasswordCapitalized);
        result.addAll(partialResultCapitalized);

        final String possiblePasswordReverse = new StringBuilder(dictionaryEntry).reverse().toString();
        final List<UserInfoClearText> partialResultReverse = checkSingleWord(userInfos, possiblePasswordReverse);
        result.addAll(partialResultReverse);

        for (int i = 0; i < 100; i++) {
            final String possiblePasswordEndDigit = dictionaryEntry + i;
            final List<UserInfoClearText> partialResultEndDigit = checkSingleWord(userInfos, possiblePasswordEndDigit);
            result.addAll(partialResultEndDigit);
        }

        for (int i = 0; i < 100; i++) {
            final String possiblePasswordStartDigit = i + dictionaryEntry;
            final List<UserInfoClearText> partialResultStartDigit = checkSingleWord(userInfos, possiblePasswordStartDigit);
            result.addAll(partialResultStartDigit);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                final String possiblePasswordStartEndDigit = i + dictionaryEntry + j;
                final List<UserInfoClearText> partialResultStartEndDigit = checkSingleWord(userInfos, possiblePasswordStartEndDigit);
                result.addAll(partialResultStartEndDigit);
            }
        }

        return result;
    }

    /**
     * Check a single word (may include a single variation)from the dictionary
     * against a list of encrypted passwords
     *
     * @param userInfos a list of user information records: username + encrypted
     * password
     * @param possiblePassword a single dictionary entry (may include a single
     * variation)
     * @return the user information record, if the dictionary entry matches the
     * users password, or {@code  null} if not.
     */
    static List<UserInfoClearText> checkSingleWord(final List<UserInfo> userInfos, final String possiblePassword) {
        final byte[] digest = messageDigest.digest(possiblePassword.getBytes());
        final List<UserInfoClearText> results = new ArrayList<UserInfoClearText>();
        for (UserInfo userInfo : userInfos) {
            if (Arrays.equals(userInfo.getEntryptedPassword(), digest)) {
                results.add(new UserInfoClearText(userInfo.getUsername(), possiblePassword));
            }
        }
        return results;
    }
}
