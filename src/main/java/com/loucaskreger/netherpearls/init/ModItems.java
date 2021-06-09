package com.loucaskreger.netherpearls.init;

import com.loucaskreger.netherpearls.NetherPearls;
import com.loucaskreger.netherpearls.item.NetherPearlItem;
import com.loucaskreger.netherpearls.item.SafeNetherPearlItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			NetherPearls.MOD_ID);

	public static final RegistryObject<Item> NETHER_PEARL = ITEMS.register("nether_pearl", NetherPearlItem::new);
	public static final RegistryObject<Item> SAFE_NETHER_PEARL = ITEMS.register("safe_nether_pearl", SafeNetherPearlItem::new);
}
