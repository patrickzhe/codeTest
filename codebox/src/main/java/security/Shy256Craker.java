package security;

import convert.HexConvert;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Shy256Craker {
    public static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789@#&!()^=+/:.;,".toCharArray();
    public static final int PASSWORD_MAX_LENGTH = 30;
    public static final String PASSWORD_SHA_256_TO_FIND = "8c9713d70976a2d1ea0c07a6d70998dc7da3ebca901f5b55cce470191bc86332";
    public static final byte[] BYTES_SHA_256_TO_FIND = HexConvert.hexStringToByteArray(PASSWORD_SHA_256_TO_FIND);
    public static long START_TIME;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        int cores = Runtime.getRuntime().availableProcessors();
        int lengthSet = PASSWORD_MAX_LENGTH / cores;
        int end = 0;

        START_TIME = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        while (end < PASSWORD_MAX_LENGTH) {
            int start = end + 1;
            end = start + lengthSet;

            if (end > PASSWORD_MAX_LENGTH)
                end = PASSWORD_MAX_LENGTH;

            executorService.submit(new Cracker(start, end));
        }

        executorService.shutdown();

    }
}
