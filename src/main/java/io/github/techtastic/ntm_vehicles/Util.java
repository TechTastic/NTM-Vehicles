package io.github.techtastic.ntm_vehicles;

import mcinterface1122.WrapperWorld;
import minecrafttransportsimulator.jsondefs.JSONBullet;
import minecrafttransportsimulator.jsondefs.JSONVariableModifier;
import net.minecraft.world.World;

import java.lang.reflect.Field;

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
        if (definition.variableModifiers == null)
            return null;

        for (JSONVariableModifier var : definition.variableModifiers) {
            if (var.variable.equals(name))
                return var;
        }
        return null;
    }

    public static double getBlastSize(JSONBullet bullet) {
        return bullet.bullet.blastStrength == 0f ? bullet.bullet.diameter / 10f : bullet.bullet.blastStrength;
    }
}
