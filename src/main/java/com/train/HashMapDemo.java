package com.train;


import java.util.HashMap;

//��дEquals����дHashCode 
class Key {
	private Integer id;
	private String value;
	public Key(Integer id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Key)) {
			return false;
		} else {
			return this.id.equals(((Key) o).id);
		}
	}
}

// ��дEqualsҲ��дHashCode
class Key_ {
	private Integer id;
	private String value;

	public Key_(Integer id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Key_)) {
			return false;
		} else {
			return this.id.equals(((Key_) o).id);
		}
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

public class HashMapDemo {
	public static void main(String[] args) {
		HashMap<Object, String> values = new HashMap<Object, String>(5);
		Key key1 = new Key(1, "one");
		Key key2 = new Key(1, "one");
		System.out.println(key1.equals(key2)); // true
		values.put(key1, "value 1");
		System.out.println(values.get(key2));  // null

		Key_ key_1 = new Key_(1, "one");
		Key_ key_2 = new Key_(1, "one");
		System.out.println(key_1.equals(key_2));  // true
		System.out.println(key_1 == key_2);   // false
		values.put(key_1, "value 1");
		System.out.println(values.get(key_2));
	}
}
