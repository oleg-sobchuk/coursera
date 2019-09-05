package sport_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public class BackPackDinamic {

    int[][] array;
    int weight;
    int count;
    int[][] d;
    int[][] way;

    public BackPackDinamic(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            if (!lines.isEmpty()) {
                //read width and height
                String[] firstLine = lines.get(0).split(" ");
                count = Integer.parseInt(firstLine[0]);
                weight = Integer.parseInt(firstLine[1]);
                d = new int[count + 1][weight + 1];
                way = new int[count + 1][weight + 1];
                lines.remove(0);

                //read scores at board
                array = new int[count][2];
                for (int i = 0; i < count; i++) {
                    Scanner scanner = new Scanner(lines.get(i));
                    for (int j = 0; j < 2; j++) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Something wrong!");
                            break;
                        }
                        array[i][j] = scanner.nextInt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process() {

        for (int i = 0; i <= count; i++) {
            for (int j = 0; j <= weight; j++) {
                if (i == 0) {
                    continue;
                }

                d[i][j] = d[i - 1][j];

                if (j - array[i - 1][0] >= 0 && d[i][j] < d[i - 1][j - array[i - 1][0]] + array[i - 1][1]) {
                    d[i][j] = d[i - 1][j - array[i - 1][0]] + array[i - 1][1];
                }
            }
        }

        for (int[] i : d) {
            System.out.println(Arrays.toString(i));
        }
    }

    void printCert() {
        List<Integer> cert = new ArrayList<>();
        int sum = d[count][weight];
        int hor = count;
        int vert = weight;

        //while (sum >= 0) {
        //find first max vertical d[i][j] with given j
        for (int i = hor; i >= 0; i--) {
            if (sum > d[i][vert]) {
                cert.add(i + 1);
                hor = i;
                vert -= array[i][0];
                sum -= array[i][1];
            }
        }
        //}
        cert.sort(Integer::compareTo);
        System.out.println(cert.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        System.out.println(cert.stream().mapToInt(i -> array[i - 1][1]).sum());
        System.out.println(cert.size());
    }

    public static void main(String[] args) {
        BackPackDinamic backPackDinamic = new BackPackDinamic("src\\main\\resources\\BackPack.txt");
        backPackDinamic.process();
        backPackDinamic.printCert();
    }
}
