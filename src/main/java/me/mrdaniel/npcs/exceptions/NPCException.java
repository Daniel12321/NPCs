package me.mrdaniel.npcs.exceptions;

import javax.annotation.Nonnull;

public class NPCException extends Exception {

	private static final long serialVersionUID = 7147632835099311666L;

	public NPCException(@Nonnull final String message) {
		super(message);
	}

	public NPCException(@Nonnull final String message, @Nonnull final Throwable cause) {
		super(message, cause);
	}
}