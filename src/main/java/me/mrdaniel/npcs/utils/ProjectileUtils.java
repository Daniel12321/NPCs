//package me.mrdaniel.npcs.utils;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.entity.projectile.Projectile;
//
//import com.flowpowered.math.vector.Vector3d;
//
//public class ProjectileUtils {
//
//	public static <T extends Projectile> T launch(@Nonnull final Player launcher, @Nonnull final Class<T> clazz, final double accuracyMultiplier) {
//
//		T projectile = launcher.launchProjectile(clazz).get();
//
//		double pitch = launcher.getHeadRotation().getX();
//		double yaw  = launcher.getHeadRotation().getY();
//
//		float f = 0.4F;
//        double x = (double) (-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI) * f);
//        double y = (double) (-Math.sin((pitch) / 180.0F * (float) Math.PI) * f);
//        double z = (double) (Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI) * f);
//
//        x = x - accuracyMultiplier + (Math.random() * accuracyMultiplier * 2);
//        y = y - accuracyMultiplier + (Math.random() * accuracyMultiplier * 2);
//        z = z - accuracyMultiplier + (Math.random() * accuracyMultiplier * 2);
//
//		Vector3d velocity = new Vector3d(x,y,z).mul(10);
//		projectile.setVelocity(velocity);
//
//		return projectile;
//	}
//}