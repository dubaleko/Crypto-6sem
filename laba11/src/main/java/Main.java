import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String string = "Dubaleko Valentin";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        long start = System.currentTimeMillis();
        byte[] bytes = md5.digest(string.getBytes());
        long end = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes){
            builder.append(String.format("%02X ", b));
        }
        System.out.println("MD5: "+builder.toString());
        System.out.println("Time to execute algorithm MD5: "+(end-start)+"ms");
    }
}
