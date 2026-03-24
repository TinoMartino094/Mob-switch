package com.tino.mob.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.tino.mob.world.MobSwitchSavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin {

    @Inject(method = "isPushable", at = @At("RETURN"), cancellable = true)
    private static void isPushable(BlockState state, Level level, BlockPos pos, Direction direction,
            boolean allowDestroyable, Direction connectionDirection, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(Blocks.HEAVY_CORE) && level instanceof ServerLevel serverLevel) {
            if (MobSwitchSavedData.getServerState(serverLevel).hasPylonAt(pos)) {
                // Inhibitor Core: prevent the piston from pushing it
                cir.setReturnValue(false);
            }
            // Regular Heavy Core: let vanilla logic decide (don't override)
        }
    }
}
