import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket(); // Tạo socket cho Client
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Địa chỉ Server

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Nhập mã sinh viên: ");
                String studentId = scanner.nextLine();

                // Gửi mã sinh viên đến Server
                byte[] sendBuffer = studentId.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 12345);
                clientSocket.send(sendPacket);

                // Nhận kết quả từ Server
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Phản hồi từ server:\n" + response);
                break; // Kết thúc sau khi nhận được phản hồi
            }

            clientSocket.close(); // Đóng kết nối
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
