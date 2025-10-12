package io.github.techtastic.ntm_vehicles;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import io.github.techtastic.ntm_vehicles.ntm_vehicles.Tags;
import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.entities.instances.EntityBullet;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "required-after:mts@[22.18.0,);required-after:hbm;required-after:mixinbooter", acceptableRemoteVersions = "*")
public class NTMVehicles {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        EntityBullet.registerCustomHitFunction("ntm_vehicles:nuke", (world, pos, side, type) -> {
            World actualWorld = null;
            try {
                Class<WrapperWorld> clazz = WrapperWorld.class;
                Field field = clazz.getDeclaredField("world");
                field.setAccessible(true);
                actualWorld = (World) field.get(world);
            } catch (Exception ignored) {}

            if (actualWorld == null)
                return;

            actualWorld.spawnEntity(EntityNukeExplosionMK5.statFac(actualWorld, actualWorld.getGameRules().getInt("ivNukeBlastRadius"), pos.x, pos.y, pos.z));
            EntityNukeTorex.statFac(actualWorld, pos.x, pos.y, pos.z, actualWorld.getGameRules().getInt("ivNukeBlastRadius"));
        });
    }

    @Mod.EventHandler
    public void worldInit(WorldEvent event) {
        if (!event.getWorld().getGameRules().hasRule("ivNukeBlastRadius"))
            event.getWorld().getGameRules().addGameRule("ivNukeBlastRadius", "120", GameRules.ValueType.NUMERICAL_VALUE);
    }

}
