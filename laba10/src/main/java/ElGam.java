import java.io.*;
import java.util.Scanner;

public class ElGam
{
    public static void main(String[] args) throws IOException
    {
        System.out.print("Enter text: ");
        Scanner scanner = new Scanner(System.in);
        long start = System.currentTimeMillis();
        String cryptText = Crypt(593,123,8, scanner.next());
        long end = System.currentTimeMillis();
        System.out.println("Encrypted text: "+ cryptText);
        System.out.println("Encrypt time: "+(end-start)+" ms");
        start = System.currentTimeMillis();
        System.out.println("Decrypted text: "+Decrypt(593,8,cryptText));
        end = System.currentTimeMillis();
        System.out.println("Decrypted time: "+(end-start)+" ms");
    }

    public static String Crypt(int p, int g, int x, String inString){
        String result = "";
        int y = Power(g,x,p);
        System.out.println("Open key (g,p,y)=("+p+","+g+","+y+")");
        System.out.println("Closed key: "+ x);
        byte[] bytes = inString.getBytes();
        for (int code : bytes){
            if (code > 0){
                int k = (int)Math.random()*(p-1);
                int a = Power(g,k,p);
                int b = Mul(Power(y,k,p),code,p);
                result += a+" "+b+" ";
            }
        }
        return  result;
    }

    public static String Decrypt(int p, int x, String inText){
        String result = "";
        String[] arr = inText.split(" ");
        for (int i = 0; i < arr.length; i += 2){
            int a = Integer.parseInt(arr[i]);
            int b = Integer.parseInt(arr[i+1]);

            if (a != 0 && b !=0){
                char symb = (char)Mul(b,Power(a,p-1-x,p),p);
                result += symb;
            }
        }
        return result;
    }

    private static  int Power(int a, int b, int n){ // a^b mod n
        int tmp = a;
        int sum = tmp;
        for (int i = 1; i < b; i++) {
            for (int j = 1; j < a; j++) {
                sum += tmp;
                if (sum >= n) {
                    sum -= n;
                }
            }
            tmp = sum;
        }
        return tmp;
    }

    private static int Mul(int a, int b, int n) { // a*b mod n
        int sum = 0;
        for (int i = 0; i < b; i++) {
            sum += a;
            if (sum >= n)
                sum -= n;
        }
        return sum;
    }
}
