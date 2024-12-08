package com.buuz135.soulplied_energistics.applied.strategies;

import appeng.api.behaviors.ContainerItemStrategy;
import appeng.api.config.Actionable;
import appeng.api.stacks.GenericStack;
import com.buuz135.industrial.item.LaserLensItem;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.soulplied_energistics.applied.SoulKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SoulContainerStrategy implements ContainerItemStrategy<SoulKey, ItemStack> {

    @Override
    public @Nullable GenericStack getContainedStack(ItemStack itemStack) {
        if (BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace().equals("industrialforegoingsouls")){
            return new GenericStack(new SoulKey(), 5);
        }
        return null;
    }

    @Override
    public @Nullable ItemStack findCarriedContext(Player player, AbstractContainerMenu abstractContainerMenu) {
        return abstractContainerMenu.getCarried();
    }

    @Override
    public long extract(ItemStack stack, SoulKey soulKey, long l, Actionable actionable) {
        return 0;
    }

    @Override
    public long insert(ItemStack stack, SoulKey soulKey, long l, Actionable actionable) {
        return 0;
    }

    @Override
    public void playFillSound(Player player, SoulKey soulKey) {

    }

    @Override
    public void playEmptySound(Player player, SoulKey soulKey) {

    }

    @Override
    public @Nullable GenericStack getExtractableContent(ItemStack stack) {
        return null;
    }
}
