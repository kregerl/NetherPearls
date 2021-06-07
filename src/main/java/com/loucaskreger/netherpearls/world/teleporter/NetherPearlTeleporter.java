package com.loucaskreger.netherpearls.world.teleporter;

import java.util.function.Function;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class NetherPearlTeleporter implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		return repositionEntity.apply(false);
	}

	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld,
			Function<ServerWorld, PortalInfo> defaultPortalInfo) {
		return new PortalInfo(entity.position(), Vector3d.ZERO, entity.yRot, entity.xRot);
	}

	@Override
	public boolean isVanilla() {
		return false;
	}
}
