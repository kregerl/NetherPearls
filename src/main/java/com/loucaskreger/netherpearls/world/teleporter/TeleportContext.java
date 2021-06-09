package com.loucaskreger.netherpearls.world.teleporter;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TeleportContext {

	private final ServerWorld destWorld;
	private final ServerPlayerEntity player;
	private final BlockPos teleportPos;
	@Nullable
	private final Block block;
	private final boolean safe;

	public TeleportContext(ServerWorld destWorld, ServerPlayerEntity player, BlockPos teleportPos, Block block,
			boolean safe) {
		this.destWorld = destWorld;
		this.player = player;
		this.teleportPos = teleportPos;
		this.block = block;
		this.safe = safe;
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

	@Nullable
	public Block getBlock() {
		return this.block;
	}

	public boolean getSafe() {
		return this.safe;
	}

}
