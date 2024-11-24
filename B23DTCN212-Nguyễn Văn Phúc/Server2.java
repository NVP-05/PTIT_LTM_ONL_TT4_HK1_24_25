import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server2 {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(12345); // Tạo socket cho Server ở cổng 12345
            System.out.println("Server đang chạy...");

            while (true) {
                // Nhận dữ liệu từ Client
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String studentId = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Khởi tạo một thread để xử lý yêu cầu
                new Thread(() -> {
                    try {
                        String result = processStudentId(studentId);
                        byte[] sendBuffer = result.getBytes();
                        // Gửi kết quả về Client
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm xử lý mã sinh viên: tính tổng các số chẵn và liệt kê số nguyên tố
    public static String processStudentId(String studentId) {
        StringBuilder result = new StringBuilder();
        int sumEven = 0;
        List<Integer> primes = new ArrayList<>();

        for (char c : studentId.toCharArray()) {
            if (Character.isDigit(c)) {
                int digit = c - '0';
                
                // Tính tổng các số chẵn
                if (digit % 2 == 0) {
                    sumEven += digit;
                }

                // Kiểm tra số nguyên tố
                if (isPrime(digit)) {
                    primes.add(digit);
                }
            }
        }

        result.append("Tổng các số chẵn: ").append(sumEven).append("\n");
        result.append("Số nguyên tố trong mã sinh viên: ").append(primes.isEmpty() ? "Không có" : primes.toString());
        return result.toString();
    }

    // Hàm kiểm tra số nguyên tố
    public static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
