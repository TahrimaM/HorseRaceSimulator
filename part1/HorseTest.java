public class HorseTest {
        public static void main(String[] args) {
            // Create a new horse
            Horse horse = new Horse('♘', "PIPPI LONGSTOCKING", 0.85);

            // Print initial state
            System.out.println("Name: " + horse.getName()); // Should be PIPPI LONGSTOCKING
            System.out.println("Symbol: " + horse.getSymbol()); // Should be ♘
            System.out.println("Confidence: " + horse.getConfidence()); // Should be 1 after giving an message
            System.out.println("Distance Travelled: " + horse.getDistanceTravelled()); // Should be 0
            System.out.println("Has Fallen: " + horse.hasFallen()); // Should be false

            // Move forward
            horse.moveForward();
            horse.moveForward();
            System.out.println("Distance Travelled after moving: " + horse.getDistanceTravelled()); // Should be 2

            // Set confidence
            horse.setConfidence(0.95);
            System.out.println("Updated Confidence: " + horse.getConfidence()); // Should be 0.95

            // Set new symbol
            horse.setSymbol('♞');
            System.out.println("Updated Symbol: " + horse.getSymbol()); // Should be ♞

            // Make the horse fall
            horse.fall();
            System.out.println("Has Fallen after fall(): " + horse.hasFallen()); // Should be true
            horse.setSymbol('❌');
            System.out.println("Updated Symbol: " + horse.getSymbol());

            // Go back to start
            horse.goBackToStart();
            System.out.println("Distance after goBackToStart: " + horse.getDistanceTravelled()); // Should be 0
            System.out.println("Has Fallen after goBackToStart: " + horse.hasFallen()); // Should be false
        }
}
