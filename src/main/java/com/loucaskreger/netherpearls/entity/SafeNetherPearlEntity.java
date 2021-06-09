package com.loucaskreger.netherpearls.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.loucaskreger.netherpearls.EventSubscriber;
import com.loucaskreger.netherpearls.init.ModEntityTypes;
import com.loucaskreger.netherpearls.init.ModItems;
import com.loucaskreger.netherpearls.world.teleporter.TeleportContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class SafeNetherPearlEntity extends ProjectileItemEntity {
	private static final RegistryKey<World> OVERWORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,
			DimensionType.OVERWORLD_EFFECTS);
	private static final RegistryKey<World> NETHER = RegistryKey.create(Registry.DIMENSION_REGISTRY,
			DimensionType.NETHER_EFFECTS);

	public SafeNetherPearlEntity(World world, LivingEntity livingEntity) {
		super(ModEntityTypes.SAFE_NETHER_PEARL.get(), livingEntity, world);
	}

	public SafeNetherPearlEntity(EntityType<? extends SafeNetherPearlEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.SAFE_NETHER_PEARL.get();
	}

	@Nonnull
	@Override
	public IPacket<?> getAddEntityPacket() {
		super.getAddEntityPacket();
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
		super.onHitEntity(entityRayTraceResult);
		entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}

	@Override
	protected void onHit(RayTraceResult rayTraceResult) {
		super.onHit(rayTraceResult);
		Entity entity = this.getOwner();

		for (int i = 0; i < 32; ++i) {
			this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D,
					this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}

		if (!this.level.isClientSide && this.isAlive()) {
			if (entity instanceof ServerPlayerEntity) {
				ServerPlayerEntity player = (ServerPlayerEntity) entity;
				if (player.connection.getConnection().isConnected() && player.level == this.level
						&& !player.isSleeping()) {
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
				}
				RegistryKey<World> currentDimension = player.getCommandSenderWorld().dimension();
				ServerWorld destWorld;
				BlockPos pos;
				if (currentDimension == OVERWORLD) {
					destWorld = player.getServer().getLevel(NETHER);
					pos = new BlockPos(this.getX() * 8, this.getY(), this.getZ() * 8);
				} else if (currentDimension == NETHER) {
					destWorld = player.getServer().getLevel(OVERWORLD);
					pos = new BlockPos(this.getX() / 8, this.getY(), this.getZ() / 8);
				} else {
					destWorld = null;
					pos = null;
				}

				if (destWorld != null) {
					// This is required since an entity cannot be modified during a tick, event
					String tag = null;
					if (this.getItem().getOrCreateTag().contains("block")) {
						tag = this.getItem().getOrCreateTag().getString("block");
					}

					EventSubscriber.stagedTeleports.add(new TeleportContext(destWorld, player, pos,
							tag != null ? ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag)) : null, true));
				}
			}
		}
		this.remove();
	}

	@Override
	public void tick() {
		Entity entity = this.getOwner();
		if (entity instanceof PlayerEntity && !entity.isAlive()) {
			this.remove();
		} else {
			super.tick();
		}

	}

	@Nullable
	@Override
	public Entity changeDimension(ServerWorld world, ITeleporter teleporter) {
		Entity entity = this.getOwner();
		if (entity != null && entity.level.dimension() != world.dimension()) {
			this.setOwner((Entity) null);
		}

		return super.changeDimension(world, teleporter);
	}
	
	
	
}
