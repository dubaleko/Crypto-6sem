import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class RC4 {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        byte [] key = new byte[]{43,45,100,21,1};
        String clearText = "hello world";

        Cipher rc4 = Cipher.getInstance("RC4");
        SecretKeySpec rc4Key = new SecretKeySpec(key, "RC4");
        rc4.init(Cipher.ENCRYPT_MODE, rc4Key);

        byte [] bytes = rc4.doFinal(clearText.getBytes());
        for (byte b : bytes){
            System.out.print(b);
        }
        System.out.println("\n");

        Cipher rc4Decrypt = Cipher.getInstance("RC4");
        rc4Decrypt.init(Cipher.DECRYPT_MODE, rc4Key);
        byte [] decryptedBytes = rc4Decrypt.doFinal(bytes);
        System.out.println(new String(decryptedBytes));
    }
}
