import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSA {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException{
        String string = "hello world";

        Cipher cipher = Cipher.getInstance("RSA");
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = pairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        cipher.init(Cipher.ENCRYPT_MODE,publicKey);

        byte[] bytes = cipher.doFinal(string.getBytes());
        for (byte b : bytes){
            System.out.print(b);
        }
        System.out.println("\n");

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = decryptCipher.doFinal(bytes);
        System.out.println(new String(decryptedBytes));
    }
}
