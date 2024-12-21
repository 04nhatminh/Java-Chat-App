package emailService;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class SMTPClient {
    private String smtpServer;
    private int port;
    private String username;
    private String password;

    public SMTPClient(String smtpServer, int port, String username, String password) {
        this.smtpServer = smtpServer;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String to, String subject, String content) throws IOException {
        try (Socket socket = new Socket(smtpServer, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            writer.write("HELO " + smtpServer + "\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("AUTH LOGIN\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write(Base64.getEncoder().encodeToString(username.getBytes()) + "\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write(Base64.getEncoder().encodeToString(password.getBytes()) + "\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("MAIL FROM:<" + username + ">\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("RCPT TO:<" + to + ">\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("DATA\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("Subject: " + subject + "\r\n");
            writer.write("To: " + to + "\r\n");
            writer.write("\r\n");
            writer.write(content + "\r\n");
            writer.write(".\r\n");
            writer.flush();
            System.out.println(reader.readLine());

            writer.write("QUIT\r\n");
            writer.flush();
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw e;
        }
    }
}