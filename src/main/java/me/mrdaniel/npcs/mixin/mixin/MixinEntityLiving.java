package me.mrdaniel.npcs.mixin.mixin;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.api.entity.ai.Goal;
import org.spongepowered.api.entity.ai.GoalTypes;
import org.spongepowered.api.entity.ai.task.builtin.WatchClosestAITask;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(value = EntityLiving.class, priority = 10)
public abstract class MixinEntityLiving extends EntityLivingBase implements NPCAble {

	@Nullable
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

		// TODO: Improve
		if ((Object)this instanceof EntityCreature) {
			((EntityCreature)(Object)this).setHomePosAndDistance(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 5);
		}

		PropertyTypes.NPC_INIT.stream()
				.filter(prop -> prop.isSupported(this))
				.forEach(prop -> this.data.getProperty(prop)
						.ifPresent(value -> prop.apply(this, value)));

		this.refreshAI();
	}

	@Override
	public void refreshEquipment() {
		PropertyTypes.EQUIPMENT.forEach(prop -> prop.apply(this, null));

		Task.builder().delayTicks(5).execute(() -> {
			PropertyTypes.EQUIPMENT.forEach(prop -> this.data.getProperty(prop).ifPresent(is -> prop.apply(this, is)));
		}).submit(NPCs.getInstance());
	}

	@Override
	public void refreshAI() {
		NPCType type = this.data.getProperty(PropertyTypes.TYPE).get();
		AIType aiType = this.data.getProperty(PropertyTypes.AITYPE).orElse(AITypes.STAY);
		Agent agent = (Agent) this;

		agent.setTarget(null);
		agent.getGoal(GoalTypes.TARGET).ifPresent(Goal::clear);
		agent.getGoal(GoalTypes.NORMAL).ifPresent(goal -> {
			goal.clear();

			goal.addTask(1, aiType.createAITask((Creature)this, type));

			if (this.data.getProperty(PropertyTypes.LOOKING).orElse(false)) {
				goal.addTask(10, WatchClosestAITask.builder().chance(1).maxDistance(25).watch(Player.class).build(agent));
			}

			// TODO: Remove
//			if (type == NPCTypes.SKELETON) {
//				List<Position> targets = Lists.newArrayList(
//						new Position("world", -40.5, 63.0, 330.5, 0, 0),
//						new Position("world", -30.5, 63.0, 330.5, 0, 0),
//						new Position("world", -30.5, 63.0, 320.5, 0, 0),
//						new Position("world", -40.5, 63.0, 320.5, 0, 0)
//				);
//				goal.addTask(1, new AITaskGuardRandom(targets, type.getMovementSpeed()));
//			}
//			else if (type == NPCTypes.ZOMBIE) {
//				List<Position> targets = Lists.newArrayList(
//						new Position("world", -40.5, 63.0, 330.5, 0, 0),
//						new Position("world", -30.5, 63.0, 330.5, 0, 0),
//						new Position("world", -30.5, 63.0, 320.5, 0, 0),
//						new Position("world", -40.5, 63.0, 320.5, 0, 0)
//				);
//				goal.addTask(1, new AITaskPatrol(targets, type.getMovementSpeed()));
//			}
//			else if (type == NPCTypes.CREEPER) {
//				Position pos = this.data.getPosition();
//				((EntityCreature)(Object)this).setHomePosAndDistance(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 5);
//				goal.addTask(1, WanderAITask.builder().speed(type.getMovementSpeed()).executionChance(50).build((Creature)this));
//			}
//			else {
//				goal.addTask(1, new AITaskStayInPosition(type.getMovementSpeed()));
//			}
		});
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
