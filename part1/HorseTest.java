public class HorseTest {
        public static void main(String[] args) {
            // Create a new horse
            HorseSRC horse1 = new HorseSRC('♘', "PIPPI LONGSTOCKING", 0.9);
            HorseSRC horse2 = new HorseSRC('♞', "KOKOMO", 0.85);
            HorseSRC horse3 = new HorseSRC('♕', "EL JEFE", 0.95);

            Race race = new Race(20);
            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);

            race.startRace();

        }
}
