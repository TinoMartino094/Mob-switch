package com.tino.mob.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class MobSwitchSavedData extends SavedData {

    private static final String DATA_NAME = "mob_switch_data";

    public static final Codec<MobSwitchSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().optionalFieldOf("pylons", List.of())
                    .forGetter(data -> new ArrayList<>(data.pylonPositions)))
            .apply(instance, positions -> {
                MobSwitchSavedData data = new MobSwitchSavedData();
                data.pylonPositions.addAll(positions);
                return data;
            }));

    private final Set<BlockPos> pylonPositions = new HashSet<>();

    public void addPylon(BlockPos pos) {
        this.pylonPositions.add(pos.immutable());
        this.setDirty();
    }

    public void removePylon(BlockPos pos) {
        this.pylonPositions.remove(pos);
        this.setDirty();
    }

    public boolean hasPylonAt(BlockPos pos) {
        return this.pylonPositions.contains(pos);
    }

    public boolean hasPylonInChunk(ChunkPos chunkPos) {
        int minX = chunkPos.getMinBlockX();
        int minZ = chunkPos.getMinBlockZ();
        int maxX = chunkPos.getMaxBlockX();
        int maxZ = chunkPos.getMaxBlockZ();

        for (BlockPos pos : this.pylonPositions) {
            if (pos.getX() >= minX && pos.getX() <= maxX && pos.getZ() >= minZ && pos.getZ() <= maxZ) {
                return true;
            }
        }
        return false;
    }

    public static SavedDataType<MobSwitchSavedData> type() {
        return new SavedDataType<>(
                Identifier.fromNamespaceAndPath("mob-switch", DATA_NAME),
                MobSwitchSavedData::new,
                CODEC,
                DataFixTypes.SAVED_DATA_MAP_DATA);
    }

    public static MobSwitchSavedData getServerState(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(type());
    }
}
