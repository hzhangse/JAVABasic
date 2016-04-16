package com.train.jdk16.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmbeddedDerbyTester {
	    public static void main(String[] args) {
	        String driver = "org.apache.derby.jdbc.EmbeddedDriver";//在derby.jar里面
	        String dbName="EmbeddedDB";
	        String dbURL = "jdbc:derby:"+dbName+";create=true";//create=true表示当数据库不存在时就创建它
	        try {            
	            Class.forName(driver);
	            Connection conn = DriverManager.getConnection(dbURL);//启动嵌入式数据库
	            Statement st = conn.createStatement();
	            st.execute("create table foo (FOOID INT NOT NULL,FOONAME VARCHAR(30) NOT NULL)");//创建foo表
	            st.executeUpdate("insert into foo(FOOID,FOONAME) values (1,'chinajash')");//插入一条数据
	            ResultSet rs = st.executeQuery("select * from foo");//读取刚插入的数据
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