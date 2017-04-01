package me.mrdaniel.npcs.exception;

import javax.annotation.Nonnull;

public class NPCException extends Exception {

	private static final long serialVersionUID = 7147632835099311666L;

	public NPCException(@Nonnull final String message) {
		super(message);
	}
}