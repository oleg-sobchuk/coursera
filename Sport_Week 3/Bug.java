package sport_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Bug {
    private int[][] array;
    private int[][] scores;
    private boolean[][] way;
    private int width;
    private int height;

    public Bug(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            if (!lines.isEmpty()) {
                //read width and height
                String[] firstLine = lines.get(0).split(" ");
                height = Integer.parseInt(firstLine[0]);
                width = Integer.parseInt(firstLine[1]);
                scores = new int[height][width];
                way = new boolean[height][width];
                lines.remove(0);

                //read scores at board
                array = new int[height][width];
                for (int i = 0; i < height; i++) {
                    Scanner scanner = new Scanner(lines.get(i));
                    for (int j = 0; j < width; j++) {
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

    public void process() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) {
                    scores[i][j] = array[i][j];
                    continue;
                }

                if (i == 0) {
                    scores[i][j] = scores[i][j - 1] + array[i][j];
                    continue;
                }

                if (j == 0) {
                    scores[i][j] = scores[i - 1][j] + array[i][j];
                    way[i][j] = true;
                    continue;
                }

                if (scores[i][j - 1] < scores[i - 1][j]) {
                    scores[i][j] = scores[i - 1][j] + array[i][j];
                    way[i][j] = true;
                } else {
                    scores[i][j] = scores[i][j - 1] + array[i][j];
                }
            }
        }
        System.out.println(scores[height - 1][width - 1]);
    }

    public void printWay() {
        int i = height - 1;
        int j = width - 1;
        StringBuilder result = new StringBuilder("");
        while (true) {
            if (way[i][j]) {
                result.append("D");
                i--;
            } else {
                result.append("R");
                j--;
            }
            if (i == 0 && j == 0) {
                break;
            }
        }
        System.out.println(result.reverse().toString());
    }

    public static void main(String[] args) {
        Bug bug = new Bug("src\\main\\resources\\Bug.txt");
        bug.process();
        bug.printWay();
    }

}
