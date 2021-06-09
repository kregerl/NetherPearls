package com.loucaskreger.netherpearls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.loucaskreger.netherpearls.init.ModEntityTypes;
import com.loucaskreger.netherpearls.init.ModItems;
import com.loucaskreger.netherpearls.recipe.SafeNetherPearlRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NetherPearls.MOD_ID)
public class NetherPearls {
	public static final String MOD_ID = "netherpearls";
	public static final Logger LOGGER = LogManager.getLogger();

	public NetherPearls() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setupCommon);
		bus.addListener(this::setupClient);
		bus.addGenericListener(IRecipeSerializer.class, this::onRegisterSerializers);
		ModItems.ITEMS.register(bus);
		ModEntityTypes.ENTITY_TYPES.register(bus);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
	}

	private void setupClient(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.NETHER_PEARL.get(),
				(manager) -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SAFE_NETHER_PEARL.get(),
				(manager) -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
	}

	public void onRegisterSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().register(
				SafeNetherPearlRecipe.SERIALIZER.setRegistryName(NetherPearls.MOD_ID, "safe_nether_pearl_recipe"));
	}

}
