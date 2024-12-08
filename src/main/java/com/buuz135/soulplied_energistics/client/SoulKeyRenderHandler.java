package com.buuz135.soulplied_energistics.client;

import appeng.api.client.AEKeyRenderHandler;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.client.SculkSoulTankScreenAddon;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulLaserBase;
import com.buuz135.soulplied_energistics.applied.SoulKey;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SoulKeyRenderHandler implements AEKeyRenderHandler<SoulKey> {

    private List<GuiParticle> particleList = new ArrayList<>();
    private long lastCheckedForParticle;

    public SoulKeyRenderHandler() {
        this.lastCheckedForParticle = 0;
    }

    @Override
    public void drawInGui(Minecraft minecraft, GuiGraphics guiGraphics, int x, int y, SoulKey soulKey) {
        guiGraphics.pose().pushPose();
        var warden_rl = ResourceLocation.withDefaultNamespace("textures/entity/warden/warden.png");
        var warden_hear = ResourceLocation.withDefaultNamespace("textures/entity/warden/warden_heart.png");
        guiGraphics.blit(warden_rl, x ,y , 12,14,16,16,128,128);

        guiGraphics.pose().pushPose();
        var heart_timing = 30f;
        heart_timing = 1 - ((Minecraft.getInstance().level.getGameTime() % heart_timing) / heart_timing);
        RenderSystem.setShaderColor(heart_timing, heart_timing, heart_timing, heart_timing);
        guiGraphics.blit(warden_hear, x - 1,y -1, 11,13,18,18,128,128);
        RenderSystem.setShaderColor(1,1,1, 1f);
        guiGraphics.pose().popPose();

        var rotation = Minecraft.getInstance().level.getGameTime() % 160 - 80;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y - 1, 100);
        guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(rotation));
        guiGraphics.blit(warden_rl, 0,0, 91,13,17,18,128,128);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x + 16,y + 17,100);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(180));
        guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(rotation));

        guiGraphics.blit(warden_rl, 0,0, 91,13,17,18,128,128);
        guiGraphics.pose().popPose();

        guiGraphics.pose().scale(0.75F, 0.75F, 0.75F);
        var fullAmount = 0.05;
        var xSize = 8;
        var ySize = 6;
        var currentTime = Minecraft.getInstance().level.getGameTime();
        if (this.lastCheckedForParticle != currentTime) {
            if (Minecraft.getInstance().level.random.nextDouble() <= fullAmount) {
                particleList.add(new GuiParticle(Minecraft.getInstance().level.random.nextInt(xSize), ySize  - Minecraft.getInstance().level.random.nextInt(3), currentTime));
            }
            this.lastCheckedForParticle = currentTime;
        }
        var ageTick = 3;
        if (currentTime % ageTick == 0) {
            particleList.removeIf(guiParticle -> ((currentTime - guiParticle.age) / ageTick) > 10);
        }
        guiGraphics.pose().translate(0,0,200);
        for (GuiParticle guiParticle : particleList.reversed()) {
            var particleAge = ((currentTime - guiParticle.age) / (double) ageTick);
            var extraY = ((ySize - 32) / 20D) * particleAge;
            guiGraphics.blit(ResourceLocation.withDefaultNamespace("textures/particle/sculk_soul_" + Math.max(0, Math.min(10, (int) particleAge)) + ".png"), (int) ((x + guiParticle.x) * (1/0.75f)), (int) ((int) (y + guiParticle.y + extraY) * (1/0.75f)), 0, 0, 16, 16, 16, 16);
        }
        guiGraphics.pose().popPose();
    }

    @Override
    public void drawOnBlockFace(PoseStack poseStack, MultiBufferSource multiBufferSource, SoulKey soulKey, float v, int i, Level level) {

    }

    @Override
    public Component getDisplayName(SoulKey soulKey) {
        return soulKey.getDisplayName();
    }

    private class GuiParticle {

        private int x;
        private int y;
        private long age;

        public GuiParticle(int x, int y, long age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }
}
