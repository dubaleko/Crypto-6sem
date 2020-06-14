import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();
        String nameBy = "ДубалекоВалянцінВіктаравіч";
        String namePl = "DąbrowskiWalentyWiktorowicz";
        String patternBY = "[^ёа-зй-шы-яЁА-ЗЙ-ШЫІіЎў]";
        String patternPL = "[^a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]";
        String result = "";

        result = "Папулярны аргумент да якая стала віруснай публікацыі пра коронавирус - ды як жа можна па трохё  " +
                "выпадках нейкую статыстыку выводзіць? Нельга рабіць высновы па такім маленькім выборкам! Гэтую гісторыю пра памеры выбарак усе, хто вучыўся " +
                "сацыяльным навуках, ўвабралі з малаком альма маці. І гэта правільна ў тых сітуацыях, з якімі мы звычайна маем справу фш - з выбарачнымі статыстыкамі.";
        String resultBy = result.replaceAll(patternBY,"");
        double entropyBy = ShannonEntropy(resultBy.toLowerCase());
        System.out.println("Shannon entrophy BY: " + entropyBy);
        char[] chars = result.toCharArray();
        for(char a : chars)
            builder.append(Integer.toBinaryString(a));
        System.out.println("Shannon entrophy BY binary: " + ShannonEntropy(builder.toString()));

        builder = new StringBuilder();
        result = "Popularny argument do publikacji wirusowej o koronawirusie — ale jak można w trzech przypadkach wywnioskować jakieś statystyki? Nie można wyciągać " +
                "wniosków z tak małych próbek! Ta historia o wielkości próbek wszyscy, którzy studiowali Nauki społeczne, wchłonęli mleko matki Alma. I jest to słuszne w " +
                "sytuacjach, z którymi zwykle mamy do fńź czynienia-z przykładowymi statystykami.";
        String resultPl = result.replaceAll(patternPL,"");
        double entropyPl = ShannonEntropy(resultPl.toLowerCase());
        System.out.println("Shannon entrophy PL: "+ entropyPl);
        chars = result.toCharArray();
        for (char a : chars)
            builder.append(Integer.toBinaryString(a));
        System.out.println("Shannon entrophy PL binary: " + ShannonEntropy(builder.toString()));

        System.out.println("Amount of information in BY: " + nameBy.length() * entropyBy);
        System.out.println("Amount of information in PL: " + namePl.length()* entropyPl);

        System.out.println("ASCII amount of information in BY: " + AmountAscii(resultBy) * entropyBy);
        System.out.println("ASCII amount of information in PL: " + AmountAscii(resultPl) * entropyPl);

        System.out.println("Err 0.1 BY: " + AmountOfInformationWithMistake(nameBy,entropyBy, 0.1));
        System.out.println("Err 0.5 BY: " + AmountOfInformationWithMistake(nameBy, entropyBy, 0.5));
        System.out.println("Err 1 BY: " + AmountOfInformationWithMistake(nameBy, entropyBy, 1));

        System.out.println("Err 0.1 PL: " + AmountOfInformationWithMistake(namePl, entropyPl, 0.1));
        System.out.println("Err 0.5 PL: " + AmountOfInformationWithMistake(namePl, entropyPl, 0.5));
        System.out.println("Err 1 PL: " + AmountOfInformationWithMistake(namePl, entropyPl, 1));
    }

    public static int AmountAscii(String result) throws UnsupportedEncodingException {
        byte[] bytes = result.getBytes("ASCII");
        String ASCII = "";
        for (byte bt : bytes){
            ASCII += bt;
        }
        return ASCII.length();
    }

    public static double AmountOfInformationWithMistake(String message, double entropy, double p)
    {
        double result = (entropy - (1 + p * (Math.log(p)/Math.log(2)) + (1-p) * (Math.log(1-p)/Math.log(2)))) * message.length();
        return result;
    }

    public static double ShannonEntropy(String s) throws IOException {
        Map<Character,Integer> map = new HashMap<Character, Integer>();
        char[] chars = s.toCharArray();
        for (char c : chars)
        {
            if (!map.containsKey(c))
                map.put(c, 1);
            else
                map.put(c,map.get(c)+1);
        }

        double result = 0.0;
        int len = s.length();

        FileWriter writer = new FileWriter( System.getProperty("user.dir")+"\\info.txt", true);
        map.forEach((k,v)->{
            try {
                writer.write( k +" = "+ v+'\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("_____________________________________\n");
        writer.flush();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            double i = (double)entry.getValue() / len;
            result -= i * (Math.log(i)/Math.log(2));
        }
        return result;
    }
}
