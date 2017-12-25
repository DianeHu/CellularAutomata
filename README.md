# cellsociety

## Required Files to Run

1. Simulations require a style file and a configuration file to run. See TestFiles for all files that can be used to run the simulation, modified to run the simulation, or used as a template to create files to run the simulation.
2. The program also requires two resource files that contain Strings for all labels, and for all XML classes. They can be found in the Resources package.

## To Run

1. Run the simulation from main.
2. Click the button that lets you choose an XML style file, and upload an appropriately formatted XML file (error will be thrown if format is improper)
3. Style file will now be stored. Type in simulation type user wants to render: Segregation, SpreadingWildfire, GameOfLife, RPS, Wator, ForagingAnts are the accepted simulation types.
4. Note: certain simulations will look strange if the style file has indicated a different neighbor type should be used than the simulation's original rules require. For example, GameOfLife looks very lackluster without using all eight neighbors. In the style file, use Rectangle/Hexagon, Toroidal/Finite, Max/Four to direct simulation properties.
5. Press create new simulation.
6. Choose an appropriate XML configuration file from the window that pops up with the simulation, using the choose XML button.
7. Click start to begin the simulation.
8. Click pause to pause, step after pause if user wants to step through the simulation, resume to resume normal updating, speed up button to accelerate the update rate, slow down button to decelerate the update rate.
9. At any point in the simulation, if the simulation is paused, user can click on the specific cells to change their state. Note: empty cells do not change state in any simulation.
10. At any point in the simulation, if the simulation has a threshold/thresholds to set, user can input thresholds in fields on right side of screen, and press submit to update the simulation thresholds. Note: all thresholds must be filled before pressing submit, otherwise error dialogue box is thrown.
11. At any point in the simulation, user can press reset to reset the simulation to a blank state, at which point the current cellular automata clears and a new XML file must be chosen. Simply opening the XML file chooser, picking another file, and pressing start, will also start a new simulation.
12. At any point, user can create another simulation from the simulation picking screen, and set it with another style file if they so choose. If no style file is reset at this point, original file is used. In this way, user can run multiple simulations side by side, independently.
13. Note: In one simulation window, user can only run simulations of the same type. If user wants to switch to another simulation type, user can go back to simulation picking screen and simply create another simulation of that type.
14. Note: certain buttons will be disabled until their prerequisite conditions have been met. For example, step cannot be used until the simulation is paused, and when resetting a new XML configuration in the same window, start will not be enabled until a proper XML file has been submitted.
15. Press save to save the current simulation state to the file called GridConfigurationExport, found in Resources. That same file can be reloaded to start the simulation again at that exact point, otherwise, it can be used simply as a reference of that point in the simulation.
