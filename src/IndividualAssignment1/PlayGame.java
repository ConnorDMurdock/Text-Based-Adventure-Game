package IndividualAssignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {
    //=--------------------------------------------=
    //|----Text Based Adventure Game Assignment----|
    //|----By: Connor Murdock----------------------|
    //|----ITEC3860 Section 02---------------------|
    //=--------------------------------------------=
    //The main class is split into several game phases.
    //Each phase relies on the previous phase to be completed before continuing
    //Each phase is outlined by dividers
    public static void main(String[] args) {
        //===============   START PHASE   =====================
        //The start phase sets up the core game components and welcomes the player

        //Create necessary objects
        PlayGame playGame = new PlayGame();
        Scanner input = new Scanner(System.in);
        Map gameMap = new Map();
        Player player = null;

        //Welcome the Player
        System.out.println("Welcome to My Adventure Game");

        //===========  FILE SELECTION PHASE  ==================
        //The file selection phase gets the map data from either a save file or new map file
        //Players can decide to create a new save game using either the default map or their own custom map
        //Players can additionally load a previous save file to continue their journey where they left off before

        //Create a new save file or load a previous one
        boolean validEntry = false;
        while (!validEntry){
            System.out.println("Type: 'New Game' to create a new game. Or, type 'Load Game' to load a previous save.");
            String gameType = input.nextLine();

            if (gameType.equalsIgnoreCase("New Game")){
                //enter new game phase
                System.out.println("Creating a New Game...");
                gameMap = playGame.CreateNewGame();
                player = new Player(gameMap);
                validEntry = true;
            }
            else if (gameType.equalsIgnoreCase("Load Game")){
                //enter load game phase
                System.out.println("Loading a Save Game...");
                ArrayList<Object> loadedFileObjects = playGame.LoadFile();
                player = (Player) loadedFileObjects.get(0);
                gameMap = (Map) loadedFileObjects.get(1);

                validEntry = true;

            }
            else {
                System.err.println("I'm sorry, but I don't recognize that command. Please try again.");
            }
        }

        //==============  PLAY PHASE  =====================
        //Enter Play Phase of the game. User can input commands to control the Player and interact with the Map
        //Features of play phase include navigation, help, and exit, interact with items, and solving puzzles

        player.getCurrentRoom().EnterRoom(player.playerHasCompass());
        boolean playing = true;
        while (playing){
            String playerInput = input.nextLine();
            String[] inputParts = playerInput.split(" ");
            if (inputParts[0].equalsIgnoreCase("Help")){
                //display help command for the explore phase
                playGame.ExploreHelp();
            }
            else if (inputParts[0].equalsIgnoreCase("exit")){
                //Save game and exit
                playGame.SaveGame(gameMap, player);
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            else if (inputParts[0].equalsIgnoreCase("Move") && inputParts.length > 1){
                player.MoveVerification(inputParts[1]);
            }
            else if (inputParts[0].equalsIgnoreCase("Explore")){
                player.getCurrentRoom().exploreRoom();
            }
            else if (inputParts[0].equalsIgnoreCase("Pickup")  && inputParts.length > 1){
                String itemName = playGame.makeStringFromInput(inputParts);
                player.pickupItem(itemName);
            }
            else if (inputParts[0].equalsIgnoreCase("Drop")  && inputParts.length > 1){
                String itemName = playGame.makeStringFromInput(inputParts);
                player.dropItem(itemName);
            }
            else if (inputParts[0].equalsIgnoreCase("Inventory")){
                player.checkInventory();
            }
            else if (inputParts[0].equalsIgnoreCase("Inspect")  && inputParts.length > 1){
                String itemName = playGame.makeStringFromInput(inputParts);
                player.inspectItem(itemName);
            }
            else if (inputParts[0].equalsIgnoreCase("Start") && inputParts[1].equalsIgnoreCase("Puzzle")){
                playGame.startPuzzleMode(player, input);
            }
            else {
                System.err.println("That command is not valid. Please try again.");
            }
        }
    }

    //Creates a new save game
    //Players can choose to load their own custom map or the default map.
    //Map files should always be a .txt file following the correct formatting outlined in the README file.
    //Returns the final map object to be used by other objects in the game.
    //The game cannot continue unless a map is loaded.
    public Map CreateNewGame(){
        Scanner input = new Scanner(System.in);
        Map map = new Map();
        boolean validInput = false;
        while (!validInput){
            System.out.println("Please specify a map file to create a game from. e.g. 'MyMap.txt'");
            System.out.println("Alternatively, type 'Default' to load the default map.");
            String newGameMapType = input.nextLine();
            if (newGameMapType.equalsIgnoreCase("Default")){
                map.LoadMapFromFile("DefaultMap.txt");
                map.CreateMapData();
                System.out.println("Loading Default Map...");
                validInput = true;
            }
            else {
                boolean successfulLoad = map.LoadMapFromFile(newGameMapType);
                if (successfulLoad) {
                    map.CreateMapData();
                    System.out.println("Loading " + newGameMapType + "...");
                    validInput = true;
                }
            }
        }
        return map;
    }

    //Saves the player and map data to a .dat file specified by the User
    //Objects are written in the following order
    //0=Player 1=Map
    public void SaveGame(Map map, Player player){
        Scanner input = new Scanner(System.in);
        while (true){
            //Prompt the player if they'd like to save before exiting
            System.out.println("Would you like to save the game? Yes or No");
            String saveChoice = input.nextLine();

            //If the player decides not to save the game, simply exit
            if (saveChoice.equalsIgnoreCase("No") || saveChoice.equalsIgnoreCase("N")){
                System.out.println("Thanks for playing!");
                System.exit(0);
            }

            //If the player wants to save the game, ask for a save file name, then write the Player and Map objects to a .dat file with that name
            //All save files are located under the 'Saves' folder in the root of the project directory
            //exist with status 1 if the file cannot be saved. All save data is then lost.
            else if (saveChoice.equalsIgnoreCase("Yes") || saveChoice.equalsIgnoreCase("Y")){
                System.out.println("Please enter a name for the save file. This name will be used to load the save later, so remember it!");
                String saveName = input.nextLine();
                try {
                    ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Saves/" + saveName + ".dat"));
                    writer.writeObject(player);
                    writer.writeObject(map);
                    writer.close();
                    System.out.println("Game saved successfully.");
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                } catch (IOException ioe){
                    System.err.println("File could not be saved.");
                    ioe.printStackTrace();
                    System.exit(1);
                }
            }
            else {
                System.err.println("I'm sorry, but I don't recognize that command. Please try again.");
            }
        }

    }

    //Loads a .dat file, with a name specified by the player
    //If the player saved their game as 'Save1.dat' they can reload the save by typing 'Save1' when prompted
    //Returns errors if the file can't be found, or if the file cannot be read
    //All objects are loaded in the following order
    //0=Player 1=Map
    public ArrayList<Object> LoadFile(){
        Scanner input = new Scanner(System.in);
        ArrayList<Object> objectsFromFile = new ArrayList<>();
        while (true){
            System.out.println("Please enter the name of your save file:");
            String saveName = input.nextLine();
            try {
                ObjectInputStream load = new ObjectInputStream(new FileInputStream("Saves/" + saveName + ".dat"));
                while (true){
                    Object obj = load.readObject();
                    objectsFromFile.add(obj);
                }
            } catch (EOFException eofe){
                System.out.println("File load complete");
                return objectsFromFile;
            } catch (IOException ioe){
                System.err.println("That file cannot be found, please try again");
            } catch (ClassNotFoundException cnfe){
                System.out.println("Error loading save file. Try another file");
            }
        }
    }

    //Starts the puzzle loop of the game. If the user enters the command "Start Puzzle" they will be brought to this loop.
    //If the puzzle is an item puzzle, check to make sure the player has the correct item, uses the "use <itemName>" command,
    //and check against the puzzle's required solution.
    //The player leaves the puzzle loop if they run out of attempts or if they use the "leave puzzle" command.
    //The user can see the possible command options by using the "help" command
    public void startPuzzleMode(Player player, Scanner input){
        Puzzle roomPuzzle = player.getCurrentRoom().getRoomPuzzle();
        if (roomPuzzle != null){
            while (!roomPuzzle.isSolved()){
                if (roomPuzzle.getNumberOfAttempts() <= 0){
                    System.err.println("You have no more attempts at this puzzle!");
                    player.getCurrentRoom().EnterRoom(player.playerHasCompass());
                    break;
                }
                else if (roomPuzzle.getNumberOfAttempts() <= 3){
                    System.err.println("Attempts remaining: " + roomPuzzle.getNumberOfAttempts());
                }
                System.out.println(roomPuzzle.getPuzzleDescription());
                String playerInput = input.nextLine();
                String[] solutionParts = playerInput.split(" ");
                String itemName = "";
                for (int i = 1; i < solutionParts.length; i++){
                    itemName += solutionParts[i] + " ";
                }
                //Checks to see if the user inputs 'leave puzzle'
                //if so, leave this loop and the puzzle remains in the room
                //the player will then be free to explore once again
                if (playerInput.equalsIgnoreCase("leave puzzle")){
                    System.out.println("You leave this puzzle behind for now.");
                    player.getCurrentRoom().EnterRoom(player.playerHasCompass());
                    break;
                }
                //displays the help command for puzzles if the user inputs 'help'
                else if (playerInput.equalsIgnoreCase("help")){
                    PuzzleHelp();
                }
                //If the puzzle in this room is an Item puzzle, make sure the player uses the
                //'use' keyword AND that they have the required item in their inventory
                else if (roomPuzzle.getPuzzleType().equalsIgnoreCase("Item")){
                    if (roomPuzzle.Solve(playerInput)){
                        if (player.playerHasItem(itemName)){
                            roomPuzzle.setSolved(roomPuzzle.Solve(playerInput));
                            player.removeItemFromPlayer(itemName);
                            player.getCurrentRoom().unlockRoom();
                            System.out.println(roomPuzzle.getPuzzleSolveText());
                            player.getCurrentRoom().setRoomPuzzle(null);
                            break;
                        }
                        else {
                            System.err.println("You don't have that item!");
                        }
                    }
                    else {
                        System.err.println("That's the wrong solution!");
                    }

                }
                //If the puzzle in this room in a Text puzzle, make sure the user's input
                //matches the required phrase for the solution
                else if (roomPuzzle.getPuzzleType().equalsIgnoreCase("Text")){
                    if (roomPuzzle.Solve(playerInput)){
                        roomPuzzle.setSolved(roomPuzzle.Solve(playerInput));
                        player.getCurrentRoom().unlockRoom();
                        System.out.println(roomPuzzle.getPuzzleSolveText());
                        player.getCurrentRoom().setRoomPuzzle(null);
                        break;
                    }
                }
            }
        }
        else {
            System.err.println("There is no puzzle in this room.");
        }
    }

    //combines the input string array back into one string
    public String makeStringFromInput(String[] inputParts){
        String words = "";
        for (int i = 1; i<inputParts.length; i++){
            words += inputParts[i] + " ";
        }
        return words;
    }

    //Prints out the following display as a list of commands to inform the player of possible options during exploration
    public void ExploreHelp(){
        System.out.println("====== COMMANDS ======");
        System.out.println("|  Movement Options  |");
        System.out.println("|      'Move' +      |");
        System.out.println("|   'North' OR 'N'   |");
        System.out.println("|   'East'  OR 'E'   |");
        System.out.println("|   'South' OR 'S'   |");
        System.out.println("|   'West'  OR 'W'   |");
        System.out.println("|      'Explore'     |");
        System.out.println("|--------------------|");
        System.out.println("|   Puzzle Options   |");
        System.out.println("|   'start puzzle'   |");
        System.out.println("|--------------------|");
        System.out.println("|    Item Options    |");
        System.out.println("|'pickup <item name>'|");
        System.out.println("|'inspect<item name>'|");
        System.out.println("|--------------------|");
        System.out.println("|  To See This Menu  |");
        System.out.println("|       'Help'       |");
        System.out.println("|--------------------|");
        System.out.println("|  To Exit The Game  |");
        System.out.println("|       'Exit'       |");
        System.out.println("======================");
    }

    //Prints out the following display as a list of commands to inform the player of possible options during puzzles
    public void PuzzleHelp(){
        System.out.println("======  PUZZLE  ======");
        System.out.println("| 'use  <item name>' |");
        System.out.println("|         OR         |");
        System.out.println("|'<puzzle  solution>'|");
        System.out.println("|--------------------|");
        System.out.println("|   To Leave Puzzle  |");
        System.out.println("|   'leave puzzle'   |");
        System.out.println("|--------------------|");
        System.out.println("|  To See This Menu  |");
        System.out.println("|       'Help'       |");
        System.out.println("|--------------------|");
    }
}
