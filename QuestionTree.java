/*
Cole and Jake
5/31/23
lab 6, 20 questions

this class creates a tree of linked nodes and the necessary methods.
the variables are
totalGames, the total games that have been played
gamesWon, the number of games the computer has won
root, the root of the tree of nodes
ui, defines the ui that is passed in the constructor

the methods are,
QuestionTree, a constructor which creates the root, defines the ui, and sets count variables to 0.

play, the method called by the main class to play 1 game of 20 questions with the user. this method
calls its helper method.

playHelper, actually plays the game with the user using recursion to loop through the tree 
of nodes.

save, saves the current tree to a file passed by the main class, this method calls 
its helper method.

saveHelper, actually saves the tree to a given file and uses recursion to loop through the tree.
The questions and answers are given the identifier "Q:" and "A:" in the created file.

load, loads a tree from a given file. this method loops until the input has no next line while
passing the lines data to its helper method.

loadHelper, adds the contents of the current line to the tree being created.

totalGames, returns the totalGames int.

gamesWon, returns the gamesWon int.

isLeafNode, returns if a node is at the end of the tree by checking if its paths are null.
the leaf nodes are also answer nodes.

the helper methods are there to make the recursion in the methods a bit easier to follow

*/ // imports
import java.util.Scanner;
import java.io.PrintStream;

public class QuestionTree {

   private int totalGames; // total games that have been played
   private int gamesWon; // the games the computer has won

   private QuestionNode root; // the root of the tree
   
   private UserInterface ui; // defines the ui

   public QuestionTree(UserInterface ui) {
      root = new QuestionNode("computer");
      this.ui = ui; 
      totalGames = 0;
      gamesWon = 0;
   } // end of QuestionTree constructor
   

   public void play() {
      totalGames ++;
      if (root == null) {
         throw new IllegalArgumentException("ERROR: root cannot be null");
      } else {
         root = playHelper(root); 
      } // checks for invalid parameters
   } // end of play method
   
   
   private QuestionNode playHelper(QuestionNode current) {
      // uses recursion to loop through the tree from the root node
      // until finding a leaf node. then seeing if the computer can guess the answer.
      // if the computer cannot a new node is added.
      if (isLeafNode(current)) { // checks if the current node is a leaf node
         ui.print("Would your object happen to be " + current.data +"? (y/n) ");
         if (ui.nextBoolean()) { // checks the final y / n question
            // if computer guessed right
            gamesWon ++;
            ui.println("I got it right!");
         } else {
            // computer guessed wrong
            // and it is not an answer node
            ui.print("What is the name of your object? ");
            QuestionNode answerNode = new QuestionNode(ui.nextLine()); // creates the answer node
            ui.println("Please give me a yes/no question that distinguishes");
            ui.print("between your object and mine: ");
            String newQuestion = ui.nextLine(); // gets the desired name of the node
            ui.print("And what is the answer for your object? (y/n) ");
            if (ui.nextBoolean()) {
               // node added to the left / yes path
               current = new QuestionNode(newQuestion, answerNode, current);
            } else {
               // node added to the right / no path
               current = new QuestionNode(newQuestion, current, answerNode); 
            } // end of if new node is added to left or right
         } // end of if else computer or human won round
      } else {
         // not at a leaf node
         ui.print(current.data);
         if (ui.nextBoolean()) {
            // continues down left / yes path
            current.yes = playHelper(current.yes);
         } else {
            // continues down right / no path
            current.no = playHelper(current.no);
         } // end of if else checking which path to continue down
      } // end of if else leaf node check
      return current;
   } // end of askQuestions method
   
   
   public void save(PrintStream output) {
      // saves current tree to output file
      // calls a helper file to do the needed recursion
      saveHelper(output, root);
   } // end of save method
   
   
   private void saveHelper(PrintStream output, QuestionNode root) {
      if (root == null || output == null) {
         throw new IllegalArgumentException("ERROR: root and output cannot be null");
      } else { // checks for invalid parameters
         // saves current tree to an output file
         if (isLeafNode(root)){
            // checks if node is a leaf / answer
            output.println("A:" + root.data); // adds data to file with answer identifier
         } else {
            // the node is a branch / question
            output.println("Q:" + root.data); // adds data to file with question identifier
            saveHelper(output, root.yes); // loop through left / yes paths
            saveHelper(output, root.no); // loop through right / no paths
         } // end of if else leaf / answer check
      }
   } // end of saveHelper method
   
   
   public void load(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException("ERROR: input cannot be null");
      } else {
         if (input.hasNextLine()) {
            root = loadHelper(input);
            load(input);
         } // loops through recursion for all the lines in the file
      } // end of null check
   } // end of load method
   
   
   private QuestionNode loadHelper(Scanner input) {
      // the main loading method
      // gets the current line data and gets if its a question or answer
      // if then loops through the tree adding values as it goes (in pre-order)
      String data = input.nextLine(); // gets the lines data
      
      QuestionNode newRoot = new QuestionNode(data.substring(2)); // creates the new node
      // note that substring is used to cut the "Q:" or "A:" 
      if (data.contains("Q:")) {
         newRoot.yes = loadHelper(input); // loads the left / yes path
         newRoot.no = loadHelper(input); // loads the right / no path
      } // end of data check
      return newRoot;
   } // end of loadHelper method
   
   
   public int totalGames() {
      return totalGames; // gets the total games played
   } // end of totalGames method
   
   
   public int gamesWon() {
      return gamesWon; // gets the games the computer has won
   } // end of gamesWon method


   private boolean isLeafNode(QuestionNode node) {
      // checks if the node is an answer / leaf node
      // it does this by checking if the left or right path is defined
      return (node.yes == null || node.no == null);
   } // end of isLeafNode method


} // end of QuestionTree class
