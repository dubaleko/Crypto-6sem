import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricKeyExample {
    private SecretKeySpec secretKey;
    private Cipher cipher;

    public SymmetricKeyExample(String secret, int length, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[]key = new byte[length];
        key = fixSecret(secret, length);
        this.secretKey = new SecretKeySpec(key, algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    private byte[]fixSecret(String s, int length) throws UnsupportedEncodingException {
        if (s.length() < length) {
            int missingLength = length - s.length();
            for (int i = 0; i < missingLength; i++) {
                s += " ";
            }
        }
        return s.substring(0, length).getBytes("UTF-8");
    }

    public void encryptFile(File f) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Encrypting file: " + f.getName());
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        this.writeToFile(f);
    }

    public void decryptFile(File f) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Decrypting file: " + f.getName());
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        this.writeToFile(f);
    }

    public void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream in = new FileInputStream(f);
        byte[]input = new byte[(int) f.length()];
        in.read(input);

        FileOutputStream out = new FileOutputStream(f);
        byte[]output = this.cipher.doFinal(input);
        out.write(output);

        out.flush();
        out.close();
        in.close();
    }

    public static void main(String[]args) {
        File file = new File("File.txt");

        SymmetricKeyExample ske;
        try {
            ske = new SymmetricKeyExample("dubalekovalentinviktorovichizslutska", 24, "DESede");

            while (true) {
                Scanner scannerR = new Scanner(System.in);
                System.out.println("________________________________");
                System.out.println("Enter one of the numbers below:");
                System.out.println("1: Encrypt File.txt");
                System.out.println("2: Decrypt File.txt");
                System.out.println("________________________________");

                if (scannerR.hasNextInt()) {
                    if (scannerR.nextInt() == 1){
                        try {
                            ske.encryptFile(file);
                            System.out.println("Files encrypted successfully");
                        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
                            System.err.println("Couldn't encrypt " + file.getName() + ": " + e.getMessage());
                        }
                    }
                    else if (scannerR.nextInt() == 2){
                        try {
                            ske.decryptFile(file);
                            System.out.println("Files decrypted successfully");
                        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
                            System.err.println("Couldn't decrypt " + file.getName() + ": " + e.getMessage());
                        }
                    }
                    else
                        System.out.println("Try again");
                }
            }
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Couldn't create key: " + ex.getMessage());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        }
    }
}