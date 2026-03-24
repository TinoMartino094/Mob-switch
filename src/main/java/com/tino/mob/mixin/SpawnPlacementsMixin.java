package com.tino.mob.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.tino.mob.world.MobSwitchSavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementsMixin {

    @Inject(method = "checkSpawnRules", at = @At("HEAD"), cancellable = true)
    private static <T extends Entity> void checkSpawnRules(EntityType<T> type, ServerLevelAccessor level,
            EntitySpawnReason spawnReason, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {

        // Only intercept natural and structure spawns; let spawners work normally
        if (spawnReason == EntitySpawnReason.NATURAL || spawnReason == EntitySpawnReason.STRUCTURE) {

            // Only block monsters; passive mobs are unaffected
            if (type.getCategory() == MobCategory.MONSTER) {

                ChunkPos chunkPos = ChunkPos.containing(pos);
                MobSwitchSavedData data = MobSwitchSavedData.getServerState(level.getLevel());

                if (data.hasPylonInChunk(chunkPos)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
