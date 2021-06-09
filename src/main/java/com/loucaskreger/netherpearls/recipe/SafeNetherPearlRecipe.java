package com.loucaskreger.netherpearls.recipe;

import com.google.gson.JsonObject;
import com.loucaskreger.netherpearls.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SafeNetherPearlRecipe extends SpecialRecipe {

	public static final SafeNetherPearlSerializer SERIALIZER = new SafeNetherPearlSerializer();
	private static final int PEARL_INDEX = 4;
	private static final int BLOCK_INDEX = 7;
	private static final int[] WART_INDICIES = new int[] { 1, 3, 5 };

	public SafeNetherPearlRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		ItemStack stack = inv.getItem(PEARL_INDEX);
		ItemStack stack2 = inv.getItem(BLOCK_INDEX);
		if (stack.getItem() == ModItems.NETHER_PEARL.get()) {
			if (stack2.getItem() instanceof BlockItem) {
				int count = 0;
				for (int i = 0; i < 3; i++) {
					if (inv.getItem(WART_INDICIES[i]).getItem() == Items.NETHER_WART) {
						count++;
					}
				}
				return count == 3;
			}
		}
		return false;
	}

	@Override
	public ItemStack assemble(CraftingInventory inv) {
		ItemStack stack = inv.getItem(BLOCK_INDEX);
		if (stack.getItem() instanceof BlockItem) {
			ItemStack result = new ItemStack(ModItems.SAFE_NETHER_PEARL.get());
			Block block = ((BlockItem) stack.getItem()).getBlock();
			CompoundNBT nbt = result.getOrCreateTag();
			nbt.putString("block", block.getRegistryName().toString());
			return result;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width == 3 && height == 3;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static class SafeNetherPearlSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<SafeNetherPearlRecipe> {

		@Override
		public SafeNetherPearlRecipe fromJson(ResourceLocation id, JsonObject json) {
			return new SafeNetherPearlRecipe(id);
		}

		@Override
		public SafeNetherPearlRecipe fromNetwork(ResourceLocation id, PacketBuffer json) {
			return new SafeNetherPearlRecipe(id);
		}

		@Override
		public void toNetwork(PacketBuffer p_199427_1_, SafeNetherPearlRecipe p_199427_2_) {

		}

	}

}
