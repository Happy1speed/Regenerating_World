package net.happyspeed.regenerating_world.entity.renderers;

import net.happyspeed.regenerating_world.entity.ReseedProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ReseedProjectileRenderer extends ThrownItemRenderer<ReseedProjectileEntity> {
    public ReseedProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0f, true);
    }
}
