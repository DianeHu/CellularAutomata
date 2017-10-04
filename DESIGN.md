
Cell Society Design File
===================
#### mr284, dh224, tjy8
Madhavi, Diane, Tyler

### High Level Design Goals
The high level design goals of this project was to create a project where adding new simulations would require very little extra code or logic or refactoring, and where adding new GUI features would require only editing a small part of the code. The project attempted to do this by creating inheritance hierarchies for all front end components to separate out the generalized code and specific code for each simulation. In this way, adding a new simulation only requires adding a small amount of simulation specific code for each feature. Another goal of our project was to encapsulate different features as much as possible from one another. We tried to create the XML Reading, the GUI and Visualization, and the Backend logic for each simulation as much as possible.

###  How to add new features
In order to add a new simulation, it's necessary to create a new subclass for Simulation, Graph, XMLReader, and GridConfiguration. All of these subclasses will be very similar in format to existing subclasses; however they must be modified to account for different thresholds, user input fields, cell name labels, and other simulation specific minutiae. Also, new Cells subclasses must be added to account for each cell's interaction behavior, and a new map for the simulation must be created in grid mapping certain characters to certain specific cell types. Finally, in main class, a text input string which is taken from the user must be mapped to the right Simulation subclass.

In order to add new features to the style document, it's necessary to modify the StyleReader and StyleConfiguration classes to get more arguments. Then the main class will read these arguments and send the additional arguments to the Simulation class, which will send these arguments to the Grid class.

In order to add a new GUI feature, a new inheritance hierarchy could be created for it if it differed based on the specification of the simulation, and the right subtype of the feature's class would be initialized in the corresponding Simulation subclass. If the feature isn't simulation specific, an instance of its class and its method calls could simply be added to the Simulation superclass.

Allowing infinite grids would require using an 2 dimensional list instead of array in order to keep track of the cells. The neighbors given to cells would include a padding around the existing grid, so that each cell would have the option to move outside the existing grid. The grid would be resized in terms of dimensions if a cell decided to move outside the existing grid. The shapes corresponding to the cells would also be resized according to the size of the grid at each update.

Adding a new shape such as triangle would require adding an additional Grid subclass which dealt with the unique initialization of triangle polygons, and the unique neighbor situation of Triangles. It would also require adding new features to the style document, a process described earlier.

###  Justification of Major Design Choices

### Assumptions or Decisions To Resolve Functionality
