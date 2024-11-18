package admin;

import javax.swing.*;
import java.awt.*;

public class GroupChatListManagement extends JFrame {
    public GroupChatListManagement() {
        setTitle("Quản lý danh sách nhóm chat");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel groupListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JButton sortByNameButton = new JButton("Sắp xếp theo tên");
        JButton sortByDateButton = new JButton("Sắp xếp theo thời gian tạo");
        JTextField filterField = new JTextField(15);
        JButton filterButton = new JButton("Lọc theo tên");

        functionPanel.add(sortByNameButton);
        functionPanel.add(sortByDateButton);
        functionPanel.add(filterField);
        functionPanel.add(filterButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        groupListPanel.add(new JLabel("Tên nhóm"), gbc);
        gbc.gridx = 1;
        groupListPanel.add(new JLabel("Thời gian tạo"), gbc);

        for (int i = 1; i <= 10; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel groupNameLabel = new JLabel("Nhóm " + i);
            groupNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            groupListPanel.add(groupNameLabel, gbc);

            gbc.gridx = 1;
            JLabel creationTimeLabel = new JLabel("2024-11-18 10:00:0" + i);
            creationTimeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            groupListPanel.add(creationTimeLabel, gbc);

            gbc.gridx = 2;
            JButton viewMembersButton = new JButton("Xem thành viên");
            groupListPanel.add(viewMembersButton, gbc);

            gbc.gridx = 3;
            JButton viewAdminsButton = new JButton("Xem admin");
            groupListPanel.add(viewAdminsButton, gbc);
        }

        JScrollPane groupListScrollPane = new JScrollPane(groupListPanel);
        groupListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        groupListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(groupListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GroupChatListManagement groupChatListManagement = new GroupChatListManagement();
            groupChatListManagement.setVisible(true);
        });
    }
}
