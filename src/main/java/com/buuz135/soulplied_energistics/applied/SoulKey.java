package com.buuz135.soulplied_energistics.applied;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import com.buuz135.soulplied_energistics.SoulpliedEnergistics;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SoulKey extends AEKey {

    public static ResourceLocation RL = ResourceLocation.fromNamespaceAndPath(SoulpliedEnergistics.MODID, "soulkey");

    public static final MapCodec<SoulKey> MAP_CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(Codec.STRING.fieldOf("uhhh_idk").forGetter(o -> ""))
                    .apply(builder, t1 -> new SoulKey())
    );
    public static final Codec<SoulKey> CODEC = MAP_CODEC.codec();


    @Override
    public AEKeyType getType() {
        return SoulAEKeyType.TYPE;
    }

    @Override
    public AEKey dropSecondary() {
        return this;
    }

    @Override
    public CompoundTag toTag(HolderLookup.Provider registries) {
        var ops = registries.createSerializationContext(NbtOps.INSTANCE);
        return (CompoundTag) CODEC.encodeStart(ops, this).getOrThrow();
    }

    @Override
    public Object getPrimaryKey() {
        return this;
    }

    @Override
    public ResourceLocation getId() {
        return RL;
    }

    @Override
    public void writeToPacket(RegistryFriendlyByteBuf data) {

    }

    @Override
    public String getModId() {
        return SoulpliedEnergistics.MODID;
    }

    @Override
    protected Component computeDisplayName() {
        return Component.translatable("soulkey.name");
    }

    @Override
    public void addDrops(long amount, List<ItemStack> drops, Level level, BlockPos pos) {
        // NO-OP
    }

    @Override
    public boolean hasComponents() {
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof SoulKey;
    }
}
