package me.mrdaniel.npcs.exceptions;

public class NPCException extends Exception {

	public NPCException(String message) {
		super(message);
	}

	public NPCException(String message, Throwable cause) {
		super(message, cause);
	}
}
