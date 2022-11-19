package net.mcreator.hyderemods.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.Explosion;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.hyderemods.item.ArtefactedetestItem;
import net.mcreator.hyderemods.HyderemodsMod;

import java.util.Map;

public class ArtefactedetestLorsDunClicDroitDansLairProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency world for procedure ArtefactedetestLorsDunClicDroitDansLair!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency x for procedure ArtefactedetestLorsDunClicDroitDansLair!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency y for procedure ArtefactedetestLorsDunClicDroitDansLair!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency z for procedure ArtefactedetestLorsDunClicDroitDansLair!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency entity for procedure ArtefactedetestLorsDunClicDroitDansLair!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof World && !world.isRemote()) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("hyderemods:competence_activation")),
					SoundCategory.NEUTRAL, (float) 1, (float) 1);
		} else {
			((World) world).playSound(x, y, z,
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("hyderemods:competence_activation")),
					SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
		}
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(ArtefactedetestItem.block, (int) 250);
		new Object() {
			private int ticks = 0;
			private float waitTicks;
			private IWorld world;

			public void start(IWorld world, int waitTicks) {
				this.waitTicks = waitTicks;
				MinecraftForge.EVENT_BUS.register(this);
				this.world = world;
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks)
						run();
				}
			}

			private void run() {
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, (int) 60, (int) 255));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.LEVITATION, (int) 10, (int) 1));
				if (world instanceof World && !((World) world).isRemote) {
					((World) world).createExplosion(null, (int) x, (int) y, (int) z, (float) 4, Explosion.Mode.NONE);
				}
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, (int) 65, (int) 2));
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, (int) 40);
		new Object() {
			private int ticks = 0;
			private float waitTicks;
			private IWorld world;

			public void start(IWorld world, int waitTicks) {
				this.waitTicks = waitTicks;
				MinecraftForge.EVENT_BUS.register(this);
				this.world = world;
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks)
						run();
				}
			}

			private void run() {
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).clearActivePotions();
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, (int) 66);
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\uFFFDc*Art\uFFFDfacte Utilis\uFFFDe*"), (false));
		}
	}
}
