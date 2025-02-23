package dev.sygii.attributes;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class AttributeBackportMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if ((mixinClassName.contains("ViewerCountManagerMixin") || mixinClassName.contains("VehicleInventoryMixin")
                || mixinClassName.contains("ServerPlayNetworkHandlerMixin") || mixinClassName.contains("ServerPlayNetworkHandler1Mixin")
                || mixinClassName.contains("ServerPlayerInteractionManagerMixin") || mixinClassName.contains("ScreenHandlerMixin")
                || mixinClassName.contains("ReachPlayerEntityMixin") || mixinClassName.contains("ItemMixin")
                || mixinClassName.contains("InventoryMixin") || mixinClassName.contains("GameRendererMixin")
                || mixinClassName.contains("ForgingScreenHandlerMixin") || mixinClassName.contains("ClientPlayerInteractionManagerMixin")) && (FabricLoader.getInstance().isModLoaded("zenith_attributes") || FabricLoader.getInstance().isModLoaded("reach-entity-attributes"))) {
            return false;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

}
