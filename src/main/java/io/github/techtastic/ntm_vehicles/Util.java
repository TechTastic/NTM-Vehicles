package io.github.techtastic.ntm_vehicles;

import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.jsondefs.JSONBullet;
import minecrafttransportsimulator.jsondefs.JSONVariableModifier;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.Optional;

public class Util {
    public static World getWorld(WrapperWorld world) {
        try {
            Class<WrapperWorld> clazz = WrapperWorld.class;
            Field field = clazz.getDeclaredField("world");
            field.setAccessible(true);
            return (World) field.get(world);
        } catch (Exception ignored) {}
        return null;
    }

    public static JSONVariableModifier getVariableModifier(JSONBullet definition, String name) {
        Optional<JSONVariableModifier> var = definition.variableModifiers.stream().filter(mod -> mod.variable.equals(name)).findFirst();
        return var.orElse(null);
    }

    public static double getBlastSize(JSONBullet bullet) {
        return bullet.bullet.blastStrength == 0f ? bullet.bullet.diameter / 10f : bullet.bullet.blastStrength;
    }
}
