package Socket_ex02;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEx01 {
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	Scanner scan = new Scanner(System.in);

	public ClientEx01() {
		try {
			// ���� ���ϰ� ���� - Socket���� ��� ������ ���� ��.
			socket = new Socket(InetAddress.getLocalHost(), 9999);
			System.out.println("Ŭ���̾�Ʈ >>> ������ ����Ǿ���.");
			// ������ ��/��� ��Ʈ���� �����Ѵ�.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// ������ userId ������
			bw.write("user01" + "\n");
			bw.flush(); // ���۸� ����ش�.
			// �������� ���� �޽��� �ޱ�
			while (true) {
				String line = scan.nextLine();
				bw.write(line + "\n");
				bw.flush();
				if (".quit".equalsIgnoreCase(line)) {
					System.out.println(".quit�� �ԷµǾ ������.");
					break;
				}
				String severMessage = br.readLine();
				System.out.println(severMessage);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new ClientEx01();
	}

}
