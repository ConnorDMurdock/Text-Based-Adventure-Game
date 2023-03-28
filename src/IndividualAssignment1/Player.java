package IndividualAssignment1;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
    private Room currentRoom;

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private Map gameMap;

    private ArrayList<Item> playerInventory;

    private boolean hasCompass;

    //Player must be given a map in order to navigate
    //Defaults to the first room in the roomID list
    //Defaults to the player not having the compass
    public Player(Map gameMap) {
        this.gameMap = gameMap;
        this.currentRoom = gameMap.GetRoom(1);
        playerInventory = new ArrayList<>();
        this.hasCompass = false;
    }

    //Checks the command to see if it matches any of the cardinal direction commands
    //If it doesn't, inform the player that their input was invalid and to try another command
    //If no room exists in the attempted direction, inform the player no room exists in that direction
    //0=north 1=east 2=south 3=west
    public void MoveVerification(String direction){
        try {
            if (direction.equalsIgnoreCase("North") || direction.equalsIgnoreCase("N")) {
                Move(0);
            } else if (direction.equalsIgnoreCase("East") || direction.equalsIgnoreCase("E")) {
                Move(1);
            } else if (direction.equalsIgnoreCase("South") || direction.equalsIgnoreCase("S")) {
                Move(2);
            } else if (direction.equalsIgnoreCase("West") || direction.equalsIgnoreCase("W")) {
                Move(3);
            } else {
                System.err.println("That is not a valid input, please try again.");
                System.out.println("What would you like to do?");
            }
        } catch (NullPointerException npe){
            System.err.println("There is not a room in that direction, please try again.");
        }
    }

    //Handles the actual movement between rooms based on a given direction.
    //0=north 1=east 2=south 3=west
    //Checks the locked connections array to make sure that the player can go int ath direction
    //The connections will unlock once the puzzle in the room is solved.
    private void Move(int direction){
        int newRoomID = currentRoom.getRoomConnections()[direction];
        if (newRoomID == 0){
            throw new NullPointerException();
        }
        if (currentRoom.getLockedConnections()[direction] == 1){
            System.err.println("That direction is locked by a puzzle. Solve the puzzle to continue in this direction!");
        }
        else{
            Room newRoom = gameMap.GetRoom(newRoomID);
            currentRoom = newRoom;
            newRoom.EnterRoom(hasCompass);
        }
    }

    //pick item up from the current room by name and places in the player's inventory
    public void pickupItem(String itemName){
        Item item = currentRoom.removeItemFromRoom(itemName);
        if (item != null) {
            if (itemName.equalsIgnoreCase("Compass ")) {
                hasCompass = true;
            }
            playerInventory.add(item);
            System.out.println("You picked up the " + item.getItemName());
        } else {
            System.err.println("That item does not exist in this room!");
        }
    }

    //Removes the item from the player's inventory and places in the current room
    public void dropItem(String itemName){
        Item item = null;
        for (int i = 0; i < playerInventory.size(); i++){
            if (playerInventory.get(i).getItemName().equalsIgnoreCase(itemName)){
                item = playerInventory.get(i);
                playerInventory.remove(i);
            }
        }
        if (item != null){
            if (itemName.equalsIgnoreCase("Compass ")){
                hasCompass = false;
            }
            System.out.println("You dropped the " + item.getItemName());
            playerInventory.remove(itemName);
            currentRoom.addItemToRoom(item);
        }
        else {
            System.err.println("You do not have this item in your inventory.");
        }
    }

    //Gets all the items by name from the player's inventory and displays to the user
    public void checkInventory(){
        if (playerInventory.size() > 0){
            System.out.println("--== Inventory ==--");
            for (int i = 0; i < playerInventory.size(); i++){
                System.out.println(playerInventory.get(i).getItemName());
            }
        }
        else {
            System.err.println("You don't have any items!");
        }
    }

    //Checks whether a player has an item by matching item names
    public boolean playerHasItem(String itemName){
        boolean hasItem = false;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().equalsIgnoreCase(itemName)) {
                hasItem = true;
                break;
            }
        }
        return hasItem;
    }

    //permanently removes an item from the player's inventory and from the game
    //used for puzzle items and consumables
    public void removeItemFromPlayer(String itemName){
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().equalsIgnoreCase(itemName)) {
                playerInventory.remove(i);
            }
        }
    }

    //Shows the user the queried item's name and description
    public void inspectItem(String itemName){
        Item item = null;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getItemName().equalsIgnoreCase(itemName)) {
                item = playerInventory.get(i);
                item.inspectItem();
            }
        }
        if (item == null){
            System.err.println("You don't have that item!");
        }
    }

    //returns whether the player has the compass item or not
    public boolean playerHasCompass() {
        return hasCompass;
    }
}
