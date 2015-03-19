package com.train;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;

	public User() {

	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

public class ServerForTransferClass {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10000);

		while (true) {
			Socket socket = server.accept();
			invoke(socket);
		}
	}

	private static void invoke(final Socket socket) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				ObjectInputStream is = null;
				ObjectOutputStream os = null;
				try {
					is = new ObjectInputStream(new BufferedInputStream(socket
							.getInputStream()));
					os = new ObjectOutputStream(socket.getOutputStream());

					Object obj = is.readObject();
					User user = (User) obj;
					System.out.println("user: " + user.getName() + "/"
							+ user.getPassword());

					user.setName(user.getName() + "_new");
					user.setPassword(user.getPassword() + "_new");

					os.writeObject(user);
					os.flush();
				} catch (IOException ex) {
				} catch (ClassNotFoundException ex) {
				} finally {
					try {
						is.close();
					} catch (Exception ex) {
					}
					try {
						os.close();
					} catch (Exception ex) {
					}
					try {
						socket.close();
					} catch (Exception ex) {
					}
				}
			}
		}).start();
	}

}
