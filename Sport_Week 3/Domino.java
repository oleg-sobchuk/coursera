package sport_3;

import java.math.BigInteger;

public class Domino {

    public static void main(String[] args) {

        BigInteger[] d = new BigInteger[2];

        d[0] = BigInteger.ZERO;
        d[1] = BigInteger.ONE;
        for (int i = 0; i < 100000; i++) {
            BigInteger temp = d[0].add(d[1]);
            d[0] = d[1];
            d[1] = temp;
        }
        d[0] = d[1].mod(BigInteger.valueOf(1000000000));
        System.out.println(d[0]);

    }
}
