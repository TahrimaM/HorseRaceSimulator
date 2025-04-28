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

        // Text display for printed race track
        raceDisplay = new JTextArea(10, 40);
        raceDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceDisplay);
        frame.add(scrollPane, BorderLayout.EAST);

        // Start button
        startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRace();
            }
        });
        frame.add(startButton, BorderLayout.SOUTH);

        JPanel instructionPanel = new JPanel();
        instructionPanel.add(new JLabel("Click 'Start Race' to begin!"));
        instructionPanel.setBackground(new Color(210, 113, 113));
        frame.add(instructionPanel, BorderLayout.NORTH);

        frame.setSize(1000, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private class RaceTrackPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(60, 179, 113)); // green background

            g.setColor(Color.WHITE);
            for (int i = 1; i <= horses.length; i++) {
                int laneY = i * getHeight() / (horses.length + 1);
                g.drawLine(0, laneY, getWidth(), laneY);
            }

            for (int i = 0; i < horses.length; i++) {
                drawHorse(g, horses[i], i + 1);
            }
        }

        private void drawHorse(Graphics g, Horse horse, int lane) {
            int x = horse.getDistanceTravelled() * 30; // scaling
            int laneY = lane * getHeight() / (horses.length + 1);
            int y = laneY - 20;

            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(horse.getSymbol() + " " + horse.getName(), x, y);
        }
    }

    private void startRace() {
        startButton.setEnabled(false);
        raceDisplay.setText("");

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRaceFinished()) {
                    raceManager.raceStep();
                    updateDisplay();
                } else {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(true);
                    displayWinner();
                }
            }
        });
        timer.start();
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();

        java.io.PrintStream originalOut = System.out;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(baos));

        raceManager.printRace();

        System.setOut(originalOut);
        String output = baos.toString();
        sb.append(output);

        raceDisplay.setText(sb.toString());
        raceTrackPanel.repaint();
    }

    private boolean isRaceFinished() {
        for (Horse horse : horses) {
            if (raceManager.raceWonBy(horse)) {
                return true;
            }
        }
        return false;
    }

    private void displayWinner() {
        for (Horse horse : horses) {
            if (raceManager.raceWonBy(horse)) {
                JOptionPane.showMessageDialog(frame, "The winner is " + horse.getName() + "!");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "All horses have fallen!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RaceGUI());
    }
}

