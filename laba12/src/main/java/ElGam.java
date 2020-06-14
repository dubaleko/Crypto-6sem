import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.util.Scanner;

public class ElGam {

    public static  String readFile(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        Integer symb;
        String str = "";
        while((symb=fileReader.read())!=-1){
            str = str.concat(symb.toString());
        }
        return  str;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        long start = System.currentTimeMillis();
        Signature signature = Signature.getInstance("SHA256withDSA");

        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        signature.initSign(keyPair.getPrivate(), secureRandom);

        byte[] data = readFile("file.txt").getBytes("UTF-8");
        signature.update(data);
        byte[] digitalSignature = signature.sign();
        long finish = System.currentTimeMillis();
        System.out.println("Time to create E-signature: "+(finish-start)+" ms");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter some text to verify E-signature:");
        if (scanner.hasNext()) {
            start = System.currentTimeMillis();
            Signature signature2 = Signature.getInstance("SHA256withDSA");
            signature2.initVerify(keyPair.getPublic());
            byte[] data2 = readFile("file.txt").getBytes("UTF-8");
            signature2.update(data2);
            boolean verified = signature2.verify(digitalSignature);
            System.out.println(verified);
            finish = System.currentTimeMillis();
            System.out.println("Time to verify E-signature: "+(finish-start)+" ms");
        }
    }
}
