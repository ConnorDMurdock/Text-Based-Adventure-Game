package IndividualAssignment1;

import java.io.Serializable;

public class Puzzle implements Serializable
{
    //the type of puzzle this is. Possible options include:
    //Text - requires a word or phrase from the player
    //Item - requires the player to 'use' an item from their inventory
    private String puzzleType;

    //The message displayed to the user when they start the puzzle
    private String puzzleDescription;

    //The required solution to solve this puzzle
    private String puzzleSolution;

    //the number of remaining attempts for this puzzle
    private int numberOfAttempts;

    //whether this puzzle has been solved or not
    private boolean isSolved;

    //A message displayed to the user after the puzzle is solved
    private String puzzleSolveText;

    //Constructor
    public Puzzle(String puzzleType, String puzzleDescription, String puzzleSolution, int numberOfAttempts, String puzzleSolveText) {
        this.puzzleType = puzzleType;
        this.puzzleDescription = puzzleDescription;
        this.puzzleSolution = puzzleSolution;
        this.numberOfAttempts = numberOfAttempts;
        this.puzzleSolveText = puzzleSolveText;
        this.isSolved = false;
    }

    //Determines whether this puzzle should be solved or not based on the given input from the user
    public boolean Solve(String solution) {
        boolean solved = false;
        numberOfAttempts--;
        if (this.puzzleType.equalsIgnoreCase("Text")){
            if (puzzleSolution.equalsIgnoreCase(solution)){
                solved = true;
            }
            else {
                System.err.println("That's the wrong solution!");
            }
            return solved;
        }
        else if (this.puzzleType.equalsIgnoreCase("Item")){
            String[] solutionParts = solution.split(" ");
            if (solutionParts[0].equalsIgnoreCase("use")){
                String itemNeeded = "";
                for (int i = 1; i<solutionParts.length; i++){
                    itemNeeded += solutionParts[i] + " ";
                }
                if (itemNeeded.equalsIgnoreCase(puzzleSolution)){
                    solved = true;
                }
            }
        }
        return solved;
    }

    //Getters and Setters
    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public String getPuzzleDescription() {
        return puzzleDescription;
    }

    public String getPuzzleType() {
        return puzzleType;
    }

    public String getPuzzleSolveText() {
        return puzzleSolveText;
    }
}
