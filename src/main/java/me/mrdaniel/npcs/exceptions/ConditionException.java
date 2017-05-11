package me.mrdaniel.npcs.exceptions;

import javax.annotation.Nonnull;

public class ConditionException extends Exception {

	private static final long serialVersionUID = 7147632835099311666L;

	public ConditionException(@Nonnull final String message) {
		super(message);
	}

	public ConditionException(@Nonnull final String message, @Nonnull final Throwable cause) {
		super(message, cause);
	}
}