import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceGUI extends JPanel {

    private Race raceManager;
    private JButton startButton;
    private JTextArea raceDisplay;
    private JFrame frame;
    private RaceTrackPanel raceTrackPanel;
    private Horse[] horses;

    private JComboBox<String> weatherComboBox;

    private String[] horseBreeds;
    private Color[] horseCoatColours;


    public RaceGUI() {
        // Ask user first
        int numberOfLanes = Integer.parseInt(JOptionPane.showInputDialog("Enter number of horses (lanes):"));
        int raceLength = Integer.parseInt(JOptionPane.showInputDialog("Enter race length:"));

        // Create horses array
        horses = new Horse[numberOfLanes];
        horseBreeds = new String[numberOfLanes];
        horseCoatColours = new Color[numberOfLanes];

        for (int i = 0; i < numberOfLanes; i++) {
            String horseName = JOptionPane.showInputDialog("Enter name for horse " + (i + 1) + ":");
            String horseBreed = JOptionPane.showInputDialog("Enter breed for horse " + (i + 1) + ":");

            String[] colourOptions = {"Black", "White", "Brown", "Grey", "Chestnut"};
            String colourChoice = (String) JOptionPane.showInputDialog(null,
                    "Choose coat colour for horse " + (i + 1) + ":",
                    "Coat Colour",
                    JOptionPane.PLAIN_MESSAGE,
                    null, colourOptions, colourOptions[0]);

            Color horseCoatColour = mapColor(colourChoice);

            String horseSymbol = JOptionPane.showInputDialog("Enter symbol (emoji or letter) for horse " + (i + 1) + ":");
            double horseConfidence = Double.parseDouble(JOptionPane.showInputDialog("Enter confidence for horse " + (i + 1) + ":"));

            horses[i] = new Horse(horseSymbol.charAt(0), horseName, horseConfidence);
            horseBreeds[i] = horseBreed;
            horseCoatColours[i] = horseCoatColour;
        }

        // Now create the raceManager AFTER we know number of horses
        raceManager = new Race(raceLength, numberOfLanes);

        // Add horses to the race
        for (int i = 0; i < numberOfLanes; i++) {
            raceManager.addHorse(horses[i], i + 1);
        }

        // Setup frame
        frame = new JFrame("My Little Pony");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setBackground(new Color(60, 179, 113));

        raceTrackPanel = new RaceTrackPanel();
        frame.add(raceTrackPanel, BorderLayout.CENTER);

        String[] weatherOptions = {"Dry", "Muddy", "Icy"};
        weatherComboBox = new JComboBox<>(weatherOptions);
        weatherComboBox.setSelectedIndex(0); // Default to "Dry"

        JPanel weatherPanel = new JPanel();
        weatherPanel.add(new JLabel("Select Weather:"));
        weatherPanel.add(weatherComboBox);

        frame.add(weatherPanel, BorderLayout.NORTH);

        // Start button
        startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the selected weather in the raceManager before starting the race
                String selectedWeather = (String) weatherComboBox.getSelectedItem();
                raceManager.setWeather(selectedWeather);

                // Show weather impact pop-up
                String message = "";
                if (selectedWeather.equals("Dry")) {
                    message = "The weather is dry, so the horses will be able to move freely.";
                }
                else if (selectedWeather.equals("Muddy")){
                    message = "The weather is muddy, so the horses will fall more easily and are less confident.";
                }
                else if (selectedWeather.equals("Icy")) {
                    message = "The weather is icy, so the horses will slip more easily and are very unconfident.";
                }
                JOptionPane.showMessageDialog(frame,
                        message,
                        "Weather Selected",
                        JOptionPane.INFORMATION_MESSAGE);

                startRace();
            }
        });
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setSize(1000, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private class RaceTrackPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set the green background to cover only the track length
            int trackLength = raceManager.getRaceLength(); // Get race length from the race manager
            g.setColor(new Color(60, 179, 113)); // Green color for the race track background
            g.fillRect(0, 0, trackLength * 30, getHeight()); // Adjust width based on race length

            // Draw lanes based on number of horses
            g.setColor(Color.WHITE);
            for (int i = 0; i < horses.length; i++) {
                int laneY = (i + 1) * getHeight() / (horses.length + 1); // evenly space out
                g.drawLine(0, laneY, getWidth(), laneY);
            }

            // Draw horses
            for (int i = 0; i < horses.length; i++) {
                drawHorse(g, horses[i], i + 1);
            }
        }

        private void drawHorse(Graphics g, Horse horse, int lane) {
            int x = horse.getDistanceTravelled() * 30; // Scale horizontal position
            int laneY = lane * getHeight() / (horses.length + 1); // Match same lane spacing
            int y = laneY - 20; // slightly above the lane line

            g.setColor(horseCoatColours[lane-1]);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(String.valueOf(horse.getSymbol()), x, y);
        }
    }

    private Color mapColor(String colourName) {
        switch (colourName) {
            case "Black":
                return Color.BLACK;
            case "White":
                return Color.WHITE;
            case "Brown":
                return new Color(86, 40, 6); // a nice brown
            case "Grey":
                return Color.GRAY;
            case "Chestnut":
                return new Color(182, 90, 20); // chestnut color
            default:
                return Color.BLACK; // fallback
        }
    }

    private void startRace() {
        startButton.setEnabled(false); // Disable the button during the race


        // Use a Swing Timer for the race animation
        final Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRaceFinished()) {
                    raceManager.raceStep();      // ⬅ one move per timer tick
                    raceTrackPanel.repaint();    // Only repaint the race track
                } else {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(true);
                    displayWinner();
                }
            }
        });
        timer.start();
    }

    private boolean isRaceFinished() {
        return raceManager.isRaceFinished();
    }

    private void displayWinner() {
        for (int i = 0; i < horses.length; i++) {
            if (raceManager.raceWonBy(horses[i])) {
                JOptionPane.showMessageDialog(frame, "The winner is " + horses[i].getName() + " the " + horseBreeds[i] + "!");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "All horses have fallen!");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RaceGUI());
    }
}

