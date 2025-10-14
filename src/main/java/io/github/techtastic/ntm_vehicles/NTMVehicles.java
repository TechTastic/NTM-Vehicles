package io.github.techtastic.ntm_vehicles;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionChaos;
import io.github.techtastic.ntm_vehicles.ntm_vehicles.Tags;
import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.entities.instances.EntityBullet;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

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
        EntityBullet.registerCustomHitFunction("ntm_vehicles:nuke", (world, pos, side, type, bullet) -> {
            World actualWorld = null;
            //INuclear nuke = null;
            try {
                Class<WrapperWorld> clazz = WrapperWorld.class;
                Field field = clazz.getDeclaredField("world");
                field.setAccessible(true);
                actualWorld = (World) field.get(world);
                //nuke = (INuclear) bullet.definition.bullet;
            } catch (Exception ignored) {}

            if (actualWorld == null)
                return;

            // y = ((x/0.28)^3)/1000
            // 1000y = (x/0.28)^3
            // (1000y)^1/3 = x/0.28
            // 0.28 * ((1000y)^1/3) = x

            //int blastRadius = (int) (Math.pow(bullet.definition.bullet.blastStrength * 1000, 1d/3) * 0.28);

            actualWorld.spawnEntity(EntityNukeExplosionMK5.statFac(actualWorld, (int) bullet.definition.bullet.blastStrength, pos.x, pos.y, pos.z));
            EntityNukeTorex.statFac(actualWorld, pos.x, pos.y, pos.z, (int) bullet.definition.bullet.blastStrength);
        });

        EntityBullet.registerCustomHitFunction("nym_vehicles:chlorine", (world, pos, side, type, bullet) -> {
            World actualWorld = null;
            try {
                Class<WrapperWorld> clazz = WrapperWorld.class;
                Field field = clazz.getDeclaredField("world");
                field.setAccessible(true);
                actualWorld = (World) field.get(world);
            } catch (Exception ignored) {}

            if (actualWorld == null)
                return;

            ExplosionChaos.spawnChlorine(actualWorld, pos.x, pos.y, pos.z, 50, 1.25, 0);
        });
    }
}