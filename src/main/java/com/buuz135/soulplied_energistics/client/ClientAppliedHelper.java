package com.buuz135.soulplied_energistics.client;

import appeng.api.client.AEKeyRendering;
import com.buuz135.soulplied_energistics.applied.SoulAEKeyType;
import com.buuz135.soulplied_energistics.applied.SoulKey;

public class ClientAppliedHelper {

    public static ClientAppliedHelper INSTANCE = new ClientAppliedHelper();

    public void init() {
        AEKeyRendering.register(SoulAEKeyType.TYPE, SoulKey.class, new SoulKeyRenderHandler());
    }
}
