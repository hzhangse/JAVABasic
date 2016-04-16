package com.train.jdk16.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmbeddedDerbyTester {
	    public static void main(String[] args) {
	        String driver = "org.apache.derby.jdbc.EmbeddedDriver";//��derby.jar����
	        String dbName="EmbeddedDB";
	        String dbURL = "jdbc:derby:"+dbName+";create=true";//create=true��ʾ�����ݿⲻ����ʱ�ʹ�����
	        try {            
	            Class.forName(driver);
	            Connection conn = DriverManager.getConnection(dbURL);//����Ƕ��ʽ���ݿ�
	            Statement st = conn.createStatement();
	            st.execute("create table foo (FOOID INT NOT NULL,FOONAME VARCHAR(30) NOT NULL)");//����foo��
	            st.executeUpdate("insert into foo(FOOID,FOONAME) values (1,'chinajash')");//����һ������
	            ResultSet rs = st.executeQuery("select * from foo");//��ȡ�ղ��������
	            while(rs.next()){
	                int id = rs.getInt(1);
	                String name = rs.getString(2);
	                System.out.println("id="+id+";name="+name);
	            }
	        } catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	}