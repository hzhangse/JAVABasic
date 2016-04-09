package com.train.jmx;

import java.util.Iterator;
import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class HelloWorldClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Create an RMI connector client and
			// connect it to the RMI connector server
			//
			echo("/nCreate an RMI connector client and "
					+ "connect it to the RMI connector server");
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:1099/TestJMXServer");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

			// Get an MBeanServerConnection
			//
			echo("/nGet an MBeanServerConnection");
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			// Get domains from MBeanServer
			//
			echo("/nDomains:");
			String domains[] = mbsc.getDomains();
			for (int i = 0; i < domains.length; i++) {
				echo("/tDomain[" + i + "] = " + domains[i]);
			}

			// Get MBean count
			//
			echo("/nMBean count = " + mbsc.getMBeanCount());

			// Query MBean names
			//
			echo("/nQuery MBeanServer MBeans:");
			Set names = mbsc.queryNames(null, null);
			for (Iterator i = names.iterator(); i.hasNext();) {
				echo("/tObjectName = " + (ObjectName) i.next());
			}

			// get MBean obj name
			//
			ObjectName stdMBeanName = new ObjectName(
					"TestJMXServer:name=HelloWorld");

			// Access MBean
			// Get attribute in MBean
			echo("/nName = " + mbsc.getAttribute(stdMBeanName, "Name"));
			// set attribute value in MBean
			mbsc.setAttribute(stdMBeanName, new Attribute("Name", "小三黑"));
			// invoke the MBean's method
			mbsc.invoke(stdMBeanName, "printHello", null, null);
			mbsc.invoke(stdMBeanName, "printHello",
					new Object[] { "HelloKity" },
					new String[] { String.class.getName() });

			// Proxy way to access MBean
			HelloMBean proxy = (HelloMBean) (MBeanServerInvocationHandler
					.newProxyInstance(mbsc, stdMBeanName,
							HelloMBean.class, false));
			echo(proxy.getName());
			proxy.setName("帅哥一个");
			echo(proxy.getName());
			proxy.printHello();

			// Close MBeanServer connection
			//
			echo("/nClose the connection to the server");
			jmxc.close();
			echo("/nBye! Bye!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void echo(String msg) {
		System.out.println(msg);
	}

}
