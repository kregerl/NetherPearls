package com.loucaskreger.netherpearls.world.teleporter;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TeleportContext {

	private final ServerWorld destWorld;
	private final ServerPlayerEntity player;
	private final BlockPos teleportPos;

	public TeleportContext(ServerWorld destWorld, ServerPlayerEntity player, BlockPos teleportPos) {
		this.destWorld = destWorld;
		this.player = player;
		this.teleportPos = teleportPos;
	}

	public ServerWorld getDestWorld() {
		return destWorld;
	}

	public ServerPlayerEntity getPlayer() {
		return player;
	}

	public BlockPos getTeleportPos() {
		return teleportPos;
	}

}
