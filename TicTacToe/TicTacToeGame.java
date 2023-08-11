package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TicTacToeGame extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private int currentPlayer;
    private String[] playerNames;
    private JLabel currentPlayerLabel;
    private Map<String, Integer> playerWins;
    private int numDraws;
    private JButton resultButton;

    public TicTacToeGame(int numPlayers, String[] names) {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentPlayer = 0;
        playerNames = names;
        playerWins = new HashMap<>();
        numDraws = 0; 

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }

        add(gamePanel);

        currentPlayerLabel = new JLabel("Current Player: " + playerNames[currentPlayer]);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(currentPlayerLabel, BorderLayout.NORTH);

        resultButton = new JButton("Result");
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayResults();
            }
        });
        add(resultButton, BorderLayout.SOUTH);

        setSize(300, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().equals("")) {
            return;
        }

        if (currentPlayer == 0) {
            clickedButton.setText("X");
        } else {
            clickedButton.setText("O");
        }

        if (checkForWin()) {
            String winnerName = playerNames[currentPlayer];
            JOptionPane.showMessageDialog(this, "Player " + winnerName + " wins!");
            updatePlayerStats(winnerName);
            resetGame();
        } else if (checkForDraw()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            numDraws++;
            resetGame();
        } else {
            currentPlayer = (currentPlayer + 1) % playerNames.length;
            currentPlayerLabel.setText("Current Player: " + playerNames[currentPlayer]);
        }
    }

    private void updatePlayerStats(String winnerName) {
        playerWins.put(winnerName, playerWins.getOrDefault(winnerName, 0) + 1);
    }

   private boolean checkForWin() {
    // Check for horizontal wins
    for (int i = 0; i < 3; i++) {
        if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
            buttons[i][0].getText().equals(buttons[i][2].getText()) &&
            !buttons[i][0].getText().equals("")) {
            return true;
        }
    }

    // Check for vertical wins
    for (int i = 0; i < 3; i++) {
        if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
            buttons[0][i].getText().equals(buttons[2][i].getText()) &&
            !buttons[0][i].getText().equals("")) {
            return true;
        }
    }

    // Check for diagonal wins
    if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
        buttons[0][0].getText().equals(buttons[2][2].getText()) &&
        !buttons[0][0].getText().equals("")) {
        return true;
    }
    if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
        buttons[0][2].getText().equals(buttons[2][0].getText()) &&
        !buttons[0][2].getText().equals("")) {
        return true;
    }

    return false;
}

private boolean checkForDraw() {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (buttons[i][j].getText().equals("")) {
                return false; // If any cell is empty, the game is not a draw
            }
        }
    }
    return true; // All cells are filled, it's a draw
}

private void resetGame() {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            buttons[i][j].setText(""); // Clear the button labels
        }
    }
    currentPlayer = 0; // Reset the current player to the first player
    currentPlayerLabel.setText("Current Player: " + playerNames[currentPlayer]);
}

    private void displayResults() {
        StringBuilder resultMessage = new StringBuilder();
        for (String playerName : playerNames) {
            int wins = playerWins.getOrDefault(playerName, 0);
            resultMessage.append(playerName).append(": ").append(wins).append(" wins\n");
        }
        resultMessage.append("Draws: ").append(numDraws);

        JOptionPane.showMessageDialog(this, resultMessage.toString(), "Match Results", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
    final int[] numPlayers = new int[1];  // Create an array to hold the value

    do {
        try {
            numPlayers[0] = Integer.parseInt(JOptionPane.showInputDialog("Enter number of players (2 only):"));
        } catch (NumberFormatException e) {
            numPlayers[0] = -1;
        }
    } while (numPlayers[0] != 2);

    String[] playerNames = new String[numPlayers[0]];

    for (int i = 0; i < numPlayers[0]; i++) {
        playerNames[i] = JOptionPane.showInputDialog("Enter name for Player " + (i + 1) + ":");
    }

    SwingUtilities.invokeLater(() -> new TicTacToeGame(numPlayers[0], playerNames));
}
}