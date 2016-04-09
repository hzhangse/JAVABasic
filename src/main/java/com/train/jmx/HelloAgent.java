package com.train.jmx;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * ����һ��Agent�� Agent��ʵʵ�ֵ���������Server�Ĺ��ܣ��������JMX����󶨵���Ӧ��URL��
 * �����������洴���ı��������󶨵����У�ʹ���ⲿ���Է��ʡ�
 * 
 * @author ryan
 *
 */
public class HelloAgent {

	public static void main(String[] args) throws MalformedObjectNameException,
			NullPointerException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException, IOException {
		/**
		 * �������ڳ���дAgent����ʱ����connection refused���쳣��ʱ�򣬲����£��Ͻ����һ����ĳ������ǲ�������仰��
		 * Registry registry = LocateRegistry.createRegistry(rmiPort);
		 * LocateRegistry.createRegistry(int
		 * port)����������ĳһ�ض��˿ڴ������ַ���,�Ӷ��û��������ֹ�����rmiregistry ���ߣ���Ҳ��������
		 * jdkfolder/bin/rmiregistry.exe 9999
		 * ����jdkfolder�����jdk�İ�װĿ¼��9999����Ҫ�󶨵Ķ˿� �����������������ڴ����������������code��һ����Ч��
		 */
		int rmiPort = 1099;
		String jmxServerName = "TestJMXServer";

		// jdkfolder/bin/rmiregistry.exe 9999
		Registry registry = LocateRegistry.createRegistry(rmiPort);
		/**
		 * Agent��ʵ���������ֻ�ȡMBeanServer�ķ�ʽ�� MBeanServer mbs =
		 * MBeanServerFactory.createMBeanServer(jmxServerName); ���ַ�ʽ��Ҫ����JDK1.5��ǰ
		 * MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		 * ���ַ�ʽ��JDK1.5����ġ� ��ȫ����� ����Ҳ���ȷ���Ժ���Ļ������氲װ��JDK��1.5���ϵİ汾ʱ�����鰴�յ�һ�ַ�ʽ��
		 */
		MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
		// MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Agentʵ����ΪMBeanServer�����һ��htmladapter���������ǾͿ���ͨ����ҳ�ķ�ʽ�����й���
		// ����˵��������ʵ�е�Agent�����ǾͿ���ͨ��http://localhost:8082���Գ�����й��������8082����htmladapter�����õĶ˿ڡ�
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		ObjectName adapterName;
		adapterName = new ObjectName(jmxServerName + ":name=" + "htmladapter");
		adapter.setPort(8082);
		adapter.start();
		mbs.registerMBean(adapter, adapterName);
		
		// ����Ҫ���������
		ObjectName objName = new ObjectName(jmxServerName + ":name="
				+ "HelloWorld");
		mbs.registerMBean(new Hello(), objName);

		//������󶨵��̶���URL��
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
						+ jmxServerName);
		System.out.println("JMXServiceURL: " + url.toString());
		JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbs);
		jmxConnServer.start();

	}
}
