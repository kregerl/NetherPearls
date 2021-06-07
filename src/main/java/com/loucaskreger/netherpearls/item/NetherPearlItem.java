package com.loucaskreger.netherpearls.item;

import com.loucaskreger.netherpearls.entity.NetherPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class NetherPearlItem extends Item {

	public NetherPearlItem() {
		super(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_MISC));
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
				SoundEvents.ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		playerEntity.getCooldowns().addCooldown(this, 20);
		if (!world.isClientSide) {
			NetherPearlEntity netherPearlEntity = new NetherPearlEntity(world, playerEntity);
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

}