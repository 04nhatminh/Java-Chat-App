package user;

import javax.swing.*;
import java.awt.*;

public class FriendRequestList extends JFrame {
    public FriendRequestList() {
        setTitle("Danh sách yêu cầu kết bạn");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 20; i++) {
            JPanel singleRequestPanel = new JPanel(new BorderLayout());
            JLabel requestLabel = new JLabel("Yêu cầu từ bạn số " + i);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton acceptButton = new JButton("Chấp nhận");
            JButton declineButton = new JButton("Từ chối");

            buttonPanel.add(acceptButton);
            buttonPanel.add(declineButton);

            singleRequestPanel.add(requestLabel, BorderLayout.WEST);
            singleRequestPanel.add(buttonPanel, BorderLayout.EAST);

            requestPanel.add(singleRequestPanel);
        }

        JScrollPane scrollPane = new JScrollPane(requestPanel);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FriendRequestList requestList = new FriendRequestList();
            requestList.setVisible(true);
        });
    }
}
