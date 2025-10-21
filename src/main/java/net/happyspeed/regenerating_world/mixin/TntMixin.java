package net.happyspeed.regenerating_world.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimedTnt.class)
public abstract class TntMixin extends Entity {

    @Shadow @javax.annotation.Nullable private LivingEntity owner;

    public TntMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    public void injectExplosion(CallbackInfo ci) {
        this.explodeCircular(this.level(), this.getOnPos(), 3.5f, this.owner);
        ci.cancel();
    }


    @Unique
    public void explodeCircular(Level level, BlockPos center, float radius, @Nullable LivingEntity source) {
        // Create a vanilla-style explosion with particles and sound
        this.level()
                .explode(
                        this,
                        Explosion.getDefaultDamageSource(this.level(), this),
                        null,
                        this.getX(),
                        this.getY(0.0625),
                        this.getZ(),
                        radius,
                        false,
                        Level.ExplosionInteraction.TRIGGER
                );

        // Manually process blocks in a sphere
        for (int x = -Math.round(radius); x <= Math.round(radius); x++) {
            for (int y = -Math.round(radius); y <= Math.round(radius); y++) {
                for (int z = -Math.round(radius); z <= Math.round(radius); z++) {
                    BlockPos pos = center.offset(x, y, z);
                    double distSq = center.distSqr(pos);
                    if (distSq <= radius * radius) {
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir()) {
                            float hardness = state.getDestroySpeed(level, pos);
                            if (hardness >= 0 && hardness <= 5.0F) { // Mimic TNT's effective range
                                if (state.is(Blocks.TNT)) {
                                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                                    PrimedTnt tnt = new PrimedTnt(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, source);
                                    tnt.setFuse(20 + level.random.nextInt(10)); // Slight fuse variation
                                    level.addFreshEntity(tnt);
                                } else {
                                    Block.dropResources(state, level, pos);
                                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
