package user;

import javax.swing.*;
import java.awt.*;

public class GroupManagement extends JFrame {
    public GroupManagement() {
        setTitle("Quản lý nhóm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));

        JPanel renameGroupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        renameGroupPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Thêm border ngăn cách
        JButton renameGroupButton = new JButton("Đổi tên nhóm");
        JLabel name = new JLabel("Tên nhóm: Nhóm số 1");
        renameGroupPanel.add(name);
        renameGroupPanel.add(renameGroupButton);
        memberListPanel.add(renameGroupPanel);

        JPanel addMemberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addMemberPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Thêm border ngăn cách
        JButton addMemberButton = new JButton("Thêm thành viên");
        addMemberPanel.add(addMemberButton);
        memberListPanel.add(addMemberPanel);

        for (int i = 1; i <= 10; i++) {
            JPanel singleMemberPanel = new JPanel(new BorderLayout());
            singleMemberPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Thêm border ngăn cách

            JLabel memberLabel = new JLabel("Thành viên số " + i);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton assignAdminButton = new JButton("Gán quyền admin");
            JButton removeMemberButton = new JButton("Xóa thành viên");

            buttonPanel.add(assignAdminButton);
            buttonPanel.add(removeMemberButton);

            singleMemberPanel.add(memberLabel, BorderLayout.WEST);
            singleMemberPanel.add(buttonPanel, BorderLayout.EAST);

            memberListPanel.add(singleMemberPanel);
        }

        JScrollPane memberListScrollPane = new JScrollPane(memberListPanel);

        getContentPane().add(memberListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GroupManagement groupManagement = new GroupManagement();
            groupManagement.setVisible(true);
        });
    }
}
