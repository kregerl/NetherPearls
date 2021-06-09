package com.loucaskreger.netherpearls.item;

import java.util.List;
import com.loucaskreger.netherpearls.entity.SafeNetherPearlEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class SafeNetherPearlItem extends ModItem {

	public SafeNetherPearlItem() {
		super();
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
				SoundEvents.ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		playerEntity.getCooldowns().addCooldown(this, 20);
		if (!world.isClientSide) {
			SafeNetherPearlEntity netherPearlEntity = new SafeNetherPearlEntity(world, playerEntity);
			netherPearlEntity.setItem(itemstack);
			netherPearlEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(netherPearlEntity);

		}

		playerEntity.awardStat(Stats.ITEM_USED.get(this));
		if (!playerEntity.abilities.instabuild) {
			itemstack.shrink(1);
		}

		return ActionResult.sidedSuccess(itemstack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		CompoundNBT nbt = stack.getOrCreateTag();
		ResourceLocation loc = new ResourceLocation(nbt.getString("block"));
		ITextComponent textComponent = new TranslationTextComponent("contained.block").append(" ")
				.append(ForgeRegistries.BLOCKS.getValue(loc).getName()).withStyle(TextFormatting.GOLD);
		tooltip.add(textComponent);
	}

}
