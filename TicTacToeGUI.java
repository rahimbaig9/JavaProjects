import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private char currentPlayer;

    public TicTacToeGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        buttons = new JButton[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

  private void initializeBoard() {
    frame.setLayout(new GridLayout(3, 3));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Font buttonFont = new Font("Arial", Font.BOLD, 80);

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            buttons[i][j] = new JButton();
            buttons[i][j].setFont(buttonFont);
            buttons[i][j].setFocusPainted(false);
            buttons[i][j].addActionListener(new ButtonClickListener(i, j));
            frame.add(buttons[i][j]);
        }
    }

    frame.setSize(300, 300);
    frame.setVisible(true);
}


   private void makeMove(int row, int col) {
    if (buttons[row][col].getText().isEmpty()) {
        buttons[row][col].setText("<html><font size='6'>" + currentPlayer + "</font></html>");
        if (checkWin(row, col)) {
            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetBoard();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }
}


    private boolean checkWin(int row, int col) {
        String symbol = String.valueOf(currentPlayer);

        // Check row
        if (buttons[row][(col + 1) % 3].getText().equals(symbol) && buttons[row][(col + 2) % 3].getText().equals(symbol)) {
            return true;
        }

        // Check column
        if (buttons[(row + 1) % 3][col].getText().equals(symbol) && buttons[(row + 2) % 3][col].getText().equals(symbol)) {
            return true;
        }

        // Check diagonals
        if (row == col && buttons[(row + 1) % 3][(col + 1) % 3].getText().equals(symbol) &&
                buttons[(row + 2) % 3][(col + 2) % 3].getText().equals(symbol)) {
            return true;
        }

        if (row + col == 2 && buttons[(row + 1) % 3][(col - 1 + 3) % 3].getText().equals(symbol) &&
                buttons[(row + 2) % 3][(col - 2 + 3) % 3].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setText("");
            }
        }
        currentPlayer = 'X';
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TicTacToeGUI();
        });
    }
}
