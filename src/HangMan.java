import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;

public class HangMan {
    String mysteryWord;
    ArrayList<String> wordList;
    ArrayList<Character> hiddenWord;
    int lifes = 6;
    HashSet usedLetters;
    int lettersLeft;

    public HangMan() throws Exception {
        usedLetters = new HashSet<Character>();

        // read in dictionary file and place the words in a string array

        File file = readInFile();

        wordList = createWordList(file);

        // Choose mysteryWord randomly
        mysteryWord = chooseMysterWord(wordList).toUpperCase();

        // create hiddenWord

        hiddenWord = createHiddenWord();

        // initiate letters left as the length of mystery word
        lettersLeft = mysteryWord.length();

        introduceGame();

        // keepPlaying

        keepPlaying();

    }

    private void keepPlaying() {

        while (lifes != 0 && lettersLeft != 0) {

            // display menu

            displayMenu();

            // inputChar from user

            char c = inputChar();

            // initiate Game

            initiateGame(c);

        }
    }

    private ArrayList<Character> createHiddenWord() {
        ArrayList<Character> hiddenWord = new ArrayList<Character>();
        for (int i = 0; i < mysteryWord.length(); i++) {
            hiddenWord.add('-');

        }

        return hiddenWord;
    }

    private char inputChar() {
        char c = ' ';
        try {
            Scanner userInput = new Scanner(System.in);
            c = Character.toUpperCase(userInput.nextLine().charAt(0));
            // userInput.close();
        } catch (Exception e) {
            print("Something went wrong!");
        }

        return c;
    }

    private void initiateGame(Character c) {

        switch (c) {
            case 'S':
                showStatus();
                break;
            case 'G':
                guessLetterProcess();
                break;
            case 'O':
                guessWord();
                break;
            default:
                print("Invalid choice!");
        }

    }

    private void guessWord() {
        Scanner wordScan = new Scanner(System.in);
        String guessedWord = wordScan.nextLine().toUpperCase();

        if (guessedWord.equals(mysteryWord)) {
            print("You live to die another day!");
            lettersLeft = 0;
        } else {
            print("You are hanged!\nThe answer you were so eagerly searching for is: " + mysteryWord);
            lifes = 0;

        }
    }

    private void guessLetterProcess() {

        char c = inputChar();

        ArrayList<Integer> indexArr = checkInputLetter(c);
        // print("test indexArr.size: " + indexArr.size());
        lettersLeft = lettersLeft - indexArr.size();
        print("test indexArr size " + indexArr.size());
        print("test letters left " + lettersLeft);
        print("test2 " + lettersLeft);
        if (indexArr.isEmpty()) {
            updateHangedMan(c);

        } else {

            updateHiddenWord(c, indexArr);
        }

        if (lettersLeft == 0) {
            print("You live to die another day!");
        }
        HashSet<Character> usedLetters = saveInputChar(c);

        print("Already used letters: " + usedLetters);

    }

    private HashSet<Character> saveInputChar(char c) {

        usedLetters.add(c);
        return usedLetters;

    }

    private void updateHangedMan(char c) {

        if (!usedLetters.contains(c)) {

            lifes--;

            drawPicture();

            System.out.println("Antal live som är kvar: " + lifes);

        } else {
            // usedLetters.remove(c);
            System.out.println("Antal live som är kvar: " + lifes);

        }
    }

    private void drawPicture() {

        switch (lifes) {
            case 0:
                twoLegsDraw();
                print("You are hanged!\n The answer you were so eagerly searching for is: " + mysteryWord);
                break;
            case 1:
                oneLegsDraw();
                break;
            case 2:
                twoArmsDraw();
                break;
            case 3:
                oneArmDraw();
                break;
            case 4:
                headDraw();
                break;
            case 5:
                ropeDraw();
                break;

            default:
                print("Ooops, something went wrong. It's not you, it's me!");

        }
    }

    private void twoLegsDraw() {
        String fullBody = "|--------------\n" + "|            |\n" + "|            o\n" + "|           /|\\ \n "
                + "|          / \\";

        print(fullBody);
    }

    private void oneLegsDraw() {
        String andOneLeg = "|--------------\n" + "|            |\n" + "|            o\n" + "|           /|\\ \n "
                + "|            \\";

        print(andOneLeg);
    }

    private void twoArmsDraw() {
        String andTwoArms = "|--------------\n" + "|            |\n" + "|            o\n" + "|           /|\\ ";

        print(andTwoArms);
    }

    private void oneArmDraw() {
        String andOneArm = "|--------------\n" + "|            |\n" + "|            o\n" + "|            |\\ ";
        print(andOneArm);

    }

    private void headDraw() {
        String andHead = "|--------------\n" + "|            |\n" + "|            o\n";
        print(andHead);
    }

    private void ropeDraw() {
        String rope = "|--------------\n" + "|            |";
        print(rope);
    }

    private void updateHiddenWord(char c, ArrayList<Integer> indexArr) {

        for (int i = 0; i < indexArr.size(); i++) {
            hiddenWord.set(indexArr.get(i), c);

        }
        print(hiddenWord);

    }

    private ArrayList<Integer> checkInputLetter(char c) {
        ArrayList<Integer> indexArr = new ArrayList<Integer>();

        char[] mysteryWordChar = createCharArray(mysteryWord);

        for (int i = 0; i < mysteryWord.length(); i++) {
            if (c == mysteryWordChar[i]) {
                indexArr.add(i);
            }
        }

        return indexArr;
    }

    private void showStatus() {
        print("Number of lifes left:" + lifes);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void introduceGame() {
        print("Welcome to Hangman. You know the drill, 6 wrong moves and you're toast.");
        print("Good luck, you need it. MOAHAHAHAHAHAHAHA!");
        print(" ");
        print(mysteryWord);
    }

    private void displayMenu() {
        print(" ");
        print(" ");
        print("**********************************************************");
        print("**    Type the letter of your choice ..... or else.     **");
        print("**       s - Game Status                                **");
        print("**       g - Guess a letter                             **");
        print("**       o _ Guess the entire word                      **");
        print("**********************************************************");
        print(" ");
        print(" ");
        print(hiddenWord);
    }

    private void print(Object obj) {
        System.out.println(obj);
    }

    private char[] createCharArray(String word) {
        char[] wordChars;
        wordChars = word.toCharArray();
        return wordChars;
    }

    private String chooseMysterWord(ArrayList<String> wordList) {
        String mystWord;
        Random rand = new Random();
        int randInt = rand.nextInt(wordList.size());
        mystWord = wordList.get(randInt);
        return mystWord;
    }

    private ArrayList<String> createWordList(File file) throws FileNotFoundException {
        Scanner wordScanner = new Scanner(file);
        ArrayList<String> wordListArray = new ArrayList<String>();

        while (wordScanner.hasNext()) {
            wordListArray.add(wordScanner.nextLine());
        }
        wordScanner.close();
        return wordListArray;
    }

    private File readInFile() {
        File dictionary = new File("english-words.csv");
        return dictionary;
    }
}
