public class HorseTest {
        public static void main(String[] args) {
            // Create a new horse
            Horse horse = new Horse('♘', "TAHRIMAS HORSE", 1.0000001);

            // Print initial state
            System.out.println("Name: " + horse.getName()); // Should be TAHRIMAS HORSE
            System.out.println("Symbol: " + horse.getSymbol()); // Should be ♘
            System.out.println("Confidence: " + horse.getConfidence()); // Should be 1
            System.out.println("Distance Travelled: " + horse.getDistanceTravelled()); // Should be 0
            System.out.println("Has Fallen: " + horse.hasFallen()); // Should be false

            // Move forward
            horse.moveForward();
            System.out.println("Distance Travelled after moving: " + horse.getDistanceTravelled()); // Should be 1

            // Set confidence
            horse.setConfidence(5);
            System.out.println("Updated Confidence: " + horse.getConfidence()); // Should be 1 and show error message

            // Set new symbol
            horse.setSymbol('♞');
            System.out.println("Updated Symbol: " + horse.getSymbol()); // Should be ♞

            // Horse has not fallen, check if still false
            System.out.println("Has Fallen: " + horse.hasFallen()); // Should be true
            System.out.println("Updated Symbol: " + horse.getSymbol());

            // Go back to start
            horse.goBackToStart();
            System.out.println("Distance after goBackToStart: " + horse.getDistanceTravelled()); // Should be 0
            System.out.println("Has Fallen after goBackToStart: " + horse.hasFallen()); // Should be false
        }
}
