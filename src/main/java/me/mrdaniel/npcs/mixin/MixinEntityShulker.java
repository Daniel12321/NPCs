package me.mrdaniel.npcs.mixin;

import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityShulker;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityShulker.class)
public abstract class MixinEntityShulker extends EntityGolem implements IMixinEntityShulker {

    @Shadow @Final protected static DataParameter<Byte> PEEK_TICK;
    @Shadow @Final protected static DataParameter<Byte> COLOR;

    public MixinEntityShulker(World worldIn) {
        super(worldIn);
    }

    @Override
    public void setColor(EnumDyeColor color) {
        this.dataManager.set(COLOR, (byte)color.getMetadata());
    }

    @Override
    public void setPeek(boolean value) {
        this.dataManager.set(PEEK_TICK, (value ? Byte.MAX_VALUE : 0));
    }
}
