package net.happyspeed.regenerating_world.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;

public class AuraParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    protected AuraParticle(ClientLevel level, double x, double y, double z,
                           double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;

        this.lifetime = 120 + level.random.nextInt(20); // 2–3 seconds
        this.gravity = 0.0f;
        this.hasPhysics = true;
        this.alpha = 0.9f;
        this.quadSize *= 1.5f;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();

        // Fade out over time

        float progress = (float) this.age / this.lifetime;
        this.alpha = 0.8f * (1.0f - progress * progress);
        //QUADRATIC


        this.setSpriteFromAge(spriteSet);

        if (this.age > (this.lifetime / 2)) {
            this.quadSize *= 0.98f;
        }

        this.xd += 0.01;
        this.zd += 0.01;

        this.yd += (this.level.random.nextFloat() - 0.5f) * 0.1f;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
