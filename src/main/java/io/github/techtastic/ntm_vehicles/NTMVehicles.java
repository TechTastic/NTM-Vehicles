package io.github.techtastic.ntm_vehicles;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionChaos;
import io.github.techtastic.ntm_vehicles.ntm_vehicles.Tags;
import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.entities.instances.EntityBullet;
import minecrafttransportsimulator.jsondefs.JSONVariableModifier;
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
            World actualWorld = Util.getWorld((WrapperWorld) world);

            if (actualWorld == null)
                return;

            actualWorld.spawnEntity(EntityNukeExplosionMK5.statFac(actualWorld, (int) Util.getBlastSize(bullet.definition), pos.x, pos.y, pos.z));
            EntityNukeTorex.statFac(actualWorld, pos.x, pos.y, pos.z, (int) Util.getBlastSize(bullet.definition));
        });

        EntityBullet.registerCustomHitFunction("ntm_vehicles:chlorine", (world, pos, side, type, bullet) -> {
            World actualWorld = Util.getWorld((WrapperWorld) world);

            if (actualWorld == null)
                return;

            JSONVariableModifier speedVar = Util.getVariableModifier(bullet.definition, "gasSpreadSpeed");
            JSONVariableModifier gasTypeVar = Util.getVariableModifier(bullet.definition, "gasType");
            double speed = speedVar == null ? 1.25 : speedVar.setValue;
            int gasType = gasTypeVar == null ? 0 : (int) gasTypeVar.setValue;
            ExplosionChaos.spawnChlorine(actualWorld, pos.x, pos.y, pos.z, (int) Util.getBlastSize(bullet.definition), speed, gasType);
        });
    }
}