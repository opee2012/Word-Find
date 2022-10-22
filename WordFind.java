import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordFind {

    static int rows = 0, rowNum = 0;
    static int cols = 0, colNum = 0;
    static char[][] puzzleArray;
    static List<String> wordsList;
    static int letterNum, comparisons;
    static String direction;

    private static String ReadFile(String[] _args, int i) {
        String file;
        try (Scanner scanner = new Scanner(_args[i])) {
            file = scanner.nextLine();
            scanner.reset();
            if (!file.endsWith(".txt")) {
                System.out.println("Error! Incorrect argument(s).");
                System.out.println("Restart program with correct argument(s) and try again.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("That file does not exist. Restart program and try again.");
            return null;
        }
        return file;
    }

    private static void ConvertPuzzleFile(String fileName) throws IOException {
        String puzzleFile = Files.readString(Path.of(fileName));
        Scanner inputData = new Scanner(puzzleFile);
        List<String> rowLists = new ArrayList<>();
        String lineTest;
        while (inputData.hasNext()) {
            lineTest = inputData.nextLine();
            if (!lineTest.startsWith("-")) {
                if (!lineTest.startsWith("|")) {
                    System.out.println("Error! Incorrect file, please input a word-find grid file.");
                    System.out.println("Restart program with correct argument(s) and try again.");
                    System.exit(0);
                }
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

    private static void ConvertWordsFile(String fileName) throws IOException {
        String wordsFile = Files.readString(Path.of(fileName));
        Scanner inputData = new Scanner(wordsFile);
        wordsList = new ArrayList<>();
        while (inputData.hasNext()) {
            wordsList.add(inputData.nextLine().toUpperCase());
        }
        inputData.reset();
    }

    private static boolean WordDirection(String inputWord, int row, int col, char letter, String compass) {
        if (row < 0 || row > puzzleArray.length || col < 0 || col > puzzleArray[0].length) {
            throw new IndexOutOfBoundsException();
        }
        letterNum++;

        if (row - 1 >= 0 && puzzleArray[row - 1][col] == letter && compass.equals("N")) {
            direction = "North";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row - 1, col, inputWord.charAt(letterNum), "N");
        } else if (row - 1 >= 0 && col + 1 < puzzleArray[row].length && puzzleArray[row - 1][col + 1] == letter && compass.equals("NE")) {
            direction = "Northeast";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row - 1, col + 1, inputWord.charAt(letterNum), "NE");
        } else if (col + 1 < puzzleArray[row].length && puzzleArray[row][col + 1] == letter && compass.equals("E")) {
            direction = "East";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row, col + 1, inputWord.charAt(letterNum), "E");
        } else if (row + 1 < puzzleArray.length && col + 1 < puzzleArray[row].length && puzzleArray[row + 1][col + 1] == letter && compass.equals("SE")) {
            direction = "Southeast";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row + 1, col + 1, inputWord.charAt(letterNum), "SE");
        } else if (row + 1 < puzzleArray.length && puzzleArray[row + 1][col] == letter && compass.equals("S")) {
            direction = "South";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row + 1, col, inputWord.charAt(letterNum), "S");
        } else if (row + 1 < puzzleArray.length && col - 1 >= 0 && puzzleArray[row + 1][col - 1] == letter && compass.equals("SW")) {
            direction = "Southwest";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row + 1, col - 1, inputWord.charAt(letterNum), "SW");
        } else if (col - 1 >= 0 && puzzleArray[row][col - 1] == letter && compass.equals("W")) { //search W
            direction = "West";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row, col - 1, inputWord.charAt(letterNum), "W");
        } else if (col - 1 >= 0 && row - 1 >= 0 && puzzleArray[row - 1][col - 1] == letter && compass.equals("NW")) {
            direction = "Northwest";
            comparisons++;
            if (letterNum == inputWord.length()) return true;
            return WordDirection(inputWord, row - 1, col - 1, inputWord.charAt(letterNum), "NW");
        }
        return false;
    }

    private static boolean ArraySearch(String inputWord) {
        String spacedWord = inputWord;
        inputWord.replace(" ", "");
        letterNum = 0;
        char letter = inputWord.charAt(letterNum);
        for (int i = 0; i < rows; i++) {
            rowNum = i + 1;
            for (int j = 0; j < cols; j++) {
                colNum = j + 1;
                comparisons++;
                if (puzzleArray[i][j] == letter) {
                    if (inputWord.length() == 1) {
                        direction = "without a direction";
                        return true;
                    }
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "N")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "NE")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "E")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "SE")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "S")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "SW")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "W")) return true;
                    letterNum = 0;
                    if (WordDirection(inputWord, i, j, inputWord.charAt(++letterNum), "NW")) return true;
                    letterNum = 0;
                }
            }
        }
        System.out.println(spacedWord + " was not found - (Comparisons: " + comparisons + ")");
        return false;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Error, no argument(s) given.");
            System.out.println("Restart program with correct argument(s) and try again.");
            System.exit(0);
        }
        ConvertPuzzleFile(ReadFile(args, 0));
        if (args.length == 1) {
            System.out.print("Enter a word to be found: ");
            do {
                Scanner input = new Scanner(System.in);
                String word = input.nextLine().toUpperCase();
                comparisons = 0;
                if (word.length() == 0) {
                    input.close();
                    System.exit(0);
                } else if (ArraySearch(word)) {
                    System.out.print(word + " was found starting at " + rowNum + ", " + colNum + " and oriented " + direction);
                    System.out.print(" - (Comparisons: " + comparisons + ")\n");
                }
                System.out.print("Enter another word to be found or press enter to exit: ");
                input.reset();
            } while (true);
        } else ConvertWordsFile(ReadFile(args, 1));

        for (String s : wordsList) {
            comparisons = 0;
            if (ArraySearch(s)) {
                System.out.print(s + " was found starting at " + rowNum + ", " + colNum + " and oriented " + direction);
                System.out.print(" - (Comparisons: " + comparisons + ")\n");
            }
        }
    }
}
