import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("NOD for 2 nums [81,45]: " + GetNOD2(1266, 6));
        System.out.println("NOD for 3 nums [81,45,21]: " + GetNOD3(81, 45, 21));
        System.out.println("Count of prime nums [2, 401]: " + PrimeCount(2, 401));
        System.out.println("Count of prime nums [367, 401]: " + PrimeCount(367, 401));
    }
    public static int PrimeCount(int start, int end)
    {
        List<Integer> numbers = new ArrayList<Integer>();
        List<Integer> bufNumbers = new ArrayList<Integer>();

        for(int i = start; i<= end; i++)
        {
            numbers.add(i);
        }

        for(int i = 2; i<=end; i++)
        {
            for(int number : numbers)
            {
                if(number%i != 0 || number == i)
                {
                    bufNumbers.add(number);
                }
            }
            numbers.clear();
            for(int number : bufNumbers)
            {
                numbers.add(number);
            }
            bufNumbers.clear();
        }

        return numbers.size();
    }
    public static int GetNOD2(int val1, int val2)
    {
        val1 = Math.abs(val1);
        val2 = Math.abs(val2);
        if (val2 == 0)
            return val1;
        else
            return GetNOD2(val2, val1 % val2);
    }

    public static int GetNOD3(int val1, int val2, int val3)
    {
        val1 = Math.abs(val1);
        val2 = Math.abs(val2);
        val3 = Math.abs(val3);
        int val4 = GetNOD2(val1, val2);
        return GetNOD2(val3, val4);
    }
}
