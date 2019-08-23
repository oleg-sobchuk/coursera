import java.util.Arrays;

public class Slogaemue {
    int count = 0;
    int n;
    int[] res;

    public Slogaemue(int n) {
        this.n = n;
        res = new int[n];
    }

    void rec(int idx, int min, int sum) {

        for(int i = min; i <= sum; i++) {
            res[idx] = i;
            if (Arrays.stream(res).sum() == n) {
                count++;
                if (count == 13672) {
                    System.out.println(Arrays.toString(res));
                }
                res[idx]=0;
                return;
            }else{
                if (Arrays.stream(res).sum() > n) {
                    return;
                }
            }
            rec(idx+1, i, sum-i);
        }
    }

    public static void main(String[] args) {
        Slogaemue slogaemue = new Slogaemue(35);
        slogaemue.rec(0,1,35);
    }
}
