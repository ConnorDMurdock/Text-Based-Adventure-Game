package IndividualAssignment1;

import java.io.Serializable;

public class Item implements Serializable
{
    //A name given to the item for identification by the user.
    private String itemName;

    //A brief description of the item to be displayed by the console when the inspect command is used.
    private String itemDescription;

    //Constructor
    public Item(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    //Gets the item name
    public String getItemName() {
        return itemName;
    }

    //Prints out the item name along with the item description to the console when the "Inspect <item>" command is used.
    public void inspectItem(){
        System.out.println(itemName);
        System.out.println(itemDescription);
    }
}
