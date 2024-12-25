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
