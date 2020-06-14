import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static List<Character> alphabet = new ArrayList<Character>(){{
        add('a'); add('b'); add('c'); add('d'); add('e'); add('f'); add('g'); add('h'); add('i');
        add('j'); add('k'); add('l'); add('m'); add('n'); add('o'); add('p'); add('q'); add('r');
        add('s'); add('t'); add('u'); add('v'); add('w'); add('x'); add('y'); add('z');
    }};

    public static void Cesar(String key) throws IOException {
        char[] letters = key.toCharArray();
        List<Character> previewAlphabet = new ArrayList<Character>();
        List<Character> testAlphabet = new ArrayList<Character>(alphabet);
        for(char letter : letters){
            int i = 0;
            previewAlphabet.add(letter);
            for (Iterator<Character> iterator = testAlphabet.iterator(); iterator.hasNext(); ) {
                Character value = iterator.next();
                if (value == letter) {
                    iterator.remove();
                }
            }
        }
        testAlphabet.addAll(0 , previewAlphabet);

        FileReader reader = new FileReader(System.getProperty("user.dir")+"\\text.txt");
        FileWriter writer = new FileWriter("cesar-coded.txt", false);
        FileReader decryptReader = new FileReader("cesar-coded.txt");
        FileWriter decryptWriter = new FileWriter("cesar-decode.txt",false);
        FileWriter countLetterWriter = new FileWriter("letters.txt",false);
        FileWriter countLetterWriterCrypto = new FileWriter("coded-letters.txt",false);

        long caesarEncryptionTime = CryptEncrypt(reader, writer, countLetterWriter, alphabet, testAlphabet,1);
        System.out.println("Caesar Encryption Runtime: " + caesarEncryptionTime +"ms");
        long caesarDescryptionTime = CryptEncrypt(decryptReader,decryptWriter,countLetterWriterCrypto, testAlphabet,alphabet,1);
        System.out.println("Caesar Decryption Runtime: "+ caesarDescryptionTime +"ms");
    }

    public static long CryptEncrypt(FileReader reader , FileWriter writer, FileWriter crypto, List<Character> alphabet, List<Character> testAlphabet, int flag) throws IOException {
        int c;
        int count = 0;
        Map<Character, Integer> letters = new HashMap<Character, Integer>();
        long start = System.currentTimeMillis();
        int index = 0;
        while((c=reader.read())!=-1){
            if (Character.isLetter((char)c)){
                char c1 = (char)c;
               if (flag == 1) {
                   if (!letters.containsKey(c1)) {
                       letters.put(c1, 1);
                   } else {
                       int countLetter = letters.get(c1) + 1;
                       letters.put(c1, countLetter);
                   }
                   writer.write(testAlphabet.get(alphabet.indexOf(c1)));
               }
               else
               {
                   if (count > 25){
                       count = count%26;
                   }
                   if (flag == 2){
                       index = alphabet.indexOf(c1) + count;
                   }
                   else if (flag == 3){
                       index = alphabet.indexOf(c1) - count;
                   }
                   if (index > 25){
                       index = index%26;
                   }
                   if (index < 0){
                       index = 26 + index;
                   }
                   Character character = alphabet.get(index);
                   count++;

                   if (!letters.containsKey(c1)){
                       letters.put(c1,1);
                   }
                   else {
                       int countLetter = letters.get(c1) + 1;
                       letters.put(c1, countLetter);
                   }
                   writer.write(character);
               }
            }
            else {
                writer.write((char) c);
            }
        }
        writer.flush();

        letters.forEach((k, v)->{
            try {
                crypto.write(k+" "+v+'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        crypto.flush();

        long finish = System.currentTimeMillis();
        return   finish - start;
    }


    public static void main(String[] args) throws IOException {
        Cesar("dubaleko");

        FileReader reader = new FileReader("text.txt");
        FileWriter writer = new FileWriter("trisemys-coded.txt", false);
        FileWriter crypto = new FileWriter("letters.txt", false);

        FileReader reader1 = new FileReader("trisemys-coded.txt");
        FileWriter writer1 = new FileWriter("trisemys-encoded.txt", false);
        FileWriter crypto1 = new FileWriter("trisemys-letters.txt",false);

        System.out.println("Trisemys-Encryption Runtime: "+CryptEncrypt(reader,writer,crypto,alphabet, new ArrayList<>(), 2)+"ms");
        System.out.println("Trisemys-Encryption Runtime: "+CryptEncrypt(reader1,writer1,crypto1,alphabet, new ArrayList<>(), 3)+"ms");
    }
}
