package io.github.techtastic.ntm_vehicles;

import io.github.techtastic.ntm_vehicles.ntm_vehicles.Tags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "required-after:mts;required-after:hbm;required-after:mixinbooter", acceptableRemoteVersions = "*")
public class NTMVehicles {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        /*EntityBullet.registerCustomHitFunction("ntm_vehicles:nuke", (world, pos, side, type, bullet) -> {
            World actualWorld = Util.getWorld((WrapperWorld) world);

            if (actualWorld == null)
                return;

            actualWorld.spawnEntity(EntityNukeExplosionMK5.statFac(actualWorld, (int) Util.getBlastSize(bullet.definition), pos.x, pos.y, pos.z));
            EntityNukeTorex.statFac(actualWorld, pos.x, pos.y, pos.z, (int) Util.getBlastSize(bullet.definition));
        });

        EntityBullet.registerCustomHitFunction("ntm_vehicles:gas", (world, pos, side, type, bullet) -> {
            World actualWorld = Util.getWorld((WrapperWorld) world);

            if (actualWorld == null)
                return;

            double speed = Util.getConstantValue(bullet.definition, "gasSpreadSpeed", 1.25);
            int gasType = (int) Util.getConstantValue(bullet.definition, "gasType", 0);
            ExplosionChaos.spawnChlorine(actualWorld, pos.x, pos.y, pos.z, (int) Util.getBlastSize(bullet.definition), speed, gasType);
        });

        EntityBullet.registerCustomHitFunction("ntm_vehicles:napalm", (world, pos, side, type, bullet) -> {
            World actualWorld = Util.getWorld((WrapperWorld) world);

            if (actualWorld == null)
                return;

            //actualWorld.spawnEntity(EntityBomber.statFacNapalm(actualWorld, pos.x, pos.y, pos.z));

            ExplosionLarge.explode(actualWorld, pos.x, pos.y, pos.z, 2.5f, false, false, false);
            ExplosionChaos.burn(actualWorld, new BlockPos(pos.x, pos.y, pos.z), 9);
            ExplosionChaos.flameDeath(actualWorld, new BlockPos(pos.x, pos.y, pos.z), 14);

            for(int i = 0; i < 5; i++)
                ExplosionLarge.spawnBurst(actualWorld, pos.x, pos.y + 1, pos.z, actualWorld.rand.nextInt(10) + 15, actualWorld.rand.nextFloat() * 2 + 2);
        });*/
    }
}