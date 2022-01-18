package Socket_ex03;

//1��1 ä�ø����
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ServerEx extends Thread {
	class User {
		Socket socket;
		BufferedReader br;
		PrintWriter pw;

		public User(Socket socket, BufferedReader br, PrintWriter pw) {
			this.socket = socket;
			this.br = br;
			this.pw = pw;
		}
	}

	HashMap<String, User> userMap = new HashMap<String, User>();
	ServerSocket listener = null;
	Socket socket = null;
	Scanner scan = new Scanner(System.in);

	public ServerEx() {
		try {
			// ServerSocket�� �����ϰ�
			listener = new ServerSocket(9000); // ����� - URL�� ���� ���� (End Pointer)
			System.out.println("���� >>> ���� ����� ...");
			// Ŭ���̾�Ʈ ���� ��� - ������ �Ǹ� Socket�� ��ȯ�Ѵ�. (�����忡�� ��� ��)

			// ������ ���� ������ �߿��ϴ�.
			this.start();

			// �������� ���� �޼��� �Է� ���
			while (true) {
				String line = scan.nextLine();
				broadcast(String.format("Server>>> %s\n", line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// ���ο� ����ڰ� �������� üũ�ϴ� ������
		while (true) {
			acceptSocket();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void acceptSocket() {
		try {
			socket = listener.accept();
			// Ŭ���̾�Ʈ�� ��/��� ��Ʈ���� �����Ѵ�.
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// �޼��� �޴� ������ ����
			try {
				// ������ '\n'�̴�. '\n'�� ���� �����ʹ� readLine()�� ���� �� ����.
				String userId = br.readLine();
				System.out.println("���� >>> " + userId + "���� ���� �Ͽ����ϴ�!");
				// userId�� �ְ� pw�� �ִٸ� ����ڸ� map �߰��Ѵ�.
				userMap.put(userId, new User(socket, br, pw));
				broadcast(">> " + userId + "���� �����ϼ̽��ϴ�.");

				ReceiveThread receive = new ReceiveThread(br, userId);
				receive.start();
			} catch (Exception e) {
				System.out.println("����� ���� ���� ���� �߻�!");
				System.out.println(e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void broadcast(String message) {
		// userMap�� ����� ��� ����ڵ鿡�� �޼����� �����Ѵ�.
		Iterator<String> keys = userMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			User user = userMap.get(key);
			Socket socket = user.socket;
			try {
				PrintWriter out = user.pw;
				out.println(new String(message.getBytes(), "utf-8"));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Socket Server
		new ServerEx();
	}

	// ---- ���� Ŭ���� - �޼����� �޴� ������ ����
	class ReceiveThread extends Thread {
		// ����� ���ϰ��� �Է� ��Ʈ�� ��ü
		BufferedReader in = null;
		String userId = "";

		public ReceiveThread(BufferedReader br, String userId) {
			this.in = br;
		}

		@Override
		public void run() {
			while (true && in != null) {
				try {
					String clientMessage = in.readLine();
					if (".quit".equalsIgnoreCase(clientMessage)) {
						System.out.println(".quit�� �ԷµǾ ������!");
						break;
					}
					System.out.println(userId + ">>> " + clientMessage);
					broadcast(userId + ">>> " + clientMessage);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	} // end of ReceiveThread
}
