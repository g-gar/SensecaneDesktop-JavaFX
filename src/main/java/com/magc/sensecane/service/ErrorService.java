package com.magc.sensecane.service;

public class ErrorService {

	public static void notifyError(String error) {
		try {
			throw new Error(error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
