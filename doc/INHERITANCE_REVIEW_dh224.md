Inheritance Review
===================
#### dh224
Diane Hu

##Part 1

 1. While each cell type contains a method isNeighbor that can check if a specific cell is its neighbor, depending on its definition of neighbor (4 or 8 surrounding), overall, the cell's neighbors are set by Grid in an arraylist. This means that each cell only has access to what Grid determines as its neighbors, and does not have any excess information about the rest of the grid.
 2. Our main inheritance feature is the cells. We have a cell abstract superclass that contains some abstract behavior method stubs each type of cell should have, and also some other general shared features. Our subclasses are extended based on behavior specific to each type. For example, a SchellingCell has no need to burn, but TreeCells do, therefore, based on this distinct behavior, our cell subclasses extend the superclass.
 3. The information encapsulated in Grid is closed to the cells--the cells only get the information they need about what their neighbors are, and act based on what Grid gives them. Grid only has to call moveCell to define the next turn's behavior, since the polymorphism we are creating in the cell subclasses involves a method moveCell that each subclass overrides from the superclass, that holds all the logic of their behavior (as aided by helper methods that define aspects of this behavior). moveCell is thus a public method, while its implementation in each subclass is private.
 4. Errors might occur when the old grid state and the "next turn" grid state collide, as in, say, if two cells try to move into the same location. We're currently trying to handle this within the cell subclasses, by checking against grid locations of the next-turn grid to make sure no collisions occur.
 5. We think our design is good by the definition of the open-closed principle--if we wanted to add a simulation it shouldn't be too difficult to do so, since all we would be adding would be cell subclasses. Grid is very generalized, and defines move logic only based on the moveCell call, and therefore should really not need much updating in order to run a new simulation type. Thus, our design is pretty closed to modification, but easily extendable with cell subclasses.
 
##Part 2

 1. Our area is dependent on XML reading. We need the XML reader to set the initial configuration of the grid, and the grid size. Otherwise, grid and cell and pretty contained within themselves.
 2. No. The implementation of the XML reading doesn't matter for us; we just need the values read from the XML file and to make the call to set the initial configuration. How XML reader does that is outside the scope of these classes' concern.
 3. Since our dependencies are solely based on value reading, and not on implementation, there's not much leeway with minimizing this sort of dependency. We need these values to start the simulation, if we don't want to hardcode anything.
 4. We have a cell superclass that is abstract and contains methods shared by our cell subclasses, such as moveCell and isNeighbor. Our cell subclasses extend the superclass as necessary based on their differentiable behavior, and override the superclass methods to define the logic of their own move behavior. We have a few methods in the superclass right now that don't pertain to all cells, but do to multiple types in single simulations, and in the future we could possibly move these into the subclasses themselves, but otherwise the main hierarchical benefit is being able to call moveCell for any cell, and have it define its own moving logic.

##Part 3

1. Use case one: Game of Life game, one live cell surrounded by three dead cells, live cell is a center cell. Use case two: Updating edge case of segregation unsatisfied cell (edge case = edge position). Use case three: Wator simulation shark in corner of grid. Use case four: Burning tree cell surrounded on three sides by trees, other side by empty cell. Use case five: Segregation cell surrounded by 50% same type, 50% empty.
2. We're excited to work on the more difficult simulation rules, and to actually see them animated successfully.
3. We're slightly worried about getting XML reading to conform with all simulation types efficiently. We currently have XML reading capabilities, it's just a matter of design on how we want to be able to use it for all simulations effectively without, for example, too many switch statements.
