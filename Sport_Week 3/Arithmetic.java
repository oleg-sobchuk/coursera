package sport_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Arithmetic {
    private int count;
    private int result;
    private int sum;
    private boolean isMinus;
    private int[] arr;
    int[][] sumArr;


    public Arithmetic(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            if (!lines.isEmpty()) {
                //read weight and height
                String[] firstLine = lines.get(0).split(" ");
                count = Integer.parseInt(firstLine[0]);
                result = Integer.parseInt(firstLine[1]);

                lines.remove(0);
                arr = new int[count];

                //read scores at board
                Scanner scanner = new Scanner(lines.get(0));
                for (int i = 0; i < count; i++) {
                    arr[i] = scanner.nextInt();

                }

                sum = sum();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int sum() {
        int summ = 0;
        for (int i : arr) {
            summ += i;
        }
        System.out.println(summ);
        //System.out.println(Arrays.stream(arr).sum());
        //System.out.println(91-67-84-50-69-74-78-58-62-64-55-95-81-77-61-91-95-92-77-86-91-54-52-53-92-82-71-66-68-95-97-76-71+88-69-62-67-99-85-94-53-61-72-83-73-64-91-61-53-68);

        return summ;
    }

    void process() {

        if (result <= 0) {
            isMinus = true;
        }

        if ((sum + result) % 2 != 0) {
            System.out.println("Wrong numbers!");
            return;
        }

        int sumToFind = isMinus ? (sum + result) / 2 - arr[0] : (sum - result) / 2;
        System.out.println(sumToFind);

        sumArr = new int[count + 1][sumToFind + 1];
        for (int i = 2; i <= count; i++) {
            for (int j = 1; j <= sumToFind; j++) {
                sumArr[i][j] = sumArr[i - 1][j];
                if (j - arr[i - 1] >= 0) {
                    sumArr[i][j] = Math.max(sumArr[i][j], sumArr[i - 1][j - arr[i - 1]] + arr[i - 1]);
                }

                if (sumArr[i][j] == sumToFind) {
                    System.out.println(i + "-" + j);

                    List<Integer> list = new ArrayList<>();
                    list.add(i - 1);
                    j = j - arr[i - 1];
                    i = i - 1;
                    while (j > 0) {
                        while (sumArr[i][j] == sumArr[i - 1][j]) {
                            i--;
                        }
                        list.add(i - 1);
                        j = j - arr[i - 1];
                        i = i - 1;
                    }
                    list.sort(Integer::compareTo);
                    System.out.println(list);
                    printCert(list, isMinus);

                    return;
                }
            }
        }
        for (int[] i : sumArr) {
            System.out.println(Arrays.toString(i));
        }
    }

    void printCert(List<Integer> list, boolean isMinus) {
        String defaultJoin = "+";
        String listJoin = "-";
        if (isMinus) {
            defaultJoin = "-";
            listJoin = "+";
        }

        StringBuilder resultString = new StringBuilder(String.valueOf(arr[0]));
        for (int i = 1; i < count; i++) {
            if (!list.isEmpty() && list.get(0) == i) {
                resultString.append(listJoin);
                list.remove(0);
            } else {
                resultString.append(defaultJoin);
            }
            resultString.append(arr[i]);
        }
        System.out.println(resultString.toString());
    }

    public static void main(String[] args) {
        Arithmetic arithmetic = new Arithmetic("src\\main\\resources\\Sum.txt");
        arithmetic.process();
    }
}
