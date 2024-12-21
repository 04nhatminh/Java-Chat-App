package emailService;

import java.io.IOException;

public class EmailUtil {
    public static void sendEmail(String to, String subject, String content) {
        String smtpServer = "smtp.example.com"; // Thay bằng SMTP server của bạn
        int port = 587; // Thay bằng cổng SMTP của bạn
        String username = "your-email@example.com"; // Thay bằng email của bạn
        String password = "your-email-password"; // Thay bằng mật khẩu email của bạn

        SMTPClient smtpClient = new SMTPClient(smtpServer, port, username, password);
        try {
            smtpClient.sendEmail(to, subject, content);
            System.out.println("Email sent successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}