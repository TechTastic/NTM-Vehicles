package io.github.techtastic.ntm_vehicles;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.baseclasses.Point3D;
import minecrafttransportsimulator.entities.instances.PartGun;
import minecrafttransportsimulator.items.instances.ItemBullet;
import minecrafttransportsimulator.jsondefs.JSONBullet;
import minecrafttransportsimulator.jsondefs.JSONVariableModifier;
import minecrafttransportsimulator.mcinterface.AWrapperWorld;
import minecrafttransportsimulator.systems.ConfigSystem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class Util {
    public static World getWorld(AWrapperWorld world) {
        try {
            Class<WrapperWorld> clazz = WrapperWorld.class;
            Field field = clazz.getDeclaredField("world");
            field.setAccessible(true);
            return (World) field.get(world);
        } catch (Exception ignored) {}
        return null;
    }

    public static JSONVariableModifier getVariableModifier(JSONBullet definition, String name) {
        if (definition.variableModifiers == null)
            return null;

        for (JSONVariableModifier var : definition.variableModifiers) {
            if (var.variable.equals(name))
                return var;
        }
        return null;
    }

    public static double getConstantValue(JSONBullet definition, String name, double defaultValue) {
        if (definition.constantValues == null)
            return defaultValue;
        return definition.constantValues.getOrDefault(name, defaultValue);
    }

    public static double getBlastSize(JSONBullet bullet) {
        return bullet.bullet.blastStrength == 0f ? bullet.bullet.diameter / 10f : bullet.bullet.blastStrength;
    }

    public static void useHitLogic(PartGun gun, Point3D pos) {
        World world = Util.getWorld(gun.world);
        if (!gun.world.isClient() && ConfigSystem.settings.damage.bulletBlockBreaking.value && world != null) {
            ItemBullet bullet = gun.lastLoadedBullet;
            int blastSize = (int) Util.getBlastSize(bullet.definition);
            if (Util.getConstantValue(bullet.definition, "nuke", 0.0) == 1.0) {
                world.spawnEntity(EntityNukeExplosionMK5.statFac(world, (int) Util.getBlastSize(bullet.definition), pos.x, pos.y, pos.z));
                EntityNukeTorex.statFac(world, pos.x, pos.y, pos.z, blastSize);
            }

            if (Util.getConstantValue(bullet.definition, "gas", 0.0) == 1.0) {
                double speed = Util.getConstantValue(bullet.definition, "gasSpreadSpeed", 1.25);
                int gasType = (int) Util.getConstantValue(bullet.definition, "gasType", 0);
                ExplosionChaos.spawnChlorine(world, pos.x, pos.y, pos.z, blastSize, speed, gasType);
            }

            if (Util.getConstantValue(bullet.definition, "napalm", 0.0) == 1.0) {
                //actualWorld.spawnEntity(EntityBomber.statFacNapalm(world, blockPosition.x, blockPosition.y, blockPosition.z));

                ExplosionLarge.explode(world, pos.x, pos.y, pos.z, 2.5f, false, false, false);
                ExplosionChaos.burn(world, new BlockPos(pos.x, pos.y, pos.z), 9);
                ExplosionChaos.flameDeath(world, new BlockPos(pos.x, pos.y, pos.z), 14);

                for(int i = 0; i < 5; i++)
                    ExplosionLarge.spawnBurst(world, pos.x, pos.y, pos.z,
                            world.rand.nextInt(10) + 15, world.rand.nextFloat() * 2 + 2);
            }
        }
    }
}
