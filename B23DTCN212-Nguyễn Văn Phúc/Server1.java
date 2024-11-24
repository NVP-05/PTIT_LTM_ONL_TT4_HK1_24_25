import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server1 {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(12345);
            System.out.println("Server đang chạy...");

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String data = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                try {
                    int n = Integer.parseInt(data); 
                    if (n <= 0) {
                        throw new Exception("N phải lớn hơn 0.");
                    }

                    String result = findPrimesDivisibleBy5(n);
                    sendBuffer = result.getBytes(); 
                } catch (Exception e) {
                    sendBuffer = ("Lỗi: " + e.getMessage()).getBytes();
                }

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String findPrimesDivisibleBy5(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 2; i <= n; i++) {
            if (isPrime(i) && i % 5 == 0) { 
                result.append(i).append(" ");
            }
        }
        return result.toString().trim();
    }

    public static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
