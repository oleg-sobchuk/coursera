package sport_3;

import java.util.Arrays;

public class Binom {
    static long C(int n, int k) {
        double res = 1;
        for (int i = 1; i <= k; ++i)
            res = res * (n - k + i) / i;
        return (long) (res + 0.01);
    }

    static long dinamicBinom(int n, int k) {
        if (k > n) {
            return 0;
        }

        if (k == n || k == 1) {
            return 1;
        }

        long[][] arr = new long[n + 1][n + 1];
        arr[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == i || j == 0) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = arr[i - 1][j] + arr[i - 1][j - 1];
                }
            }
        }

        Arrays.stream(arr).map(Arrays::toString).forEach(System.out::println);
        return arr[n][k];

    }

    public static void main(String[] args) {
        System.out.println(C(50, 20));

        System.out.println(Long.MAX_VALUE);

        System.out.println(dinamicBinom(50, 20));

        long[][] array = new long[51][51];
        for (int i = 0; i <= 50; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == i || j == 0) {
                    array[i][j] = 1;
                } else {
                    array[i][j] = C(i, j);
                }
            }
        }

        System.out.println("----------");

        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);
        System.out.println(array[50][20]);

    }
}
