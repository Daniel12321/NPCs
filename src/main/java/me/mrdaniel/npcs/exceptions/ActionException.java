package me.mrdaniel.npcs.exceptions;

public class ActionException extends Exception {

	public ActionException(String message) {
		super(message);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}
}
