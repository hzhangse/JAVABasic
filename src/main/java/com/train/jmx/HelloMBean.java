package com.train.jmx;

/**
 * Server�˳������� 1. MBean�ӿ� ����һ��Ӧ�ó���Ҫ���Ⱪ¶�Ľӿڣ��ڸýӿ�����Ҫ����Ҫ���������к�����
 * ���У��������һ�Զ�Ӧ��get������set��������ô��Ĭ����һ�����ԣ�����Ϥ�����־���get����������get����Ĳ��֡�
 * ������MBean�з��������ǿ��Ա�����ġ�MBean�������й淶�ģ�����ԭ���������MBean������
 * 
 * @author ryan
 *
 */
public interface HelloMBean {
	public String getName();

	public void setName(String name);

	public void printHello();

	public void printHello(String whoName);
}
