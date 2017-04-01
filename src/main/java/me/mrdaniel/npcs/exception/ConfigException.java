package me.mrdaniel.npcs.exception;

import javax.annotation.Nonnull;

public class ConfigException extends Exception {

	private static final long serialVersionUID = -7613389271356385719L;

	public ConfigException(@Nonnull final String message) {
		super(message);
	}
}