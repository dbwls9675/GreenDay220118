package Socket_ex02;

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
		PrintWriter pw = null;
		Scanner scan = new Scanner(System.in);
		try {
			// ServerSocket�� �����ϰ� 
			listener = new ServerSocket(9000); // ����� - URL�� ���� ���� (End Pointer)
			System.out.println("���� >>> ���� ����� ...");
			// Ŭ���̾�Ʈ ���� ��� - ������ �Ǹ� Socket�� ��ȯ�Ѵ�.
			socket = listener.accept();
			System.out.println("���� >>> Ŭ���̾�Ʈ�� ������ �Ǿ����ϴ�~");
			// Ŭ���̾�Ʈ�� ��/��� ��Ʈ���� �����Ѵ�.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Ŭ���̾�Ʈ�� userId�� �о����
			try {
				// ������ '\n'�̴�. '\n'�� ���� �����ʹ� readLine()�� ���� �� ����.
				String userId = br.readLine(); 
				System.out.println("���� >>> "+userId+"���� ���� �Ͽ����ϴ�!");
			} catch (Exception e) {
				System.out.println("�о� ���� �����Ͱ� ����!");
			}
			// ���� �����͸� �ٽ� ���� �ֱ� - ���� ���
			while(true) {
				String line = br.readLine();
				if(".quit".equalsIgnoreCase(line)) {
					System.out.println(".quit�� �ԷµǾ ������!");
					break;
				}
				System.out.println(">>> " + line);
				pw.printf("Server>>> %s\n", line);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) bw.close();
				if(br != null) br.close();
				if(socket != null) socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Socket Server
		new ServerEx01();
	}

}
