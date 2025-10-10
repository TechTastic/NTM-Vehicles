package io.github.techtastic.ntm_vehicles;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import io.github.techtastic.ntm_vehicles.ntm_vehicles.Tags;
import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.entities.instances.EntityBullet;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
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
                Field field = clazz.getDeclaredField("worldWrappers");
                field.setAccessible(true);
                Map<World, WrapperWorld> wrappers = (Map<World, WrapperWorld>) field.get(world);
                for (Map.Entry<World, WrapperWorld> entry : wrappers.entrySet()) {
                    if (entry.getValue() == world) {
                        actualWorld = entry.getKey();
                        break;
                    }
                }
            } catch (Exception ignored) {}

            if (actualWorld == null)
                return;

            actualWorld.spawnEntity(EntityNukeExplosionMK5.statFac(actualWorld, 120, pos.x, pos.y, pos.z));
            EntityNukeTorex.statFac(actualWorld, pos.x, pos.y, pos.z, 120);
        });
    }

}
