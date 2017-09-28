Inheritance Review
===================
#### mr284, dh224, tjy8
Madhavi, Diane, Tyler

## Duplication refactoring

 1. We decided to refactor out the duplicated code where in some of the cell subclasses we instantiate different cell types to model the simulation behavior. We decided to work on this part specifically because we know it will only happen more times, given
 more simulations, that we need to create new cells. To resolve the duplicated code, we're creating a new method that creates any type of cell to replace the current cell,
 and we'll call this method as necessary in place of the current code.
 
 2. We also decided to remove some of the duplicated code in our segregation cells that determines their move behavior based on their satisfied threshold. We're
 going to pull out the code within those methods that is the same, and move it into the superclass so that both can call it. We decided to refactor this portion because
 it's likely with more simulations, we'll need a similar such method anyways that determines generalized behavior based on thresholds. With this bit pulled out, we could
 edit that, or override it, to drive more simulations.
 
 ## Alternatives considered
 
 1. For the segregation cells, we considered simply moving the methods entirely into the cell superclass, but ended up deciding against it since
 it's not a method used by all subclasses. We agreed that superclasses should, as much as possible, contain only methods that all cells/most cells use,
 and since in this case these methods apply only to two subclasses, we decided against moving it up.
 
 ## Checklist refactoring
 
 1. We decided to make all our instantiations of ArrayLists simply Lists. This provides greater flexibility in passing objects, and makes it so that
 we can easily change the list type. There's not really another alternative here--either they're concrete lists, or they're not, and we thought that making them
 generalized Lists would make changing them into, say, LinkedLists, in the future easier.
 
 2. We decided to take out the isNeighbor method from the cells entirely, and to give that control over to Grid. Because neighbor is no longer determined
 by the simulation, but rather by the user, it makes more sense to put the method into a higher managing class, so that neighbor type is no longer tied with specific
 simulation type.