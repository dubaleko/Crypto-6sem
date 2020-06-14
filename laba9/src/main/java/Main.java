import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> privateKey = generate();
        int n = 1;
        for (Integer val : privateKey){
            n = n + val;
        }
        int getValue = 0; int a = 2;
        while (getValue != 1){
            getValue = GetNod(n, a);
            if (getValue != 1)
                a++;
        }
        ArrayList<Integer> openKey = new ArrayList<Integer>();
        for (Integer val : privateKey){
            openKey.add(val*(a%n));
        }
        long start = System.currentTimeMillis();
        ArrayList<String> binary = getByteName("Dubaleko Valentin");
        ArrayList<Integer> cryptedText = new ArrayList<>();
        for (String str : binary){
            char[] arr = str.toCharArray();
            int value = 0;
            for (int i =0; i < arr.length; i++ ){
                if (arr[i] == 49){
                    value += openKey.get(i);
                }
            }
            cryptedText.add(value);
        }
        long finish = System.currentTimeMillis();
        System.out.print("Encrypt text: ");
        for (Integer value : cryptedText ){
            System.out.print(value+" ");
        }
        System.out.println("\nEncription time: "+(finish-start)+" ms");
        getValue = 0; int a1 = 1;
        while (getValue != 1){
            getValue = a *a1%n;
            if (getValue != 1)
                a1++;
        }
        long startDecrypt = System.currentTimeMillis();
        ArrayList<String> binaries = new ArrayList<String>();
        for (Integer value: cryptedText){
            int myVal = value * a1 % n;
            int[] arr = new int[8];
            for (int i = 7; i > 0; i--){
                if (privateKey.get(i) <= myVal){
                    myVal -= privateKey.get(i);
                    arr[i] = 1;
                }
                else arr[i] = 0;
            }
            String str = "";
            for (int i =0; i < arr.length; i++)
                str = str + arr[i];
            binaries.add(str);
        }
        long endDecrypt = System.currentTimeMillis();
        System.out.println("Decrypt text:" );
        for (String str : binaries){
            char b = (char)(int)Integer.valueOf(str, 2);
            System.out.print(b);
        }
        System.out.println("\nDecryption time: "+(endDecrypt-startDecrypt)+" ms");
    }

    public static  int GetNod(int val1, int  val2){
        if (val2 == 0)
            return val1;
        else
            return GetNod(val2, val1 % val2);
    }

    public static ArrayList<String> getByteName(String username){
        char[] arr = username.toCharArray();
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (int i=0; i< arr.length; i++){
            bytes.add((byte)arr[i]);
        }
        ArrayList<String> binary = new ArrayList<String>();
        for (byte bt : bytes){
            binary.add(String.format("%8s", Integer.toBinaryString(bt & 0xFF)).replace(' ', '0'));
        }
        return  binary;
    }

    public static ArrayList<Integer> generate(){
        ArrayList<Integer> key = new ArrayList<Integer>();
        Random random = new Random();
        while (key.size() < 8){
            int minVal = 1;
            for (Integer val : key){
                minVal = minVal + val;
            }
            double value = Math.random()*10 + minVal;
            key.add((int) value);
        }
        return  key;
    }
}
