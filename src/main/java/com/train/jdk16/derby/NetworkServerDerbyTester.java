package com.train.jdk16.derby;

import java.io.PrintWriter;
import java.sql.DriverManager;

import org.apache.derby.drda.NetworkServerControl;

public class NetworkServerDerbyTester {
	    public static void main(String[] args) {
	        String driver = "org.apache.derby.jdbc.ClientDriver";//��derbyclient.jar����
	        String dbName="NetworkDB";
	        String connectionURL = "jdbc:derby://localhost:1527/" + dbName + ";create=true";
	        try {
	            /*
	             ����Derby���������,Ĭ�϶˿���1527,Ҳ����ͨ������
	             <Derby_Home>/frameworks/NetworkServer/bin/startNetworkServer.bat
	             ������������Derby���������,�����Unix,��startNetworkServer.ksh
	            */
	            NetworkServerControl derbyServer = new NetworkServerControl();//NetworkServerControl����derbynet.jar����
	            PrintWriter pw = new PrintWriter(System.out);//��ϵͳ�����ΪDerby���ݿ�����
	            derbyServer.start(pw);//����Derby������
	            Class.forName(driver);
	            DriverManager.getConnection(connectionURL);
	            //do something
	            derbyServer.shutdown();//�ر�Derby������
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}