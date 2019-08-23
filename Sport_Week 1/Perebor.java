public class Perebor {
    int m;
    int n;
    int[] result;
    boolean[] used;

    public Perebor(int m, int n) {
        this.m = m+1;
        this.n = n;
        result = new int[m+1];
        used = new boolean[m+1];
    }

    void rec(int idx) {
        if (idx == m) {
            out(result);
            return;
        }
        for(int i = 1; i <= n; i++) {
            if(used[i]) {
                //continue;
            }
            result[idx] = i;
            used[i] = true;
            rec(idx + 1);
            used[i] = false;
        }
    }

    void out(int[] r) {
        for(int i : r) {
            System.out.print(i);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Perebor p = new Perebor(6,4);
        p.rec(0);
    }
}
