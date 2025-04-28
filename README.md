# Horse Race Simulator

This project is a simulation of a horse race, with both a textual and graphical version. It consists of two main parts:

Part 1: The textual version of the simulator, which runs in the command line.

Part 2: The graphical version of the simulator, which uses a GUI interface.

The program simulates a race between three horses, with each horse having its own lane and moving based on its "confidence" rating.

## Table of Contents
1. [Project Setup/Usage Instructions](#project-setup-instructions)
2. [Dependencies & Installation](#dependencies--installation)
3. [File Summary](#file-summary)

---

## Project Setup/Usage Instructions

To run the project locally on your machine, follow these instructions:

1. **Clone the repository:**
   If you haven't cloned the repository, open your terminal (or PowerShell) and run the following command:
   
   ```bash
   git clone https://github.com/yourusername/HorseRaceSimulator.git

2. **Navigate to the project directory:**

      `cd HorseRaceSimulator`

5. **Compile the code**

   
      `javac Part1/*.java Part2/*.java`

      This will compile all the necessary Java files in the Part1 and Part2 directories.

4. **Running the simulations**
      To run the **Textual** version
   Make sure you have the necessary files from the above commands.
   From the terminal, run the startRace method in the Part1 folder:

      `java Part1.RaceTextual`

   To run the **Graphical** version
      Make sure you have the necessary files from the above commands.
      From the terminal, run the startRaceGUI method in the Part2 folder:

     `java Part2.RaceGUI`

5. **How to Invoke Methods**
   Textual version: The race is started by calling the startRace method, which is just the Main method located in the Part1 folder as startRace.
   
   Graphical version: The race is started by calling the startRace method located in the Part2 folder, inside the RaceTextual.java file. 
   
   Both versions are self-contained and do not require additional setup once compiled, inside RaceGUI.java file.


## Dependencies & Installation
   Before running the simulator, make sure you have the following:
   
   
   - **Java 8+** installed on your system.
     - You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use a package manager (e.g., `apt`, `brew`).
     - You can check if you have the correct version by writing in your terminal:
    
       `java -version`

        If not installed, use the website above to install it.
     
   - A **command-line interface** (CLI) to run Java programs, such as Terminal (macOS/Linux) or Command Prompt/PowerShell (Windows).
   
   My code is IDE-agnostic, meaning it can run in any IDE and the terminal. 
   
## File Summary
   ```
   ├── Part1/
   │   ├── RaceTextual.java      # Manages the logic for the textual race simulation.
   │   ├── Horse.java            # Represents individual horses, their attributes, and behaviour.
   │   ├── Race.java             # Handles the race logic, including race progress and determining winners.
   │   └── startRace            # Main method to start the textual race simulation.
   │
   ├── Part2/
   │   ├── RaceGUI.java          # Manages the GUI for the graphical race simulation.
   │   ├── RaceTrackPanel.java   # Helper class for drawing the race track and horses.
   │   ├── Horse.java            # Represents individual horses, similar to Part 1, but used in the GUI.
   │   ├── Race.java             # The race logic for the graphical version of the race.
   │   └── startRaceGUI          # Main method to initialize and start the graphical race simulation.
   │
   ├── README.md

