package me.mrdaniel.npcs.mixin.mixin;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.ai.pattern.AIPatternStay;
import me.mrdaniel.npcs.ai.pattern.AIPatternWander;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
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

		Position pos = this.data.getProperty(PropertyTypes.POSITION).get();
		this.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());

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
        AbstractAIPattern aiPattern = this.data.getProperty(PropertyTypes.AI_PATTERN).orElse(new AIPatternStay(this.data.getProperty(PropertyTypes.TYPE).get()));
		Agent agent = (Agent) this;

        if (aiPattern instanceof AIPatternWander) {
            Position pos = this.data.getProperty(PropertyTypes.POSITION).get();
            ((EntityCreature)(Object)this).setHomePosAndDistance(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), ((AIPatternWander)aiPattern).getDistance());
        }

        agent.setTarget(null);
		agent.getGoal(GoalTypes.TARGET).ifPresent(Goal::clear);
		agent.getGoal(GoalTypes.NORMAL).ifPresent(goal -> {
			goal.clear();
			goal.addTask(1, aiPattern.createAITask((Creature)this));

			if (this.data.getProperty(PropertyTypes.LOOKING).orElse(false)) {
				goal.addTask(10, WatchClosestAITask.builder().chance(1).maxDistance(25).watch(Player.class).build(agent));
			}
		});
	}

	@Override
	public <T> INPCData setProperty(PropertyType<T> property, T value) {
		this.data.setProperty(property, value);
		property.apply(this, value);
		return this.data;
	}
}
