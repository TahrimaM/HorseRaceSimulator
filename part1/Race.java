import java.lang.Math;
import java.util.concurrent.TimeUnit;

public class Race {
    private int raceLength;
    private Horse[] lanes;

    public Race(int distance, int numberOfLanes) {
        raceLength = distance;
        lanes = new Horse[numberOfLanes];
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber >= 1 && laneNumber <= lanes.length) {
            lanes[laneNumber - 1] = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    public void startRace() {
        boolean finished = false;

        // reset all horses
        for (Horse horse : lanes) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }

        while (!finished) {
            raceStep();


            if (isRaceFinished()) {
                finished = true;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {}
        }
    }

    public void moveHorse(Horse theHorse) {
        if (theHorse != null && !theHorse.hasFallen()) {
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }
            if (Math.random() < (0.05 * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();
            }
        }
    }

    public boolean raceWonBy(Horse theHorse) {
        if (theHorse == null) return false;
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    public boolean isRaceFinished() {
        for (Horse horse : lanes) {
            if (raceWonBy(horse)) {
                return true;
            }
        }
        boolean allFallen = true;
        for (Horse horse : lanes) {
            if (horse != null && !horse.hasFallen()) {
                allFallen = false;
                break;
            }
        }
        if (allFallen) {
            System.out.println("All horses have fallen, so the race is finished!");
            return true;
        }
        return false;
    }

    //Removed the textual version of the game outputted
    // such as displayRace


    public void printLane(Horse theHorse) {
        if (theHorse == null) return;

        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - spacesBefore;

        System.out.print('|');
        multiplePrint(' ', spacesBefore);

        if (theHorse.hasFallen()) {
            System.out.print("❌");
            multiplePrint(' ', spacesAfter - 1);
        } else {
            System.out.print(theHorse.getSymbol());
            multiplePrint(' ', spacesAfter);
        }

        System.out.print('|');
        System.out.print(" " + theHorse.getName() + " (Confidence: " + theHorse.getConfidence() + ")");
    }

    public void raceStep() {
        for (Horse horse : lanes) {
            moveHorse(horse);
        }
    }

    public void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }
}