import java.util.Arrays;

public class Cells {
    int fishki;
    int count;
    int[] res;
    int printCount = 0;

    public Cells(int fishki, int count) {
        this.fishki = fishki;
        this.count = count;
        res = new int[fishki];
    }

    void rec(int start, int remain) {
        for(int i = start; i < count; i++) {
            if (remain == fishki) {
                res[0] = i;
                rec(i+2,remain-1);
                continue;
            }
            if (remain == 1) {
                res[fishki-1] = i;
                out();
            }else {
                res[fishki-remain] = i;
                //out();
                rec(i+2,remain-1);
            }
        }
    }

    void out(){
        char[] result = new char[count];
        for(int i = 0; i < count; i++) {
            result[i] = '.';
        }
        for(int i : res) {
            result[i] = '*';
        }
        printCount++;
        System.out.println(printCount + " : " + String.valueOf(result));

    }

    public static void main(String[] args) {
        Cells cells = new Cells(8,25);
        cells.rec(0,8);
    }
}
