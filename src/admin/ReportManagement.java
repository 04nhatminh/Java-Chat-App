package admin;

import javax.swing.*;
import java.awt.*;

public class ReportManagement extends JFrame {
    public ReportManagement() {
        setTitle("Quản lý báo cáo spam");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel reportListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JButton sortByTimeButton = new JButton("Sắp xếp theo thời gian");
        JButton sortByUsernameButton = new JButton("Sắp xếp theo tên đăng nhập");
        JTextField filterTimeField = new JTextField(15);
        JButton filterTimeButton = new JButton("Lọc theo thời gian");
        JTextField filterUsernameField = new JTextField(15);
        JButton filterUsernameButton = new JButton("Lọc theo tên đăng nhập");

        functionPanel.add(sortByTimeButton);
        functionPanel.add(sortByUsernameButton);
        functionPanel.add(filterTimeField);
        functionPanel.add(filterTimeButton);
        functionPanel.add(filterUsernameField);
        functionPanel.add(filterUsernameButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        reportListPanel.add(new JLabel("Thời gian"), gbc);
        gbc.gridx = 1;
        reportListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 2;
        reportListPanel.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 3;
        reportListPanel.add(new JLabel("Nội dung báo cáo"), gbc);

        for (int i = 1; i <= 10; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel timeLabel = new JLabel("2024-11-18 10:00:0" + i);
            timeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            reportListPanel.add(timeLabel, gbc);

            gbc.gridx = 1;
            JLabel usernameLabel = new JLabel("user" + i);
            usernameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            reportListPanel.add(usernameLabel, gbc);

            gbc.gridx = 2;
            JLabel fullNameLabel = new JLabel("Họ tên " + i);
            fullNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            reportListPanel.add(fullNameLabel, gbc);

            gbc.gridx = 3;
            JLabel reportContentLabel = new JLabel("Nội dung báo cáo " + i);
            reportContentLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            reportListPanel.add(reportContentLabel, gbc);

            gbc.gridx = 4;
            JButton lockAccountButton = new JButton("Khóa tài khoản");
            reportListPanel.add(lockAccountButton, gbc);
        }

        JScrollPane reportListScrollPane = new JScrollPane(reportListPanel);
        reportListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reportListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(reportListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportManagement reportManagement = new ReportManagement();
            reportManagement.setVisible(true);
        });
    }
}