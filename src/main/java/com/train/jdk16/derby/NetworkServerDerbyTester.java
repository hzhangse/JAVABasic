package com.train.jdk16.derby;

import java.io.PrintWriter;
import java.sql.DriverManager;

import org.apache.derby.drda.NetworkServerControl;

public class NetworkServerDerbyTester {
	    public static void main(String[] args) {
	        String driver = "org.apache.derby.jdbc.ClientDriver";//在derbyclient.jar里面
	        String dbName="NetworkDB";
	        String connectionURL = "jdbc:derby://localhost:1527/" + dbName + ";create=true";
	        try {
	            /*
	             创建Derby网络服务器,默认端口是1527,也可以通过运行
	             <Derby_Home>/frameworks/NetworkServer/bin/startNetworkServer.bat
	             来创建并启动Derby网络服务器,如果是Unix,用startNetworkServer.ksh
	            */
	            NetworkServerControl derbyServer = new NetworkServerControl();//NetworkServerControl类在derbynet.jar里面
	            PrintWriter pw = new PrintWriter(System.out);//用系统输出作为Derby数据库的输出
	            derbyServer.start(pw);//启动Derby服务器
	            Class.forName(driver);
	            DriverManager.getConnection(connectionURL);
	            //do something
	            derbyServer.shutdown();//关闭Derby服务器
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}