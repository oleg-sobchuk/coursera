import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Olimpic {
    int count;
    int time;
    int[] arr;
    boolean[] used;

    public Olimpic(String path) throws IOException {
        //this.count = count;
        String context = readFile(path, Charset.defaultCharset());
        int idx = context.indexOf('\r');

        count = Integer.parseInt(context.substring(0, idx).split(" ")[0]);
        time = Integer.parseInt(context.substring(0, idx).split(" ")[1]);
        context = context.substring(idx + 2);
        arr = stringToInts(context);
        used = new boolean[count];
    }

    void process() {
        int sum = 0;
        long pent = 0;
        int res = 0;
        while(sum < time && res < count) {
            sum += arr[res];
            pent += sum;
            res++;
            System.out.println("idx - " + res + "," + sum + "," + pent);
        }

    }

    void sort() {
        Arrays.sort(arr);
    }

    static int[] stringToInts(String line) {
        String[] strArr = line.split(" ");
        int[] res = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            res[i] = Integer.parseInt(strArr[i]);
        }
        return res;
    }

    static int[][] stringToDoubleInts(String str) {
        String[] arrs = str.split("\r\n");
        int[][] res = new int[arrs.length][2];
        for (int i = 0; i < arrs.length; i++) {
            res[i] = stringToInts(arrs[i]);
        }
        return res;
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) {
        try {
            Olimpic olimpic = new Olimpic("D:\\Progects\\coursera\\Algorithm percolation\\olimp.txt");
            olimpic.sort();
            olimpic.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
