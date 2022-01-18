package Socket_ex01;

//Socket ServerSocket ���� ��� ����
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerEx01 {
	ServerSocket listener = null;

	public ServerEx01() {
		Socket socket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		Scanner scan = new Scanner(System.in);
		try {
			// ServerSocket�� �����ϰ�
			listener = new ServerSocket(9999);
			System.out.println("���� >>> ���� ����� ...");
			// Ŭ���̾�Ʈ ���� ��� - ������ �Ǹ� Socket�� ��ȯ�Ѵ�.
			socket = listener.accept();
			System.out.println("���� >>> Ŭ���̾�Ʈ�� ������ �Ǿ����ϴ�~");
			// Ŭ���̾�Ʈ�� ��/��� ��Ʈ���� �����Ѵ�.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Ŭ���̾�Ʈ�� userId�� �о����
			String userId = br.readLine();
			System.out.println("���� >>> " + userId + "���� ���� �Ͽ����ϴ�!");

			while (true) {
				// �ݺ��ؼ� �о���δ�.
				String line = br.readLine();
				System.out.println(">>> " + line);
				// Ŭ���̾�Ʈ�� �ٽ� ���� (����)
				bw.write("Server>>> " + line + "\n");
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Socket Server
		new ServerEx01();
	}

}
