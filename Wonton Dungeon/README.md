# This is a Unity "Game" Created by Nick Raffel Called Wonton Dungeon (WorkingTitle) 
This is a Game I made in Unity using C#. The main loop is a "game board" will randomly generate a Maze like labryinth where the player
must explore and find the Key Orbs to power the final portal and fight the final boss whilst also avoiding traps! 

This is my first "big" unity project that I made from scratch using mostly my own code. All the code here in the GitHub Repo
in this directory specfically, is everything I wrote for the game itself! I think I only used the basic First Person Unity Controller from the Asset Store otherwise.

### Maze generation 
The picture below shows the Maze that got randomly gnerated.

On the right you can see the parameters set for how the map should be made.

Each element is a room that has a special reason to exist, or is just a empty room.

The Min and Max postions state the parameters of where the rooms can spawn in the gride.

The Max Spawn parameter determins the maximum amount of times the room can spawn.

If the "obligatory" tag is checked, that means the room HAS to spawn the maximum number of times.

So for Element 1, for example, it is obligatory and has a max spawn of 5, so that means the room will spawn 5 times, no more or no less.

![Game1](https://user-images.githubusercontent.com/56615124/222036016-2cdf3e2e-4943-4554-be31-d6aa583b52ce.JPG)

### Game Traps
Spike Trap, spikes shoot up when you enter the room.

Deactivated Spikes
![spikesDeactivted](https://user-images.githubusercontent.com/56615124/222036673-2898ca68-088e-4a2d-bc27-79221edd89b6.JPG)
Activated Spikes
![SpikesActivated](https://user-images.githubusercontent.com/56615124/222036683-97816185-5f93-4fbe-bb77-ec48bbd4960d.JPG)
Spin Trap - Spins for a few seconds upon entering the room and damages the player if hit
![gameTrap](https://user-images.githubusercontent.com/56615124/222036916-0a77065c-2695-4796-be76-212ef47e1e51.JPG)
False Portal Trap - Uses up some keys and damages the Player
![falseport](https://user-images.githubusercontent.com/56615124/222036821-a2abcc4f-7977-4bbc-9a51-3818c8268ff6.JPG)

### BOSS FIGHT
Once you make it to the end you are met with a fearsome Boss! Cuboid, The Slither!
![boss](https://user-images.githubusercontent.com/56615124/222037316-f9191303-04e9-4cfd-b97b-a50a94914193.JPG)

### Collectables
Key Orb - Collect 3 (Or more depending on the parameters set) and unlock portals!
![keyorb](https://user-images.githubusercontent.com/56615124/222037149-1119ccd2-3a32-47a0-b508-ffc260735e0a.JPG)
Health - Collect to restore some health!
![health](https://user-images.githubusercontent.com/56615124/222037195-a248cb27-53e6-412a-9ba0-d7640400e9a5.JPG)

### Menus
This is the title screen/Main Menu, buttons to play,or quit with directions on screen
![mainmen](https://user-images.githubusercontent.com/56615124/222037501-461b1159-ba71-4dd9-9afb-df78fbde2f42.JPG)
This is a Game Over Screen, incase you die and want to retry (A new dungeon generates for more of a challenge!)
![gmo](https://user-images.githubusercontent.com/56615124/222037627-5d8dd45b-3d14-4bcc-bd5a-419245cb43e5.JPG)
The pause menu
![pause](https://user-images.githubusercontent.com/56615124/222037650-095a32f9-a03e-40cc-b110-570a7c625449.JPG)
