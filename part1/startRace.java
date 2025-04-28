public class startRace {
    public static void main(String[] args) {
        // Create horses
        Horse horse1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.9);
        Horse horse2 = new Horse('♞', "KOKOMO", 0.85);
        Horse horse3 = new Horse('♕', "EL JEFE", 0.95);

        // Create a race with a distance of 20 meters
        RaceTextual race = new RaceTextual(20);

        // Add horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        race.startRace();
    }
}