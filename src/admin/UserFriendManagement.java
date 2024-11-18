package admin;

import javax.swing.*;
import java.awt.*;

public class UserFriendManagement extends JFrame {
    public UserFriendManagement() {
        setTitle("Quản lý bạn bè người dùng");
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
        JTextField filterFriendCountField = new JTextField(5);
        JComboBox<String> filterFriendCountComboBox = new JComboBox<>(new String[]{"Bằng", "Nhỏ hơn", "Lớn hơn"});
        JButton filterFriendCountButton = new JButton("Lọc theo số lượng bạn trực tiếp");

        functionPanel.add(sortByNameButton);
        functionPanel.add(sortByDateButton);
        functionPanel.add(filterNameField);
        functionPanel.add(filterNameButton);
        functionPanel.add(filterFriendCountField);
        functionPanel.add(filterFriendCountComboBox);
        functionPanel.add(filterFriendCountButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        userListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        userListPanel.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 2;
        userListPanel.add(new JLabel("Thời gian tạo"), gbc);
        gbc.gridx = 3;
        userListPanel.add(new JLabel("Số lượng bạn trực tiếp"), gbc);
        gbc.gridx = 4;
        userListPanel.add(new JLabel("Số lượng bạn của bạn"), gbc);

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
            JLabel directFriendsLabel = new JLabel(String.valueOf(i * 2)); // số lượng bạn trực tiếp
            directFriendsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(directFriendsLabel, gbc);

            gbc.gridx = 4;
            JLabel totalFriendsLabel = new JLabel(String.valueOf(i * 3)); // số lượng bạn của bạn
            totalFriendsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            userListPanel.add(totalFriendsLabel, gbc);
        }

        JScrollPane userListScrollPane = new JScrollPane(userListPanel);
        userListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(userListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserFriendManagement userFriendManagement = new UserFriendManagement();
            userFriendManagement.setVisible(true);
        });
    }
}