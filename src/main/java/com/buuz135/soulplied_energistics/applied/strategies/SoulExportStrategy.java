package com.buuz135.soulplied_energistics.applied.strategies;

import appeng.api.behaviors.StackExportStrategy;
import appeng.api.behaviors.StackTransferContext;
import appeng.api.config.Actionable;
import appeng.api.stacks.AEKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;

public class SoulExportStrategy implements StackExportStrategy {

    public SoulExportStrategy(ServerLevel level, BlockPos fromPos, Direction fromSide) {
    }

    @Override
    public long transfer(StackTransferContext stackTransferContext, AEKey aeKey, long l) {
        return 0;
    }

    @Override
    public long push(AEKey aeKey, long l, Actionable actionable) {
        return 0;
    }
}
