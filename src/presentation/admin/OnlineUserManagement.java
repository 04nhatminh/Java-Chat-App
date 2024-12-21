package admin;

import javax.swing.*;
import java.awt.*;

public class OnlineUserManagement extends JFrame {
    public OnlineUserManagement() {
        setTitle("Quản lý người dùng hoạt động");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel userListPanel = new JPanel(new GridBagLayout());
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
        JTextField filterActivityCountField = new JTextField(5);
        JComboBox<String> filterActivityCountComboBox = new JComboBox<>(new String[]{"Bằng", "Nhỏ hơn", "Lớn hơn"});
        JButton filterActivityCountButton = new JButton("Lọc theo số lượng hoạt động");

        functionPanel.add(sortByNameButton);
        functionPanel.add(sortByDateButton);
        functionPanel.add(filterNameField);
        functionPanel.add(filterNameButton);
        functionPanel.add(filterActivityCountField);
        functionPanel.add(filterActivityCountComboBox);
        functionPanel.add(filterActivityCountButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        userListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        userListPanel.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 2;
        userListPanel.add(new JLabel("Thời gian tạo"), gbc);
        gbc.gridx = 3;
        userListPanel.add(new JLabel("Mở ứng dụng"), gbc);
        gbc.gridx = 4;
        userListPanel.add(new JLabel("Chat với bao nhiêu người"), gbc);
        gbc.gridx = 5;
        userListPanel.add(new JLabel("Chat bao nhiêu nhóm"), gbc);

        for (int i = 1; i <= 10; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel usernameLabel = new JLabel("user" + i);
            usernameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(usernameLabel, gbc);

            gbc.gridx = 1;
            JLabel fullNameLabel = new JLabel("Họ tên " + i);
            fullNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(fullNameLabel, gbc);

            gbc.gridx = 2;
            JLabel creationTimeLabel = new JLabel("2024-11-18 10:00:0" + i);
            creationTimeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(creationTimeLabel, gbc);

            gbc.gridx = 3;
            JLabel appOpenLabel = new JLabel(String.valueOf(i * 2)); // số lần mở ứng dụng
            appOpenLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(appOpenLabel, gbc);

            gbc.gridx = 4;
            JLabel chatWithUsersLabel = new JLabel(String.valueOf(i * 3)); // số người đã chat
            chatWithUsersLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(chatWithUsersLabel, gbc);

            gbc.gridx = 5;
            JLabel chatInGroupsLabel = new JLabel(String.valueOf(i * 4)); // số nhóm đã chat
            chatInGroupsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(chatInGroupsLabel, gbc);
        }

        JScrollPane userListScrollPane = new JScrollPane(userListPanel);
        userListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel chartPlaceholderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
                g.setColor(Color.BLACK);
                g.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
                g.drawString("Biểu đồ số lượng người hoạt động theo năm", 20, 30);
            }
        };
        chartPlaceholderPanel.setPreferredSize(new Dimension(800, 200));

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(userListScrollPane, BorderLayout.CENTER);
        getContentPane().add(chartPlaceholderPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineUserManagement onlineUserManagement = new OnlineUserManagement();
            onlineUserManagement.setVisible(true);
        });
    }
}