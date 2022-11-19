package net.mcreator.hyderemods.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.hyderemods.particle.CompetenceactiverarmecummunParticle;
import net.mcreator.hyderemods.item.SwordBasicrItem;
import net.mcreator.hyderemods.HyderemodsMod;

import java.util.Map;

public class ShingToufieProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency world for procedure ShingToufie!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency x for procedure ShingToufie!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency y for procedure ShingToufie!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency z for procedure ShingToufie!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency entity for procedure ShingToufie!");
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
		for (int index0 = 0; index0 < (int) (15); index0++) {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(CompetenceactiverarmecummunParticle.particle, x, y, z, (int) 5, 3, 3, 3, 1);
			}
		}
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(SwordBasicrItem.block, (int) 600);
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
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SPEED, (int) 20, (int) 5));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) 20, (int) 1));
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\uFFFDbComp\uFFFDtence d'\uFFFDp\uFFFDe Utiliser"), (false));
				}
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, (int) 5);
	}
}
