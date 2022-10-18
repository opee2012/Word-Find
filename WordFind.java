import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WordFind {

    static int rows = 0;/*rowNum = 0;*/
    static int cols = 0;/*colNum = 0;*/
    static char[][] puzzleArray;
    //static List<String> wordsList;
    //static int letterNum, comparisons;
    //static String word, direction;

    private static String ReadFile(String[] _args, int i) {
        try (Scanner scanner = new Scanner(_args[i])) {
            String s = scanner.nextLine();
            scanner.reset();
            System.out.println(s);
            return s;
        } catch (Exception e) {
            System.out.println("That file does not exist. Restart program to try again.");
            return null;
        }
    }

    private static void ConvertPuzzleFile(String fileName) throws IOException {
        String puzzleFile = Files.readString(Path.of(fileName));
        Scanner inputData = new Scanner(puzzleFile);
        List<String> rowLists = new ArrayList<>();
        String lineTest;
        while (inputData.hasNext()) {
            lineTest = inputData.nextLine();
            if (!lineTest.startsWith("-")) {
                rows++;
                lineTest = lineTest.replace("|", "");
                rowLists.add(lineTest);
                cols = lineTest.length();
            }
        }
        inputData.reset();

        String rowData;
        puzzleArray = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            rowData = rowLists.get(i);
            for (int j = 0; j < cols; j++) {
                puzzleArray[i][j] = rowData.charAt(j);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ConvertPuzzleFile(ReadFile(args, 0));
        System.out.println(Arrays.deepToString(puzzleArray));
    }
}
