package com.buuz135.soulplied_energistics;

import appeng.api.behaviors.ContainerItemStrategy;
import appeng.api.behaviors.GenericSlotCapacities;
import appeng.api.behaviors.StackExportStrategy;
import appeng.api.stacks.AEKeyTypes;
import appeng.parts.automation.StackWorldBehaviors;
import com.buuz135.soulplied_energistics.applied.SoulAEKeyType;
import com.buuz135.soulplied_energistics.applied.SoulKey;
import com.buuz135.soulplied_energistics.applied.strategies.SoulContainerStrategy;
import com.buuz135.soulplied_energistics.applied.strategies.SoulExportStrategy;
import com.buuz135.soulplied_energistics.applied.strategies.SoulExternalStorageStrategy;

public class AppliedHelper {

    public static AppliedHelper INSTANCE = new AppliedHelper();

    public void init(){
        StackWorldBehaviors.registerExternalStorageStrategy(SoulAEKeyType.TYPE, SoulExternalStorageStrategy::new);
    }

    public void runRegister() {
        AEKeyTypes.register(SoulAEKeyType.TYPE);
        GenericSlotCapacities.register(SoulAEKeyType.TYPE, 16L);
        ContainerItemStrategy.register(SoulAEKeyType.TYPE, SoulKey.class, new SoulContainerStrategy());
    }

}
