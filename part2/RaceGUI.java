import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A three-horse race GUI, each horse running in its own lane
 * for a given distance
 */
public class RaceGUI extends JPanel  {

    private Race raceManager;
    private JButton startButton;
    private JTextArea raceDisplay; // To show the race progress
    private JFrame frame;
    private RaceTrackPanel raceTrackPanel;

    private Horse[] horses;

    // Ask user for number of lanes and race length
    int numberOfLanes = Integer.parseInt(JOptionPane.showInputDialog("Enter number of horses (lanes):"));
    int raceLength = Integer.parseInt(JOptionPane.showInputDialog("Enter race length:"));

    public RaceGUI() {
        raceManager = new Race(raceLength);
        horses = new Horse[numberOfLanes];

        for (int i = 0; i < numberOfLanes; i++) {
            String horseName = JOptionPane.showInputDialog("Enter name for horse " + (i + 1) + ":");
            String horseSymbol = JOptionPane.showInputDialog("Enter symbol for horse " + (i + 1) + ":");
            double horseConfidence = Double.parseDouble(JOptionPane.showInputDialog("Enter confidence for horse " + (i + 1) + ":"));

            horses[i] = new Horse(horseSymbol.charAt(0), horseName, horseConfidence);
            raceManager.addHorse(horses[i], i + 1);
        }


        // Initialize race manager





        // Set up the frame
        frame = new JFrame("My Little Pony");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Make it aesthetic
        frame.setBackground(new Color(60, 179, 113));

        raceTrackPanel = new RaceTrackPanel();
        frame.add(raceTrackPanel, BorderLayout.CENTER);

        // Set up the race display area
        raceDisplay = new JTextArea(10, 40); // Rows, Columns
        raceDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceDisplay); // Add scrollbars
        frame.add(scrollPane, BorderLayout.CENTER);

        // Set up the start button
        startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRace();
            }
        });
        frame.add(startButton, BorderLayout.SOUTH);

        // Set up the panel for instructions
        JPanel instructionPanel = new JPanel();
        instructionPanel.add(new JLabel("Click 'Start Race' to begin!"));

        instructionPanel.setBackground(new Color(210, 113, 113)); // Light baby pink
        frame.add(instructionPanel, BorderLayout.NORTH);

        // Set gui width and height
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the window
    }


    private class RaceTrackPanel extends JPanel {
        @Override

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(new Color(60, 179, 113)); // Green field

            // Draw lanes
            g.setColor(Color.WHITE);
            for (int i = 1; i <= numberOfLanes; i++) {
                g.drawLine(0, i * 100, getWidth(), i * 100);
            }

            // Draw horses (as rectangles or emojis)
            for (int i = 0; i < horses.length; i++) {
                drawHorse(g, horses[i], i + 1);
            }
        }

        private void drawHorse(Graphics g, Horse horse, int lane) {
            int x = horse.getDistanceTravelled() * 30; // Scale horizontal position
            int y = lane * 100 - 40; // Y offset for vertical spacing

            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(horse.getSymbol() + " " + horse.getName(), x, y);
        }
    }

    private void startRace() {
        startButton.setEnabled(false); // Disable the button during the race
        raceDisplay.setText(""); // Clear any previous race display

        // Use a Swing Timer for the race animation
        final Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRaceFinished()) {
                    raceManager.raceStep();      // ⬅ one move per timer tick
                    updateDisplay();             // update visuals
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

        raceTrackPanel.repaint(); // <-- this is key
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RaceGUI();
            }
        });
    }
}

