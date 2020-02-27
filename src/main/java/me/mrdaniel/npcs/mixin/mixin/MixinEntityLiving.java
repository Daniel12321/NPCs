package me.mrdaniel.npcs.mixin.mixin;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.ai.AITaskStayInPosition;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(value = EntityLiving.class, priority = 10)
public abstract class MixinEntityLiving extends EntityLivingBase implements NPCAble {

	private INPCData data;

	public MixinEntityLiving(World worldIn) {
		super(worldIn);

		this.data = null;
	}

	@Shadow public abstract void enablePersistence();
	@Shadow public abstract void setCanPickUpLoot(boolean canPickup);
	@Shadow public abstract void setNoAI(boolean disable);

	@Nullable
	@Override
	public INPCData getData() {
		return this.data;
	}

	@Override
	public void setData(INPCData data) {
		this.data = data;

		this.enablePersistence();
		this.setCanPickUpLoot(false);
		this.setNoAI(false);
		this.setDropItemsWhenDead(false);
		this.setEntityInvulnerable(true);
		this.setNoGravity(false);

		Position pos = this.data.getPosition();
		this.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());

		PropertyTypes.NPC_INIT.stream()
				.filter(prop -> prop.isSupported(this))
				.forEach(prop -> this.data.getProperty(prop)
						.ifPresent(value -> prop.apply(this, value)));

		Agent agent = (Agent) this;
		agent.setTarget(null);
		agent.getGoal(GoalTypes.TARGET).ifPresent(Goal::clear);
		agent.getGoal(GoalTypes.NORMAL).ifPresent(goal -> {
			goal.clear();
			goal.addTask(1, new AITaskStayInPosition());
		});
	}

	@Override
	public void refreshEquipment() {
		PropertyTypes.EQUIPMENT.forEach(prop -> prop.apply(this, null));

		Task.builder().delayTicks(5).execute(() -> {
			PropertyTypes.EQUIPMENT.forEach(prop -> this.data.getProperty(prop).ifPresent(is -> prop.apply(this, is)));
		}).submit(NPCs.getInstance());
	}

	@Override
	public void setLooking(boolean value) {
		Agent agent = (Agent) this;

//		agent.getGoal(GoalTypes.NORMAL).ifPresent(goal -> {
//			goal.removeTasks(AITaskTypes.WATCH_CLOSEST);
//			if (value) {
//				goal.addTask(10, WatchClosestAITask.builder().chance(1).maxDistance(25).watch(Player.class).build(agent));
//			}
//		});
	}

	@Override
	public INPCData setPosition(Position value) {
		this.setPositionAndRotation(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
		return this.data.setPosition(value);
	}

	@Override
	public <T> INPCData setProperty(PropertyType<T> property, T value) {
		this.data.setProperty(property, value);
		property.apply(this, value);
		return this.data;
	}
}
