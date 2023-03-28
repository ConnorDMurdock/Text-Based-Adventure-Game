package IndividualAssignment1;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable
{
    //A unique numberID given to a room for identification.
    private int roomID;

    //The name of the room displayed to the player upon entry.
    //e.g. "Forest"
    private String roomName;

    //An array of 4 integers to identify room connections.
    //Order is North, East, South, West
    //0 = no connection in that direction
    private int[] roomConnections;

    //A boolean to track whether this room has been visited before.
    private boolean seenBefore;

    //A text description to display to the player when they enter this room.
    private String roomDescription;

    //An arraylist of items to act as this room's inventory
    private ArrayList<Item> roomInventory;

    //The puzzle within this room, if it exists.
    private Puzzle roomPuzzle;

    //The same format as the roomConnections [N,E,S,W]
    //0 means unlocked, player can move to and from that direction freely.
    //1 means locked, the player cannot move in that direction from this room.
    private int[] lockedConnections;


    //Constructor
    public Room(int roomID, String roomName, int[] roomConnections, boolean seenBefore, String roomDescription, int[] lockedConnections) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomConnections = roomConnections;
        this.seenBefore = seenBefore;
        this.roomDescription = roomDescription;
        this.lockedConnections = lockedConnections;
        roomInventory = new ArrayList<>();
    }

    //Getters and Setters
    public int getRoomID() {
        return roomID;
    }
    public int[] getRoomConnections() {
        return roomConnections;
    }

    public int[] getLockedConnections() { return lockedConnections; }

    //sets all the locked connections to unlocked (0) in this room.
    public void unlockRoom(){
        for (int i = 0; i <= 3; i++){
            lockedConnections[i] = 0;
        }
    }

    //getter and setter for this room's puzzle.
    public Puzzle getRoomPuzzle() {
        return roomPuzzle;
    }

    public void setRoomPuzzle(Puzzle roomPuzzle) {
        this.roomPuzzle = roomPuzzle;
    }

    //Show the user all the items in this room's inventory.
    public void exploreRoom(){
        if (roomInventory.size() > 0){
            System.out.println("You find the following items in this room:");
            for (int i = 0; i < roomInventory.size(); i++) {
                System.out.println(roomInventory.get(i).getItemName());
            }
        }
        else {
            System.out.println("You find nothing of importance in this room.");
        }
    }

    //Add item to this room's arraylist
    public void addItemToRoom(Item item){
        roomInventory.add(item);
    }

    //Remove the item from room by given item name.
    public Item removeItemFromRoom(String itemName){
        Item item = null;
        for (int i = 0; i < roomInventory.size(); i++){
            if (roomInventory.get(i).getItemName().equalsIgnoreCase(itemName)){
                item = roomInventory.get(i);
                roomInventory.remove(i);
            }
        }
        return item;
    }

    //Display the room information whenever the player enters this room.
    //Only display the possible connections if the player has the compass item
    //in their inventory.
    public void EnterRoom(boolean playerHasCompass){
        System.out.println("--== " + roomName + " ==--");
        System.out.println(roomDescription);

        if (seenBefore){
            System.out.println("This place feels familiar...");
        }

        if (playerHasCompass){
            String connections = "";
            if (roomConnections[0] != 0){
                connections += "North, ";
            }
            if (roomConnections[1] != 0){
                connections += "East, ";
            }
            if (roomConnections[2] != 0){
                connections += "South, ";
            }
            if (roomConnections[3] != 0){
                connections += "West, ";
            }

            System.out.println("There's a path to the " + connections);
        }

        System.out.println("What would you like to do?");
        this.seenBefore = true;
    }
}
