import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane for a given distance
 *
 * @author McRaceface
 * @version 1.0
 */
public class RaceTextual {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class RaceTextual
     * Initially, there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in meters/yards...)
     */
    public RaceTextual(int distance) {
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse   the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber == 1) {
            lane1Horse = theHorse;
        } else if (laneNumber == 2) {
            lane2Horse = theHorse;
        } else if (laneNumber == 3) {
            lane3Horse = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horses are brought to the start and then repeatedly moved forward until the race is finished
     */
    public void startRace() {
        boolean finished = false;

        // Reset all lanes (all horses are not fallen and back to 0)
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        while (!finished) {
            // Move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);

            // Print the race positions
            printRace();

            // If any horse has won, the race is finished
            if (raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse)) {
                finished = true;
            }

            // If all the horses fall, the race is finished
            if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
                finished = true;
                System.out.println("All horses have fallen, so the race is finished");
            }

            // Wait for 100 milliseconds
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
            }
        }

        // After the race finishes, check and announce the winner
        if (raceWonBy(lane1Horse)) {
            System.out.println("And the winner is: " + lane1Horse.getName() + "!");
        } else if (raceWonBy(lane2Horse)) {
            System.out.println("And the winner is: " + lane2Horse.getName() + "!");
        } else if (raceWonBy(lane3Horse)) {
            System.out.println("And the winner is: " + lane3Horse.getName() + "!");
        }
    }

    /**
     * Randomly make a horse move forward or fall depending on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        // If the horse has fallen, it cannot move
        if (!theHorse.hasFallen()) {
            // The probability that the horse will move forward depends on its confidence
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }

            // The probability that the horse will fall is very small, but depends on confidence
            if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
                if (Math.random() < (0.05 * theHorse.getConfidence() * theHorse.getConfidence())) {
                    theHorse.fall();
                }
            }
        }
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() == raceLength;
    }

    /**
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C'); // Clear the terminal window

        multiplePrint('=', raceLength + 3); // Top edge of track
        System.out.println();

        printLane(lane1Horse);
        System.out.println();

        printLane(lane2Horse);
        System.out.println();

        printLane(lane3Horse);
        System.out.println();

        multiplePrint('=', raceLength + 3); // Bottom edge of track
        System.out.println();
    }

    /**
     * Print a horse's lane during the race, showing how far the horse has run
     * Example: |           X                      |
     *
     * @param theHorse the horse to print
     */
    private void printLane(Horse theHorse) {
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - spacesBefore;

        System.out.print('|'); // Start of lane

        multiplePrint(' ', spacesBefore); // Spaces before the horse

        // If the horse has fallen, print a fallen marker (❌)
        if (theHorse.hasFallen()) {
            System.out.print("❌");
            multiplePrint(' ', spacesAfter - 1); // Adjust space after
        } else {
            System.out.print(theHorse.getSymbol());
            multiplePrint(' ', spacesAfter); // Spaces after the horse
        }

        System.out.print('|'); // End of lane

        // Print the name and confidence of the horse
        System.out.print(" " + theHorse.getName() + " (Confidence: " + theHorse.getConfidence() + ")");
    }

    /**
     * Print a character a given number of times
     *
     * @param aChar the character to print
     * @param times the number of times to print the character
     */
    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }
}