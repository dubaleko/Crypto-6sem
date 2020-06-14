import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static ArrayList<Character> columnCrypt(int result, ArrayList<Character> lettersArray){
        for (int j = 0; j < result; j = j+2){
            if (j+2<result) {
                int index = result;
                int columnIndex = j;
                while (index != 0) {
                    Character symb1 = lettersArray.get(columnIndex);
                    Character symb2 = lettersArray.get(columnIndex + 1);
                    lettersArray.set(columnIndex + 1, symb1);
                    lettersArray.set(columnIndex, symb2);
                    columnIndex = columnIndex + result;
                    index--;
                }
            }
        }
        return  lettersArray;
    }

    public static ArrayList<Character> lineCrypt(int result,ArrayList<Character> lettersArray){
        for (int j = 0; j < result; j = j+2){
            if (j+2<result) {
                int index = result;
                int lineIndex = result * j;
                while (index != 0) {
                    Character symb1 = lettersArray.get(lineIndex);
                    Character symb2 = lettersArray.get(lineIndex + result);
                    lettersArray.set(lineIndex + result, symb1);
                    lettersArray.set(lineIndex, symb2);
                    lineIndex = lineIndex + 1;
                    index--;
                }
            }
        }
        return lettersArray;
    }

    public static long Many(FileReader reader, FileWriter writer, FileWriter lettersCount, boolean flag) throws IOException{
        int c;
        int i = 0;
        long start = System.currentTimeMillis();

        ArrayList<Character> lettersArray = new ArrayList<Character>();
        while ((c=reader.read())!=-1){
            char c1 = (char)c;
            lettersArray.add(i,c1);
            i++;
        }

        int length = lettersArray.size();
        int result = (int)Math.ceil(Math.sqrt(length));
        int needToAdd = result * result - length;

        while (needToAdd != 0){
            lettersArray.add(i,' ');
            needToAdd--;
            i++;
        }

        if (true){
            lettersArray = columnCrypt(result,lettersArray);
            lettersArray = lineCrypt(result,lettersArray);

        }
        else {
            lettersArray = lineCrypt(result,lettersArray);
            lettersArray = columnCrypt(result,lettersArray);
        }

        for (Character c1 : lettersArray){
           writer.write(c1);
        }
        writer.flush();
        long finish = System.currentTimeMillis();

        Map<Character, Integer> letters = new HashMap<Character, Integer>();
        for (Character c1 : lettersArray){
            if (!letters.containsKey(c1)){
                letters.put(c1,1);
            }
            else {
                int countLetter = letters.get(c1) + 1;
                letters.put(c1, countLetter);
            }
        }
        letters.forEach((k, v)->{
            try {
                lettersCount.write(k+" "+v+'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lettersCount.flush();

        return  finish - start;
    }

    public static long ZigZag(FileReader reader, FileWriter writer, FileWriter lettersCount) throws IOException {
        int c;
        int i = 0;
        long start = System.currentTimeMillis();

        ArrayList<Character> lettersArray = new ArrayList<Character>();
        while ((c=reader.read())!=-1){
            char c1 = (char)c;
            lettersArray.add(i,c1);
            i++;
        }

        int length = lettersArray.size();
        int result = (int)Math.ceil(Math.sqrt(length));
        int needToAdd = result * result - length;

        while (needToAdd != 0){
            lettersArray.add(i,' ');
            needToAdd--;
            i++;
        }

        for (int j = 0; j < result; j++){
            int index = result;
            int columnIndex = j;
            while (index != 0){
                if(index == result) {
                    writer.write(lettersArray.get(j));
                }
                else {
                    writer.write(lettersArray.get(columnIndex));
                }
                columnIndex = columnIndex + result;
                index--;
            }
        }
        writer.flush();
        long finish = System.currentTimeMillis();

        Map<Character, Integer> letters = new HashMap<Character, Integer>();
        for (Character c1 : lettersArray){
            if (!letters.containsKey(c1)){
                letters.put(c1,1);
            }
            else {
                int countLetter = letters.get(c1) + 1;
                letters.put(c1, countLetter);
            }
        }
        letters.forEach((k, v)->{
            try {
                lettersCount.write(k+" "+v+'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lettersCount.flush();
        return  finish - start;
    }

    public static void main(String[] args) throws IOException {

        FileReader reader = new FileReader("file.txt");
        FileWriter writer = new FileWriter("zigzag.txt");
        FileWriter letters = new FileWriter("letters.txt");
        long zigZagEncryptionTime = ZigZag(reader, writer, letters);

        FileReader decryptionReader = new FileReader("zigzag.txt");
        FileWriter decryptionWriter = new FileWriter("file.txt");
        long zigZagDecryptionTime = ZigZag(decryptionReader,decryptionWriter,letters);

        FileReader manyReader = new FileReader("file.txt");
        FileWriter manyWriter = new FileWriter("many.txt");
        long manyEncryptionTime = Many(manyReader,manyWriter,letters,true);

        FileReader manyDecryptionReader = new FileReader("many.txt");
        FileWriter manyDecryptionWriter = new FileWriter("file.txt");
        long manyDecryptionTime = Many(manyDecryptionReader,manyDecryptionWriter,letters,false);

        System.out.println("Zig-zag Encryption time: "+ zigZagEncryptionTime);
        System.out.println("Zig-zag Decryption time: "+ zigZagDecryptionTime);
        System.out.println("Many Encryption time: "+ manyEncryptionTime);
        System.out.println("Many Decryption Time: "+manyDecryptionTime);

    }
}
