package com.buuz135.soulplied_energistics.applied;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import com.buuz135.soulplied_energistics.SoulpliedEnergistics;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SoulAEKeyType extends AEKeyType {

    public static final SoulAEKeyType TYPE = new SoulAEKeyType();

    public SoulAEKeyType() {
        super(ResourceLocation.fromNamespaceAndPath(SoulpliedEnergistics.MODID, "soul"), SoulKey.class, Component.translatable("soulkey.description"));
    }

    @Override
    public MapCodec<? extends AEKey> codec() {
        return SoulKey.MAP_CODEC;
    }

    @Override
    public @Nullable AEKey readFromPacket(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        return new SoulKey();
    }

    @Override
    public int getAmountPerByte() {
        return 1;
    }
}
