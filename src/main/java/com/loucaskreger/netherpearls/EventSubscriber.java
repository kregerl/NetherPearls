package com.loucaskreger.netherpearls;

import java.util.ArrayList;
import java.util.List;
import com.loucaskreger.netherpearls.world.teleporter.NetherPearlTeleporter;
import com.loucaskreger.netherpearls.world.teleporter.TeleportContext;
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
		context.getDestWorld().getChunk(context.getTeleportPos());
		context.getPlayer().teleportTo(context.getTeleportPos().getX() + 0.5f, context.getTeleportPos().getY() + 1.0f,
				context.getTeleportPos().getZ() + 0.5f);
	}
}
