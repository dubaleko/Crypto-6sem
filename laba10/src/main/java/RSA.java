import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSA {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException{
        String string = "Dubaleko Valentin";

        Cipher cipher = Cipher.getInstance("RSA");
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = pairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);

        long start = System.currentTimeMillis();
        byte[] bytes = cipher.doFinal(string.getBytes());
        for (byte b : bytes){
            System.out.print(b);
        }
        long end = System.currentTimeMillis();
        System.out.println("\nEncrypt time: "+(end-start)+" ms");

        start = System.currentTimeMillis();
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = decryptCipher.doFinal(bytes);
        end = System.currentTimeMillis();
        System.out.println(new String(decryptedBytes));
        System.out.println("Decrypt time: "+(end-start)+" ms");
    }
}