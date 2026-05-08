package com.buuz135.soulplied_energistics.applied.strategies;

import appeng.api.behaviors.ExternalStorageStrategy;
import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.MEStorage;
import com.buuz135.industrialforegoingsouls.capabilities.ISoulHandler;
import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.buuz135.soulplied_energistics.applied.SoulKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public class SoulExternalStorageStrategy implements ExternalStorageStrategy {

    private ServerLevel level;
    private BlockPos fromPos;
    private Direction fromSide;

    public SoulExternalStorageStrategy(ServerLevel level, BlockPos fromPos, Direction fromSide) {
        this.level = level;
        this.fromPos = fromPos;
        this.fromSide = fromSide;
    }

    @Override
    public @Nullable MEStorage createWrapper(boolean b, Runnable runnable) {
        var cap = this.level.getCapability(SoulCapabilities.BLOCK, fromPos, fromSide);
        if (cap != null) {
            return new SoulLaserDrillMEStorage(cap, this.level, this.fromPos, this.fromSide, runnable);
        }
        runnable.run();
        return null;
    }

    private static class SoulLaserDrillMEStorage implements MEStorage {

        private final ServerLevel level;
        private final BlockPos pos;
        private final Direction side;
        private final Runnable onChange;

        SoulLaserDrillMEStorage(ISoulHandler baseBlock, ServerLevel level, BlockPos pos, Direction side, Runnable onChange) {
            this.level = level;
            this.pos = pos;
            this.side = side;
            this.onChange = onChange;
        }

        private ISoulHandler getCap() {
            return this.level.getCapability(SoulCapabilities.BLOCK, pos, side);
        }

        @Override
        public boolean isPreferredStorageFor(AEKey what, IActionSource source) {
            return MEStorage.super.isPreferredStorageFor(what, source);
        }

        @Override
        public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
            var cap = getCap();
            if (cap == null) { onChange.run(); return 0; }
            return cap.fill((int) amount, mode.isSimulate() ? ISoulHandler.Action.SIMULATE : ISoulHandler.Action.EXECUTE);
        }

        @Override
        public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
            var cap = getCap();
            if (cap == null) { onChange.run(); return 0; }
            return cap.drain((int) amount, mode.isSimulate() ? ISoulHandler.Action.SIMULATE : ISoulHandler.Action.EXECUTE);
        }

        @Override
        public void getAvailableStacks(KeyCounter out) {
            var cap = getCap();
            if (cap == null) { onChange.run(); return; }
            for (int i = 0; i < cap.getSoulTanks(); i++) {
                out.add(SoulKey.INSTANCE, cap.getSoulInTank(i));
            }
        }

        @Override
        public Component getDescription() {
            return Component.translatable("soulstorage.description");
        }

        @Override
        public KeyCounter getAvailableStacks() {
            return MEStorage.super.getAvailableStacks();
        }
    }
}