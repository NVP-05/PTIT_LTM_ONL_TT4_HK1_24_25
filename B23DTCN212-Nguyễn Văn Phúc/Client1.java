import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket(); 
            InetAddress serverAddress = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Nhập một số nguyên tố N: ");
                String input = scanner.nextLine();

                try {
                    int n = Integer.parseInt(input);
                    if (n <= 0) {
                        throw new Exception("N phải là một số nguyên dương.");
                    }

                    if (!isPrime(n)) {
                        System.out.println("Số " + n + " không phải là số nguyên tố. Vui lòng nhập lại.");
                        continue;
                    }

                    byte[] sendBuffer = input.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 12345);
                    clientSocket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    clientSocket.receive(receivePacket);

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Phản hồi từ server: " + response);

                    break;
                } catch (Exception e) {
                    System.out.println("Lỗi nhập liệu: " + e.getMessage() + ". Vui lòng thử lại.");
                }
            }

            clientSocket.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
