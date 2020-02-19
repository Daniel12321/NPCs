package me.mrdaniel.npcs.exceptions;

public class ConditionException extends Exception {

	public ConditionException(String message) {
		super(message);
	}

	public ConditionException(String message, Throwable cause) {
		super(message, cause);
	}
}
