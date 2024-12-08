package com.buuz135.soulplied_energistics;

import appeng.api.AECapabilities;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.buuz135.industrialforegoingsouls.capabilities.SLBSoulCap;
import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.buuz135.soulplied_energistics.cap.InterfaceSoulCap;
import com.buuz135.soulplied_energistics.client.ClientAppliedHelper;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SoulpliedEnergistics.MODID)
public class SoulpliedEnergistics {

    /**
     * Clean Code
     * Localization
     * Add tooltips
     * Change interface filtering to use souls items
     */

    public static final String MODID = "soulplied_energistics";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SoulpliedEnergistics(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener((RegisterEvent e) -> {
            if (e.getRegistryKey().equals(Registries.BLOCK)) {
                AppliedHelper.INSTANCE.runRegister();
            }
        });
        EventManager.mod(RegisterCapabilitiesEvent.class).process(event -> {
            for (var block : BuiltInRegistries.BLOCK) {
                if (event.isBlockRegistered(AECapabilities.GENERIC_INTERNAL_INV, block)) {
                    event.registerBlock(SoulCapabilities.BLOCK, (level, pos, state, tile, side) -> {
                                var genericInv = level.getCapability(AECapabilities.GENERIC_INTERNAL_INV, pos, state, tile, side);
                                if (genericInv != null) {
                                    return new InterfaceSoulCap(genericInv);
                                }
                                return null;
                            },
                            block
                    );
                }
            }
        }).subscribe();
        EventManager.forge(ItemTooltipEvent.class).process(event -> {
            if (BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).getNamespace().equals("industrialforegoingsouls")){
                event.getToolTip().add(Component.translatable("soulpliedenergistics.can_be_used_filter").withStyle(ChatFormatting.GRAY));
            }
            if (event.getItemStack().getItem().equals(IndustrialForegoingSouls.SOUL_LASER_BLOCK.asItem())){
                event.getToolTip().add(Component.translatable("soulpliedenergistics.storage_bus").withStyle(ChatFormatting.GRAY));

            }
        }).subscribe();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AppliedHelper.INSTANCE.init();
    }


    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ClientAppliedHelper.INSTANCE.init();
        }
    }
}
