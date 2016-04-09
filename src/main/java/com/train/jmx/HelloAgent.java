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
 * 创建一个Agent类 Agent其实实现的是类似于Server的功能，他负责把JMX服务绑定到相应的URL，
 * 并将我们上面创建的被管理的类绑定到其中，使得外部可以访问。
 * 
 * @author ryan
 *
 */
public class HelloAgent {

	public static void main(String[] args) throws MalformedObjectNameException,
			NullPointerException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException, IOException {
		/**
		 * 如果大家在尝试写Agent程序时出现connection refused的异常的时候，不用怕，赶紧检查一下你的程序中是不是有这句话：
		 * Registry registry = LocateRegistry.createRegistry(rmiPort);
		 * LocateRegistry.createRegistry(int
		 * port)方法可以在某一特定端口创建名字服务,从而用户无需再手工启动rmiregistry 或者，你也可以运行
		 * jdkfolder/bin/rmiregistry.exe 9999
		 * 其中jdkfolder是你的jdk的安装目录，9999是你要绑定的端口 运行上面的命令和你在代码中添加上面那行code是一样的效果
		 */
		int rmiPort = 1099;
		String jmxServerName = "TestJMXServer";

		// jdkfolder/bin/rmiregistry.exe 9999
		Registry registry = LocateRegistry.createRegistry(rmiPort);
		/**
		 * Agent的实现中是两种获取MBeanServer的方式。 MBeanServer mbs =
		 * MBeanServerFactory.createMBeanServer(jmxServerName); 这种方式主要用于JDK1.5以前
		 * MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		 * 这种方式是JDK1.5引入的。 安全起见， 当大家不能确定以后部署的机器上面安装的JDK是1.5以上的版本时，建议按照第一种方式。
		 */
		MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
		// MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Agent实现中为MBeanServer添加了一个htmladapter，这样我们就可以通过网页的方式来进行管理。
		// 比如说上面我们实行的Agent，我们就可以通过http://localhost:8082来对程序进行管理。这里的8082就是htmladapter中设置的端口。
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		ObjectName adapterName;
		adapterName = new ObjectName(jmxServerName + ":name=" + "htmladapter");
		adapter.setPort(8082);
		adapter.start();
		mbs.registerMBean(adapter, adapterName);
		
		// 绑定需要被管理的类
		ObjectName objName = new ObjectName(jmxServerName + ":name="
				+ "HelloWorld");
		mbs.registerMBean(new Hello(), objName);

		//将服务绑定到固定的URL上
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
						+ jmxServerName);
		System.out.println("JMXServiceURL: " + url.toString());
		JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbs);
		jmxConnServer.start();

	}
}
