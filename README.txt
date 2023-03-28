=--------------------------------------------=
|----Text Based Adventure Game Assignment----|
|----By: Connor Murdock----------------------|
|----ITEC3860 Section 02---------------------|
=--------------------------------------------=

====== COMMANDS ======
|  Movement Options  |
|      'Move' +      |
|   'North' OR 'N'   |
|   'East'  OR 'E'   |
|   'South' OR 'S'   |
|   'West'  OR 'W'   |
|      'Explore'     |
|--------------------|
|   Puzzle Options   |
|   'start puzzle'   |
|--------------------|
|    Item Options    |
|'pickup <item name>'|
|'inspect<item name>'|
|--------------------|
|  To See This Menu  |
|       'Help'       |
|--------------------|
|  To Exit The Game  |
|       'Exit'       |
======================


=--------------------------------------------=
|----------------Introduction----------------|
=--------------------------------------------=

Welcome to my Text Based Adventure Game! This game allows the player to navigate on the cardinal directions
between different room. The rooms are generated into a map by reading a text file. The game offers a default
map but players can also create their own custom maps to load instead. The game also offers players the ability
to save and load their games. Saved games are saved as .dat files and given a name by the player. The next time 
the game is launched, players can load that save file by name. Save files can be found in the 'Saves' folder.

=--------------------------------------------=
|------------Creating Custom Maps------------|
=--------------------------------------------=

The formating instructions for the map text file is as follows:

=============== Format ===============
**The file MUST be a .txt file
**Map files MUST go into the 'Maps' folder
---------------------------------------------------------------------
ROOMS:
roomID-roomName-CN-CE-CS-CW-visitedBefore-roomDescription-LN-LE-LS-LW
int-String-int-int-int-int-boolean-String-int-int-int-int

**Each new line is a room
**The cardinal directions are in the following order: N-E-S-W
**CN-CE-CS-CW refer to room connections. The room connection will be the ID number of the adjacent room.
A 0 in a direction means there is no connection in that direction
**LN-LE-LS-LW refer to locked connections. a 0 means that direction is unlocked, and a 1 means that direction is locked
**roomID MUST be higher than 0 AND be unique
**On the line after your last room, you MUST have the phrase ROOMBREAK
---------------------------------------------------------------------
ITEMS:
itemName-itemDescription-roomID
String-String-int

**Each new line is an item
**The roomID is equal to the ID of the room this item is found inside
**On the line after your last room, you MUST have the phrase ITEMBREAK
---------------------------------------------------------------------
PUZZLES:
puzzleType-puzzleDescription-puzzleSolution-numberOfAttempts-puzzleSolveText
String-String-String-int-String

**There are two puzzle types: Text and Item
**If the puzzle is an Item puzzle, the solution must be the required item's name with a space afterwards
**If the puzzle is a Text puzzle, the solution will be the phrase needed from the user to solve the puzzle
**puzzle description = the message shown when the puzzle starts
**puzzleSolveText = the message shown when the puzzle is solved

========== Format  Example ===========
1-My House-0-2-0-0-false-A modest house stands before you. This is your home.-0-0-0-0
2-Plains-0-3-0-1-false-A large field stretches out in front of you. You feel a sense of adventure.-0-0-0-0
3-Forest-4-5-0-2-false-The tall trees tower over you. A short goblin meanders about the area.-0-1-0-0
4-Mountains-0-0-3-0-false-You can see for miles up here. Perhaps you can find something useful up here.-0-0-0-0
5-Deep Forest-0-6-0-3-false-As you enter deeper into the forest, the light of the sun becomes faint. A large temple door stands before you.-0-1-0-0
6-Lake-0-0-0-5-false-The temple door makes way to a beautiful lake. You are overcome with a sense of tranquility and peace...-0-0-0-0
ROOMBREAK
Crude Note-A note with crude handwriting. It reads, "PASSWERD IZ HONEY"-2
Compass-With your trusty compass, you'll never get lost!-1
Ancient Energy Core-This old energy core pulsates with a beautiful blue glow.-4
ITEMBREAK
Text-The goblin stands before you, blocking your passage to the next room. He shouts at you, "WHAT'S THE PASSWORD?"-HONEY-3-999-"YES THAT'S RIGHT! YUMMY DELICIOUS HONEY!" The goblin happily replies, as he moves out of your path.
Item-A large temple door blocks your path, eminating a blue glow. There seems to be some kind of pedestal that's missing something next to it-Ancient Energy Core -5-999-The ancient temple door whirs to life, as the path forward opens in front of you.
PUZZLEBREAK

=--------------------------------------------=
|-------------Default Map Layout-------------|
=--------------------------------------------=

 		     	    -------------
 		      	    |     4     |
 		            | Mountains |
 		            -------------
------------- ------------- ------------- ------------- -------------
|     1     | |     2     | |     3     | |     5     | |     6     |
|  My House | |   Plains  | |   Forest  | |Deep Forest| |    Lake   |
------------- ------------- ------------- ------------- -------------

--== ITEMS ==--           |--== PUZZLES==--
1. Compass                |3. Text Puzzle - Solution: HONEY
2. Crude Note             |5. Item Puzzle - Solution: Ancient Energy Core
4. Ancient Energy Core    |



