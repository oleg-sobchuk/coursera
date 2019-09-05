package sport_3;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Sequence {
    private int countFirst;
    private int countSecond;
    private int[] arrFirst;
    private int[] arrSecond;
    private int[][] results;
    private int[][] cert;

    public Sequence(String path) {
        try {
            Scanner sc = new Scanner(Paths.get(path));

            countFirst = sc.nextInt();
            arrFirst = new int[countFirst];
            for (int i = 0; i < countFirst; i++) {
                arrFirst[i] = sc.nextInt();
            }

            countSecond = sc.nextInt();
            arrSecond = new int[countSecond];
            for (int i = 0; i < countSecond; i++) {
                arrSecond[i] = sc.nextInt();
            }

            results = new int[countFirst + 1][countSecond + 1];
            cert = new int[countFirst + 1][countSecond + 1];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process() {
        for (int i = 1; i <= countFirst; i++) {
            for (int j = 1; j <= countSecond; j++) {

                if (results[i - 1][j] < results[i][j - 1]) {
                    cert[i][j] = 1;
                }

                results[i][j] = Math.max(results[i - 1][j], results[i][j - 1]);


                if (arrFirst[i - 1] == arrSecond[j - 1]) {
                    if (results[i][j] < results[i - 1][j - 1] + 1) {
                        cert[i][j] = 2;
                    }

                    results[i][j] = Math.max(results[i][j], results[i - 1][j - 1] + 1);

                }
            }
        }
        for (int[] i : results) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println(results[countFirst][countSecond]);

        for (int[] i : cert) {
            System.out.println(Arrays.toString(i));
        }
        //System.out.println(results[countFirst][countSecond]);
    }


    void printCert() {
        List<Integer> certificate = new ArrayList<>();
        int size = results[countFirst][countSecond];
        int i = countFirst;
        int j = countSecond;
        while (size != 0) {
            while (cert[i][j] == 0) {
                i--;
            }
            while (cert[i][j] == 1) {
                j--;
            }
            certificate.add(i);
            size--;
            i--;
        }
        System.out.println(certificate.stream().sorted().map(String::valueOf).collect(Collectors.joining(" ")));
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence("src\\main\\resources\\Sequences.txt");
        sequence.process();
        sequence.printCert();
    }
}
