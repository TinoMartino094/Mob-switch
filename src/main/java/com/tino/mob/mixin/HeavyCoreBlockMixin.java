package com.tino.mob.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import com.tino.mob.world.MobSwitchSavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HeavyCoreBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(HeavyCoreBlock.class)
public abstract class HeavyCoreBlockMixin extends Block {

    public HeavyCoreBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * When an Inhibitor Core is placed, register its position in the world
     * SavedData.
     * This is server-side only — no custom block entity required.
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level instanceof ServerLevel serverLevel) {
            if (stack.has(DataComponents.CUSTOM_DATA)) {
                CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
                if (customData.copyTag().contains("chunk_pylon")) {
                    MobSwitchSavedData.getServerState(serverLevel).addPylon(pos.immutable());
                }
            }
        }
    }

    /**
     * Called after the block is removed for any reason (player, explosion, command,
     * etc.).
     * Cleans up the SavedData entry so the chunk resumes normal spawning.
     */
    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (level instanceof ServerLevel serverLevel) {
            MobSwitchSavedData.getServerState(serverLevel).removePylon(pos);
        }
    }
}
