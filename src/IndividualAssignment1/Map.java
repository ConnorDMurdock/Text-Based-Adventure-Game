package IndividualAssignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map implements Serializable
{
    //A hashmap to store the unique roomID and room objects for easy referencing by the game.
    private HashMap<Integer, Room> mapReference;

    //An arraylist of all the rooms in the game.
    private ArrayList<Room> gameRooms;

    //Loads map data from a file. Rooms are read once per line and converted into objects for storage in the "gameRooms" ArrayList
    //Will return an error if the file cannot be found or if the formatting of the room values are incorrect.
    //Each map file has information on rooms, items, and puzzles. Each section is split by a "BREAK" word.
    public boolean LoadMapFromFile(String fileName){
        gameRooms = new ArrayList<>();
        boolean successful = false;
        File mapFile = new File("Maps/" + fileName);
        try{
            //Open Scanner and read file line by line
            //Each line in the file is one room. Each component of a room is split by a '-'
            int loadingStep = 0;
            Scanner readFile = new Scanner(mapFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("-");

                //Load the rooms from the file until "ROOMBREAK" is reached
                if (loadingStep == 0){
                    if (parts[0].equalsIgnoreCase("ROOMBREAK")){
                        CreateMapData();
                        loadingStep++;
                    }
                    else {
                        LoadRoomData(parts);
                    }
                }
                //Load the items from the file until "ITEMBREAK" is reached
                else if (loadingStep == 1){
                    if (parts[0].equalsIgnoreCase("ITEMBREAK")){
                        loadingStep++;
                    }
                    else {
                        LoadItemData(parts);
                    }
                }
                //Load the puzzles from the file until "PUZZLEBREAK" is reached
                else if (loadingStep == 2){
                    if (parts[0].equalsIgnoreCase("PUZZLEBREAK")){
                        loadingStep++;
                    }
                    else {
                        LoadPuzzleData((parts));
                    }
                }
            }
            //close Scanner and return true
            readFile.close();
            successful = true;
        } catch(FileNotFoundException fnfe){
            System.err.println("Map file not found. Make sure you entered the file name correctly.");
        } catch (NumberFormatException nfe){
            System.err.println("Unable to parse map data. Please check your map's formatting matches the README!");
            System.out.println(nfe);
        }
        return successful;
    }

    //Creates a quick reference to all the room objects via room ID.
    //Returns an error if roomID or roomConnections are invalid.
    //Only do this operation if the map reference doesn't already exist
    public void CreateMapData(){
        if (mapReference == null) {
            mapReference = new HashMap<>();
            try {
                for (Room room : gameRooms) {
                    mapReference.put(room.getRoomID(), room);
                }
            } catch (Exception e) {
                System.err.println("Map could not be created. Check room ID and Room Connections.");
                e.printStackTrace();
            }
        }
    }

    //Breaks the lines of the text file into parameters to turn into room objects
    public void LoadRoomData(String[] parts){
        int[] roomConnections = new int[4];
        int[] lockedConnections = new int[4];
        int roomID = Integer.parseInt(parts[0]);
        String roomName = parts[1];
        boolean visitedBefore = Boolean.parseBoolean(parts[7]);
        int roomIndex = 0;
        for(int i = 2; i < 6; i++){
            roomConnections[roomIndex] = Integer.parseInt(parts[i]);
            roomIndex++;
        }
        String roomDescription = parts[7];
        int lockedIndex = 0;
        for(int i = 8; i < 12; i++){
            lockedConnections[lockedIndex] = Integer.parseInt(parts[i]);
            lockedIndex++;
        }
        //create room object and store into arraylist
        Room room = new Room(roomID, roomName, roomConnections, visitedBefore, roomDescription, lockedConnections);
        gameRooms.add(room);
    }

    //Breaks the lines of the text file into parameters to turn into item objects
    public void LoadItemData (String[] parts){
        String itemName = parts[0] + " ";
        String itemDescription = parts[1];
        int location = Integer.parseInt(parts[2]);
        Item item = new Item(itemName, itemDescription);
        mapReference.get(location).addItemToRoom(item);
    }

    //Breaks the lines of the text file into parameters to turn into puzzle objects
    public void LoadPuzzleData (String[] parts){
        String puzzleType = parts[0];
        String puzzleDescription = parts[1];
        String solution = parts[2];
        int roomID = Integer.parseInt(parts[3]);
        int numberOfAttempts = Integer.parseInt(parts[4]);
        String puzzleSolveText = parts[5];
        Puzzle puzzle = new Puzzle(puzzleType, puzzleDescription, solution, numberOfAttempts, puzzleSolveText);
        mapReference.get(roomID).setRoomPuzzle(puzzle);
    }

    //returns the room object based on roomID
    public Room GetRoom(int roomID){
        return mapReference.get(roomID);
    }
}
