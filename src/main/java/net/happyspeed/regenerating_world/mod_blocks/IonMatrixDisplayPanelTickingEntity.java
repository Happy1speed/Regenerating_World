package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.happyspeed.regenerating_world.sounds.ModSounds;
import net.happyspeed.regenerating_world.util.ModTags;
import net.happyspeed.regenerating_world.util.TagSearcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IonMatrixDisplayPanelTickingEntity extends BlockEntity {


    public IonMatrixDisplayPanelTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.ION_STATUS_BOARD_BLOCK_ENTITY.get(), pos, state);
    }

    int soundTime = 199;

    public static void tick(Level level, BlockPos pos, BlockState state, IonMatrixDisplayPanelTickingEntity entity) {
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                entity.soundTime++;
                if (entity.soundTime >= 200) {
                    serverLevel.playSound(
                            null,
                            pos,
                            ModSounds.getRandomDataSound(level.getRandom()),
                            SoundSource.BLOCKS,
                            0.5F,
                            1.0f
                    );
                    entity.soundTime = 0;
                }
            }
        }
    }

}
