package admin;

import javax.swing.*;
import java.awt.*;

public class LoginManagement extends JFrame {
    public LoginManagement() {
        setTitle("Quản lý đăng nhập");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginListPanel.add(new JLabel("Thời gian"), gbc);
        gbc.gridx = 1;
        loginListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 2;
        loginListPanel.add(new JLabel("Họ tên"), gbc);

        for (int i = 1; i <= 30; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel timeLabel = new JLabel("2024-11-18 10:00:0" + i);
            timeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            loginListPanel.add(timeLabel, gbc);

            gbc.gridx = 1;
            JLabel usernameLabel = new JLabel("user" + i);
            usernameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            loginListPanel.add(usernameLabel, gbc);

            gbc.gridx = 2;
            JLabel fullNameLabel = new JLabel("Họ tên " + i);
            fullNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            loginListPanel.add(fullNameLabel, gbc);
        }

        JScrollPane loginListScrollPane = new JScrollPane(loginListPanel);
        loginListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        loginListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(loginListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginManagement loginManagement = new LoginManagement();
            loginManagement.setVisible(true);
        });
    }
}
