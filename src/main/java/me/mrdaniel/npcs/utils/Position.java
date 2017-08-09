package me.mrdaniel.npcs.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Position {

	private final double x;
	private final double y;
	private final double z;
	private final float yaw;
	private final float pitch;
}