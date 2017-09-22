Inheritance Review
===================
#### mr284, dh224, nbl7, pl111
Madhavi, Diane, Nathan, Peilin

##Part 1

 1. We are hiding the Cells from the front end; only the cell manager, the Grid class, has access to the Cells and their behaviors.
 2. We're building an inheritance hierarchy of Cells, and they're based around the behavior of cells moving and interacting with their neighbors. 
 3. We're trying to make all the cell interaction and decision making information closed to the Grid class which manages instances of the Cell class, but we're leaving their locations open so that the Grid class can keep track of them.
 4. Errors might occur with collisions between old and new states; we're trying to handle them by having cells check against the future state before moving while basing their decision based off of the present state. 
 5. We think our design is good because the Grid class is very generalized, so that adding new types of Cells for different simulations would be very easy. This makes our design quite flexible.

##Part 2

 1. Our area is dependent on the XML reader since the way the backend logic is set up is based off of the initial information read from the XML file.
 2. This dependency is solely based off of the values returned by the XMLReader class; the actual implementation of the class doesn't matter.
 3.  It isn't possible to decrease the reliance on the XML reader since those values are necessary to set up the simulation.
 4. We have a cell superclass that is extended by cell subclasses from each simulation. They have basic methods in common, such as isNeighbor, but the cell subclasses override these as necessary. Since the superclass is abstract as well, it contains abstract stubs that the subclasses use. The main cell subclass method is moveCell, which each subclass overrides as necessary.

##Part 3

1. Use case one: Game of Life game, one live cell surrounded by three dead cells, live cell is a center cell. Use case two: Updating edge case of segregation unsatisfied cell (edge case = edge position). Use case three: Wator simulation shark in corner of grid. Use case four: Burning tree cell surrounded on three sides by trees, other side by empty cell. Use case five: Segregation cell surrounded by 50% same type, 50% empty.
2. We're excited to design the rules for the more complex simulations and actually see them animated, such as the rules for the Wator simulation.
3. We're worried about getting XML reading up and running right now. Currently our values are just hard-coded for testing purposes.
