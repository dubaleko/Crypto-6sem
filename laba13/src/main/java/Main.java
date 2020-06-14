import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Task 1:");
        List<String> data  = new ArrayList<String>(){};
        for (int i = 71; i < 106; i++){
            double numb = Math.pow(i,3) - i + 1 % 751;
            double y = Math.pow(numb,0.5);
            data.add(i+": [-"+y+","+y+"]");
        }
        for (String str : data)
            System.out.println(str);

        System.out.println("Task 2:");
        double[] aPart = new double[]{0,0};
        double[] oneP = new double[]{1,2};
        double[] twoP = sumSimilar(oneP,-1);
        double[] fourP = sumSimilar(twoP,-1);
        double[] timeA = sumVarious(fourP,twoP);
        aPart = sumVarious(timeA, oneP);
        System.out.println("kP = ["+aPart[0]+","+aPart[1]+"]");

        double[] bPart = sumVarious(new double[]{1,2}, new double[]{3,4});
        System.out.println("P+Q = ["+bPart[0]+","+bPart[1]+"]");

        double[] cPart = new double[]{0,0};
        double[] oneQ =  new double[]{3,4};
        double[] twoQ = sumSimilar(oneQ,-1);
        double[] fourQ = sumSimilar(twoQ,-1);
        cPart = sumSimilar(fourQ,-1);

        if (cPart[0] != aPart[0] && aPart[1] != cPart[1])
            cPart = sumVarious(aPart,cPart);
        else
            cPart = sumSimilar(cPart, -1);
        double[] reverseR = bPart;
        reverseR[1] = -bPart[1];
        if (cPart[0] != reverseR[0] && reverseR[1] != cPart[1])
            cPart = sumVarious(cPart,reverseR);
        else
            cPart = sumSimilar(cPart, -1);
        System.out.println("kP+lQ-R = ["+cPart[0]+","+cPart[1]+"]");

        double[] dPart = sumVarious(new double[]{1,2}, new double[]{3,-4});
        if (dPart[0] != bPart[0] && bPart[1] != bPart[1])
            dPart = sumVarious(dPart,bPart);
        else
            dPart = sumSimilar(dPart, -1);
        System.out.println("P-Q+R = ["+dPart[0]+","+dPart[1]+"]");
    }

    public static double[] sumSimilar(double[]p, int a){
        double[] arr = new double[2];
        double h = (Math.pow(p[0],2) * 3 + a)/(2*p[1]);
        arr[0] = Math.pow(h,2) - p[0] - p[0];
        arr[1] = h *(p[0]-arr[0]) -p[1];
        return arr;
    }

    public static double[] sumVarious (double[] p, double[] q){
        double[] arr = new double[2];
        double h = (q[1] - p[1])/(q[0]-p[0]);
        arr[0] = Math.pow(h,2) - p[0] - q[0];
        arr[1] = h *(p[0]-arr[0]) -p[1];
        return arr;
    }
}
