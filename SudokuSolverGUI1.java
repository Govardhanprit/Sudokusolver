import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class SudokuSolverGUI1 extends JFrame {
    private JTextField[][] cells;
    private JButton solveButton;
    private JButton resetButton;

    public SudokuSolverGUI1() {
        setTitle("SUDOKU SOLVER");
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        ImageIcon image = new ImageIcon("/Users/govardhanprit/Desktop/Sudoku Final/logo4.png"); //create an image icon
        setIconImage(image.getImage()); //change the icon


        JPanel mainPanel = new JPanel(new GridLayout(9, 9));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(new Dimension(400, 400));
        cells = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField(1);
                cells[i][j].setBackground(new Color(147,112,219)); // cell colour
                cells[i][j].setFont(new Font("Monaco", Font.BOLD, 21));
                cells[i][j].setForeground(new Color(75,0,130)); // number colour
                cells[i][j].setHorizontalAlignment(JTextField.CENTER); // Set horizontal alignment to center
                Border thickBorder = new MatteBorder(
                    i % 3 == 0 ? 3 : 1, j % 3 == 0 ? 3 : 1,
                    (i == 8) ? 3 : 0, (j == 8) ? 3 : 0,
                    new Color(255,255,255)
            );

            // Set the matte border for the cell
            cells[i][j].setBorder(thickBorder);
                mainPanel.add(cells[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(75,0,130));
        solveButton = new JButton("SOLVE");
        solveButton.setFont(new Font("Monaco", Font.ITALIC, 18));
        solveButton.setForeground(new Color(75,0,130));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        resetButton = new JButton("RESET");
        resetButton.setFont(new Font("Monaco", Font.ITALIC, 18));
        resetButton.setForeground(new Color(75,0,130));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSudoku();
            }
        });
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(solveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); //Add space between Solve and Reset buttons
        buttonPanel.add(resetButton);
        
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void solveSudoku() {
        int[][] board = new int[9][9];
        boolean isValidInput = true;

        // Read values from the text fields and validate input
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText().trim();
                if (!text.isEmpty()) {
                    int value;
                    try {
                        value = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        // Invalid input (non-numeric value)
                        JOptionPane.showMessageDialog(this, "Invalid input: Enter only numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                        isValidInput = false;
                        break;
                    }
                    if (value < 1 || value > 9) {
                        // Invalid input (out of range)
                        JOptionPane.showMessageDialog(this, "Invalid input: Enter numbers between 1 and 9.", "Error", JOptionPane.ERROR_MESSAGE);
                        isValidInput = false;
                        break;
                    }
                    board[i][j] = value;
                }
            }
        }

        // If input is valid, check if it is a valid Sudoku puzzle
        if (isValidInput && !isValidSudoku(board)) {
            JOptionPane.showMessageDialog(this, "Invalid Sudoku puzzle: The input contains repeated numbers in rows, columns, or 3x3 boxes.", "Error", JOptionPane.ERROR_MESSAGE);
            isValidInput = false;
        }

        if (isValidInput && solve(board)) {
            updateUI(board);
        }
    }

    private boolean isValidSudoku(int[][] board) {
        // Check each row
        for (int i = 0; i < 9; i++) {
            if (!isValidRow(board, i)) {
                return false;
            }
        }

        // Check each column
        for (int j = 0; j < 9; j++) {
            if (!isValidColumn(board, j)) {
                return false;
            }
        }

        // Check each 3x3 box
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!isValidBox(board, i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidRow(int[][] board, int row) {
        boolean[] visited = new boolean[10]; // To keep track of visited numbers (1-9)
        for (int i = 0; i < 9; i++) {
            int num = board[row][i];
            if (num != 0 && visited[num]) {
                return false; // If number is already visited, it's repeated
            }
            visited[num] = true;
        }
        return true;
    }

    private boolean isValidColumn(int[][] board, int col) {
        boolean[] visited = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int num = board[i][col];
            if (num != 0 && visited[num]) {
                return false;
            }
            visited[num] = true;
        }
        return true;
    }

    private boolean isValidBox(int[][] board, int startRow, int startCol) {
        boolean[] visited = new boolean[10];
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int num = board[i][j];
                if (num != 0 && visited[num]) {
                    return false;
                }
                visited[num] = true;
            }
        }
        return true;
    }

    private void resetSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
            }
        }
    }

    private void updateUI(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(Integer.toString(board[i][j]));
            }
        }
    }

    public static boolean solve(int[][] board) {
        int[] emptyCell = findEmptyCell(board);
        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(board, row, col, num)) {
                board[row][col] = num;

                if (solve(board)) {
                    return true;
                }

                board[row][col] = 0;
            }
        }
        return false;
    }

    private static int[] findEmptyCell(int[][] board) {
        int[] cell = new int[2];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    cell[0] = i;
                    cell[1] = j;
                    return cell;
                }
            }
        }
        return null;
    }

    private static boolean isValidMove(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI1::new);
    }
}