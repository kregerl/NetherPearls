package com.loucaskreger.netherpearls;

import java.util.ArrayList;
import java.util.List;

import com.loucaskreger.netherpearls.world.teleporter.NetherPearlTeleporter;
import com.loucaskreger.netherpearls.world.teleporter.TeleportContext;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherPearls.MOD_ID)
public class EventSubscriber {

	public static List<TeleportContext> stagedTeleports = new ArrayList<TeleportContext>();
	private static final NetherPearlTeleporter TELEPORTER = new NetherPearlTeleporter();

	@SubscribeEvent
	public static void onServerTick(final ServerTickEvent event) {
		if (event.phase == Phase.END) {
			for (TeleportContext ctx : stagedTeleports) {
				teleport(ctx);
			}
			stagedTeleports.clear();
		}
	}

	private static void teleport(final TeleportContext context) {
		if (context.getPlayer().canChangeDimensions()) {
			context.getPlayer().changeDimension(context.getDestWorld(), TELEPORTER);
		}
		if (context.getSafe()) {
			context.getDestWorld().setBlock(context.getTeleportPos(), context.getBlock().defaultBlockState(), 2);
			for (int i = 1; i <= 2; i++) {
				context.getDestWorld().setBlock(context.getTeleportPos().offset(new Vector3i(0, i, 0)),
						Blocks.AIR.getBlock().defaultBlockState(), 2);
			}
		}

		context.getDestWorld().getChunk(context.getTeleportPos());
		context.getPlayer().teleportTo(context.getTeleportPos().getX() + 0.5f, context.getTeleportPos().getY() + 1.0f,
				context.getTeleportPos().getZ() + 0.5f);

	}

}
