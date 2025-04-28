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

    // 3 15 Q Q 0.4 W W 0.6 E E 0.7 sunny vs muddy

    public RaceGUI() {
        // Ask user first
        int numberOfLanes = Integer.parseInt(JOptionPane.showInputDialog("Enter number of horses (lanes):"));
        int raceLength = Integer.parseInt(JOptionPane.showInputDialog("Enter race length:"));

        // Create horses array
        horses = new Horse[numberOfLanes];

        for (int i = 0; i < numberOfLanes; i++) {
            String horseName = JOptionPane.showInputDialog("Enter name for horse " + (i + 1) + ":");
            String horseSymbol = JOptionPane.showInputDialog("Enter symbol for horse " + (i + 1) + ":");
            double horseConfidence = Double.parseDouble(JOptionPane.showInputDialog("Enter confidence for horse " + (i + 1) + ":"));

            horses[i] = new Horse(horseSymbol.charAt(0), horseName, horseConfidence);
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

        String[] weatherOptions = {"Dry", "Muddy"};
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

            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(String.valueOf(horse.getSymbol()), x, y);
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
        for (Horse horse : horses) {
            if (raceManager.raceWonBy(horse)) {
                JOptionPane.showMessageDialog(frame, "The winner is " + horse.getName() + "!");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "All horses has fallen!");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RaceGUI());
    }
}

