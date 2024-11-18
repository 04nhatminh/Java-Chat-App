package user;

import javax.swing.*;
import java.awt.*;

public class CreateGroup extends JFrame {
    public CreateGroup() {
        setTitle("Danh sách bạn bè");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 20; i++) {
            JPanel singleFriendPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel friendLabel = new JLabel("Bạn số " + i);
            JButton addButton = new JButton("Thêm");
            JButton setAdminButton = new JButton("Admin");

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0;
            singleFriendPanel.add(friendLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0.25;
            singleFriendPanel.add(addButton, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0.25;
            singleFriendPanel.add(setAdminButton, gbc);

            friendPanel.add(singleFriendPanel);
        }

        JScrollPane scrollPane = new JScrollPane(friendPanel);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        JButton createGroupButton = new JButton("Tạo Group");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(createGroupButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreateGroup CreateGroup = new CreateGroup();
            CreateGroup.setVisible(true);
        });
    }
}

