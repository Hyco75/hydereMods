package net.mcreator.hyderemods.procedures;

import net.minecraft.entity.Entity;

import net.mcreator.hyderemods.HyderemodsMod;

import java.util.Map;

public class SpawntProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				HyderemodsMod.LOGGER.warn("Failed to load dependency entity for procedure Spawnt!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		{
			Entity _ent = entity;
			if (!_ent.world.isRemote && _ent.world.getServer() != null) {
				_ent.world.getServer().getCommandManager().handleCommand(_ent.getCommandSource().withFeedbackDisabled().withPermissionLevel(4),
						"tp @s -254 7 -63");
			}
		}
	}
}
