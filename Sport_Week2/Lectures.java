import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Lectures {
    int count;
    int[][] arr;
    boolean[] used;

    public Lectures(String path) throws IOException {
        //this.count = count;
        String context = readFile(path, Charset.defaultCharset());
        int idx = context.indexOf('\r');
        count = Integer.parseInt(context.substring(0, idx));
        context = context.substring(idx + 2);
        arr = stringToDoubleInts(context);
        used = new boolean[count];
    }

    void process() {
        int result = 0;
        int controll = 0;
        boolean chang = true;
        while (chang) {
            int lastEnd = 0;
            chang = false;
            result++;
            int cnt = 0;
            if (result == 1347) {
                System.out.println();
            }
            for (int i = 0; i < count; i++) {
                if (!used[i]) {
                    if (arr[i][0] >= lastEnd) {
                        int idx = i;//findMin(i, lastEnd);
                        lastEnd = arr[idx][1];
                        chang = true;
                        cnt++;
                        used[idx] = true;
                    }
                }
            }
            System.out.println(cnt);
            controll+=cnt;
        }
        System.out.println(controll);
        System.out.println("Result: "+(result-1));
    }

    int findMin(int i, int lastEnd) {
        int minIdx = i;
        int nextIdx = i+1;
        while (nextIdx < count && arr[nextIdx][1] == arr[i][1]) {
            if (!used[nextIdx] && arr[nextIdx][0] >= lastEnd && arr[nextIdx][0] < arr[minIdx][0]) {
                minIdx = nextIdx;
            }
            nextIdx++;
        }
        return minIdx;
    }

    void sort() {
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);
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
            Lectures lectures = new Lectures("D:\\Progects\\coursera\\Algorithm percolation\\lecture.txt");
            lectures.sort();
            lectures.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}