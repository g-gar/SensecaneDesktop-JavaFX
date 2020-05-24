package com.magc.sensecane.service;

public class LoggerService {

	public static void notifyError(String error) {
		try {
			throw new Error(error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void notify(String message) {
		System.out.printf("[%s]: %s\n", Thread.currentThread().getStackTrace()[2].getClassName(), message);
	}
}