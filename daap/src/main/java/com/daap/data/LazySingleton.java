package com.daap.data;

public class LazySingleton {

	private static LazySingleton lazySingleton;

	private LazySingleton() {

	}

	public static LazySingleton getEagerInstance() {

		if (lazySingleton == null) {
			lazySingleton = new LazySingleton();
		}

		return lazySingleton;
	}


}
