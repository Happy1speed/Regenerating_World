package net.happyspeed.regenerating_world.mixin;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockStateProperties.class)
public class BlockStatePropertiesMixin {
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 7, ordinal = 1))
    private static int getAndEdit(int constant) {
        return 15;
    }
}
