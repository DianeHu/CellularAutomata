CompSci 308: Cell Society Design Document
===================

### Introduction
We're trying to solve the problem of creating a interface capable of animating several different cellular automata simulations where different simulation have different cell behaviors necessitating different parameters. The design must be flexible in adding new simulations and modifying the behavior of current simulations. The design must also be flexible in allowing the user to change simple parameters of the input. The user should be able to upload certain XML files, so they have control over all data controlled through the XML file. However, once the file is uploaded, at the moment they have no control over any parameters of the simulation. This may change in the future. 
### Overview
The main component of the program will be a Simulation class, will includes the game loop and user interface details. The Simulation class creates an instance of the Grid class, which has parameters of the XML file which describes the simulation, and the root for the scene. The Grid class creates an instance of an XMLReader with a parameter of an xml file. This XMLReader reads the XML file to return all the values required for the simulation to be run; it has several getter methods which parse the XML file for certain values. The Grid class then creates cells based on the information from XML reader, and adds the grid of cells to the scene through the root. The Grid class maintains a 2D array of Cells, and thus is able to update each cell's list of neighbors. There's a Cell abstract superclass, and the different types of cells from the different simulations will be subclasses. For example, there will be BlackCell, WhiteCell, FishCell, SharkCell, TreeCell, EmptyCell, etc. Cell methods, which would be implemented differently for each subtype, include moveNewLoc() to figure out where to move to the next location, killCell() to remove the cell and replace it with an EmptyCell, createCell() to reproduce, and isNeighbor() to see whether input coordinates correspond to a cell which counts as a neighbor by simulation rules. Each cell has an instance variable of an ArrayList of Neighbor cells which the Grid class updates with every step. This list is used when the cell makes decisions about what actions to undertake. The Grid class will go through its 2D array of Cells in its method updateGrid() to have each cell move to a new location or perform another action based on its neighbors.  The Grid class also ensures that each step of the simulation is being accurately visualized on the screen, using Cell instance variable of images used to represent them. The Cell classes purposes are to describe the behavior of each type of simulation, while the Grid class's purpose is to go update the entire simulation based on each cell's behavior. In essence, the Grid class manages the cells so that they all function together.

### User Interface
### Design Details
####Use Cases

 - Apply the rules to a middle cell
 - Apply the rules to an edge cell
 - Move to the next generation
 - Set a simulation parameter 
 - Switch simulations

### Design Considerations
One issue our group discussed was whether to have empty cell locations as truly empty in the scene, or to fill them with an EmptyCell class. We considered whether it would be easier to determine if a cell had empty neighboring spots it could move into if those spots were truly empty, or filled with the Empty class. For now, we are going to move forward with the EmptyCell subclass--we decided it might be easier to check instanceof to determine emptiness, rather than whether or not the location had any other cell in it. This way, when cells die off, we can simply replace their location with EmptyCell cells as well. This method may necessitate more code in creating the class, as compared to the other method, but overall it should make the rest of the design easier to read. We also discussed how we wanted the Grid class to look like, and whether Grid should have subclasses for different simulations. Ultimately, we decided that while it might be easier to have different subclasses, it would be best if we could make Grid as flexible as possible, so that extending future simulations can be easier.

### Team Responsibilities
**Tyler Yam** - Primary responsibility is user interface, focusing on the Simulation class. Secondary responsibility is figuring out how to write XML files and implementing the XML reader.
**Madhavi Rajiv** - Primary responsibility is working on cell subclasses for 2 simulations (Wator and Segregation), and secondary responsibility is working on the Grid class.
**Diane Hu** - Primary responsibility is working on cell subclasses for 2 simulations (Game of Life and Spreading of Fire), and secondary responsibility is working on the Grid class.

Overall, we're planning on checking in in person about once a day to see how our code fits together. We'll work together in person so that we don't write unnecessary code or code which doesn't fit in with another class.