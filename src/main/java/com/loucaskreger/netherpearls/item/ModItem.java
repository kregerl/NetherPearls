package com.loucaskreger.netherpearls.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ModItem extends Item {

	public ModItem() {
		super(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_MISC));
	}

}
