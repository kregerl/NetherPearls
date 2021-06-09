package com.loucaskreger.netherpearls.init;

import com.loucaskreger.netherpearls.NetherPearls;
import com.loucaskreger.netherpearls.entity.NetherPearlEntity;
import com.loucaskreger.netherpearls.entity.SafeNetherPearlEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			NetherPearls.MOD_ID);

	public static final RegistryObject<EntityType<NetherPearlEntity>> NETHER_PEARL = ENTITY_TYPES.register(
			"nether_pearl",
			() -> EntityType.Builder.<NetherPearlEntity>of(NetherPearlEntity::new, EntityClassification.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
					.build(new ResourceLocation(NetherPearls.MOD_ID, "nether_pearl").toString()));

	public static final RegistryObject<EntityType<SafeNetherPearlEntity>> SAFE_NETHER_PEARL = ENTITY_TYPES.register(
			"safe_nether_pearl",
			() -> EntityType.Builder.<SafeNetherPearlEntity>of(SafeNetherPearlEntity::new, EntityClassification.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
					.build(new ResourceLocation(NetherPearls.MOD_ID, "safe_nether_pearl").toString()));
}
