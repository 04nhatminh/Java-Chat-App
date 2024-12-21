package admin;

import javax.swing.*;
import java.awt.*;

public class SignupManagement extends JFrame {
    public SignupManagement() {
        setTitle("Quản lý đăng ký mới");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel signupListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JButton sortByNameButton = new JButton("Sắp xếp theo tên");
        JButton sortByDateButton = new JButton("Sắp xếp theo thời gian tạo");
        JTextField filterNameField = new JTextField(15);
        JButton filterNameButton = new JButton("Lọc theo tên");

        functionPanel.add(sortByNameButton);
        functionPanel.add(sortByDateButton);
        functionPanel.add(filterNameField);
        functionPanel.add(filterNameButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        signupListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        signupListPanel.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 2;
        signupListPanel.add(new JLabel("Thời gian tạo"), gbc);

        for (int i = 1; i <= 20; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel usernameLabel = new JLabel("user" + i);
            usernameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            signupListPanel.add(usernameLabel, gbc);

            gbc.gridx = 1;
            JLabel fullNameLabel = new JLabel("Họ tên " + i);
            fullNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            signupListPanel.add(fullNameLabel, gbc);

            gbc.gridx = 2;
            JLabel creationTimeLabel = new JLabel("2024-11-18 10:00:0" + i);
            creationTimeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            signupListPanel.add(creationTimeLabel, gbc);
        }

        JScrollPane signupListScrollPane = new JScrollPane(signupListPanel);
        signupListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        signupListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel chartPlaceholderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
                g.setColor(Color.BLACK);
                g.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
                g.drawString("Biểu đồ số lượng người đăng ký mới theo tháng", 20, 30);
            }
        };
        chartPlaceholderPanel.setPreferredSize(new Dimension(800, 200));

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(signupListScrollPane, BorderLayout.CENTER);
        getContentPane().add(chartPlaceholderPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignupManagement signupManagement = new SignupManagement();
            signupManagement.setVisible(true);
        });
    }
}