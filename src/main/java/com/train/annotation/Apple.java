package com.train.annotation;

public class Apple {

	@FruitName("Apple")
	private String appleName;

	@FruitColor(fruitColor = FruitColor.Color.RED)
	private String appleColor;

	@FruitProvider(id = 1, name = "�����츻ʿ����", address = "����ʡ�������Ӱ�·89�ź츻ʿ����")
	private String appleProvider;

	public void setAppleColor(String appleColor) {
		this.appleColor = appleColor;
	}

	public String getAppleColor() {
		return appleColor;
	}

	public void setAppleName(String appleName) {
		this.appleName = appleName;
	}

	public String getAppleName() {
		return appleName;
	}

	public void setAppleProvider(String appleProvider) {
		this.appleProvider = appleProvider;
	}

	public String getAppleProvider() {
		return appleProvider;
	}

	public void displayName() {
		System.out.println("ˮ���������ǣ�ƻ��");
	}

	public static void main(String[] args) {

		FruitInfoUtil.getFruitInfo(Apple.class);
		Apple apple = new Apple();
		System.out.println(apple.getAppleName() + apple.getAppleColor() + apple.getAppleProvider());
	}
}