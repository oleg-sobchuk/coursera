package sport_3;

import sun.plugin.dom.css.Rect;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Rectangle {
    int height;
    int width;
    int[][] scores;
    int[][] array;
    int reqCount;
    int[][] requests;

    public Rectangle(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            if (!lines.isEmpty()) {
                //read weight and height
                String[] firstLine = lines.get(0).split(" ");
                height = Integer.parseInt(firstLine[0]);
                width = Integer.parseInt(firstLine[1]);

                lines.remove(0);

                //read scores at board
                scores = new int[height][width];
                array = new int[height][width];
                for (int i = 0; i < height; i++) {
                    Scanner scanner = new Scanner(lines.get(i));
                    for (int j = 0; j < width; j++) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Something wrong!");
                            break;
                        }
                        scores[i][j] = scanner.nextInt();
                    }
                }
                reqCount = Integer.parseInt(lines.get(height));
                requests = new int[reqCount][4];
                for (int i = 1; i <= reqCount; i++) {
                    Scanner scanner = new Scanner(lines.get(height + i));
                    for (int j = 0; j < 4; j++) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Something wrong!");
                            break;
                        }
                        requests[i - 1][j] = scanner.nextInt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        int sum;
        for (int i = 0; i < height; i++) {
            sum = 0;
            for (int j = 0; j < width; j++) {
                sum += scores[i][j];
                if (i == 0) {
                    array[i][j] = sum;
                } else {
                    array[i][j] = sum + array[i - 1][j];
                }
            }
            System.out.println(Arrays.toString(array[i]));
        }
    }

    public void process() {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < reqCount; i++) {
            long full = array[requests[i][1] - 1][requests[i][3] - 1];
            long upper = 0;
            ;
            if (requests[i][2] - 2 >= 0) {
                upper = array[requests[i][1] - 1][requests[i][2] - 2];
            }

            long left = 0;
            if (requests[i][0] - 2 >= 0) {
                left = array[requests[i][0] - 2][requests[i][3] - 1];
            }

            long cross = 0;
            if (requests[i][2] - 2 >= 0 && requests[i][0] - 2 >= 0) {
                cross = array[requests[i][0] - 2][requests[i][2] - 2];
            }

            //result += full - upper - left + cross;
            result = result.add(BigInteger.valueOf(full - upper - left + cross));
            //System.out.println(full + "-" + upper + "-" + left + "+" + cross + "=" + (full-upper-left+cross)+ ":" + result.toString());
        }
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle("src\\main\\resources\\Rectangle.txt");
        rectangle.init();
        rectangle.process();
    }
}
