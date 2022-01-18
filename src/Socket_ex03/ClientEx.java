package Socket_ex03;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEx {
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	Scanner scan = new Scanner(System.in);
	static String[] fieldArgs;

	public ClientEx() {
		try {
			// ���� ���ϰ� ���� - Socket���� ��� ������ ���� ��.
			socket = new Socket(InetAddress.getLocalHost(), 9000);
			System.out.println("Ŭ���̾�Ʈ >>> ������ ����Ǿ���.");
			// ������ ��/��� ��Ʈ���� �����Ѵ�.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// ������ userId ������
			String userId = "user" + (int) (Math.random() * 1000);
			if (fieldArgs != null && fieldArgs.length != 0) {
				userId = fieldArgs[0];
			}
			bw.write(userId + "\n");
			bw.flush(); // ���۸� ����ش�.

			// ReceiveThread ����
			ReceiveThread receive = new ReceiveThread(br);
			receive.start();

			// �������� ���� �޽��� �ޱ�
			while (true) {
				String line = scan.nextLine();
				bw.write(line + "\n");
				bw.flush();
				if (".quit".equalsIgnoreCase(line)) {
					System.out.println(".quit�� �ԷµǾ ������!");
					System.exit(0);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		fieldArgs = args;
		new ClientEx();
	}

	// �޼����� �޴� ������ ����
	class ReceiveThread extends Thread {
		// ����� ���ϰ��� �Է� ��Ʈ�� ��ü
		BufferedReader br = null;

		public ReceiveThread(BufferedReader br) {
			this.br = br;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String serverMessage = br.readLine();
					System.out.println(serverMessage);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				} catch (SocketException e) {
					System.out.println("Ŭ���̾�Ʈ>> ������ ������ ���������ϴ�.");
					try {
						if (socket != null)
							socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0); // ���� ����
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end of while
		}
	} // end of ReceiveThread
}
