# cellsociety

CompSci 308 Cell Society Project

* XML files for testing can be found in docs. All cell classes are in cells, cell managing Grid class is in cellManger, main simulation classes are in simulationDrivers, resource files are in Resources, and XML reading classes are in XMLClasses.

Inheritance Review
===================
#### mr284, dh224, tjy8
Madhavi, Diane, Tyler

## Dates/Times

 1. We started on the 14th in lab when the project was assigned.
 2. We finished on the evening of the 1st around 10:30 PM.
 3. Estimated time spent: 90-100 hours total.
 
## Roles

1. Diane: for the first sprint, co-developed the backend and glue code between the backend and the front end. Specifically, created two simulations, co-wrote the grid class/cell superclass, and hooked up the front and back end. For the second sprint: did all visualization items (graph, click to change state, multiple simulations, user input thresholds). Added styling with the style file, created frontend logic for backend use of user setting configuration details (edge type, grid shape).

2. Madhavi: for the first sprint, co-developed the backend, co-wrote the grid class/cell superclass, created two simulations. Added resource files. For the second sprint: did all simulation items--added grid shapes, edge shapes, neighbor determination, and added two more simulations.

3. Tyler: for the first sprint, completed all XML reading items, wrote the front-end GUI. For the second sprint, threw all errors dialog boxes/completed error checking, wrote all additional XML reading, exporting, and configuration classes.

## Resources Used

1. Professor Duvall's code to see how resource files are used.
2. Professor Duvall's code to see how throwing error dialogue boxes works.
3. Professor Duvall's code to see how XML reading works.
4. TutorialPoint.com code to see how XML exporting works.

## Starter Files

1. Referenced Professor Duvall's code demonstrated during lab regarding XML reading and GUI basic items.

## Testing Files

1. All testing files are included in the TestFiles Package, one configuration for each simulation, and one style file. All files can be edited to change basic grid properties or style properties.

## Errors Handled

1. Pressing start before uploading configurations.
2. Uploading improper style or configuration XML files.
3. Inputting incomplete number of thresholds when setting thresholds mid-simulation.
4. Through disabling of buttons, pressing buttons in the wrong order, such as pressing step before pausing.
5. Inputting wrong or unavailable simulation type when choosing simulation.

## Required Files to Run

1. Simulations require a style file and a configuration file to run. See TestFiles for all files that can be used to run the simulation, modified to run the simulation, or used as a template to create files to run the simulation.
2. The program also requires two resource files that contain Strings for all labels, and for all XML classes. They can be found in the Resources package.

## To Run

1. Run the simulation from main.
2. Click the button that lets you choose an XML style file, and upload an appropriately formatted XML file (error will be thrown if format is improper)
3. Style file will now be stored. Type in simulation type user wants to render: Segregation, SpreadingWildfire, GameOfLife, RPS, Wator, ForagingAnts are the accepted simulation types.
4. Press create new simulation.
5. Choose an appropriate XML configuration file from the window that pops up with the simulation, using the choose XML button.
6. Click start to begin the simulation.
7. Click pause to pause, step after pause if user wants to step through the simulation, resume to resume normal updating, speed up button to accelerate the update rate, slow down button to decelerate the update rate.
8. At any point in the simulation, if the simulation is paused, user can click on the specific cells to change their state. Note: empty cells do not change state in any simulation.
9. At any point in the simulation, if the simulation has a threshold/thresholds to set, user can input thresholds in fields on right side of screen, and press submit to update the simulation thresholds. Note: all thresholds must be filled before pressing submit, otherwise error dialogue box is thrown.
10. At any point in the simulation, user can press reset to reset the simulation to a blank state, at which point the current cellular automata clears and a new XML file must be chosen. Simply opening the XML file chooser, picking another file, and pressing start, will also start a new simulation.
11. At any point, user can create another simulation from the simulation picking screen, and set it with another style file if they so choose. If no style file is reset at this point, original file is used. In this way, user can run multiple simulations side by side, independently.
12. Note: In one simulation window, user can only run simulations of the same type. If user wants to switch to another simulation type, user can go back to simulation picking screen and simply create another simulation of that type.
13. Note: certain buttons will be disabled until their prerequisite conditions have been met. For example, step cannot be used until the simulation is paused, and when resetting a new XML configuration in the same window, start will not be enabled until a proper XML file has been submitted.
14. Press save to save the current simulation state to the file called GridConfigurationExport, found in Resources. That same file can be reloaded to start the simulation again at that exact point, otherwise, it can be used simply as a reference of that point in the simulation.

## Decisions, Assumptions, and Simplifications.

1. Due to time constraints, in the RPS simulation we have not implemented dragging to set initial cell states, or gradient colors for the cells. Initial states are simply set like in any other simulation. However, since we have the functionality of user setting states mid simulation implemented, it would not be difficult to do the same for an RPS simulation at the get-go, given more time.
2. In ForagingAnts, we decided not to implement dispersion of pheromones also due to time constraints. Pheromones simply evaporate after a certain amount of time. However, since pheromones are tracked in a separate class, it should not be difficult to extend their functionality to dispersion.

## Known Bugs, Crashes, and Problems

1. The current ForagingAnts simulation does not run correctly with toroidal neighbors. It still runs but doesn't get the correct neighbors all the time. We would be able to fix the algorithm without shifting the current design, given more time to study the patterns of which cells are neighbors for an ant, when an ant faces a certain direction.
2. In the Wator simulation, sharks occasionally disappear, and sometimes the sharks don't move in the right direction if it eats a fish that moves before it does, according to the steps in which we direct how they move.

## Impressions of the Project

1. This project, while extremely interesting, was pretty heavy during the couple weeks when recruiting events also happened to be occurring. It also landed right in the middle of a time period where all group members had midterms, so all of us had to make the decision to work on completing the project instead of studying for other exams. However, overall, we found the project to provide a solidly exciting challenge for us to test the flexibilities of our design. It was also pretty rewarding to see the simulations in action once complete.