package site.siredvin.turtlematic.mixins;

import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.model.turtle.TurtleModelParts;
import dan200.computercraft.shared.turtle.items.TurtleItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import site.siredvin.turtlematic.util.DataStorageObjects;
import site.siredvin.tweakium.modules.turtle.api.TurtleUpgradeHolder;
import site.siredvin.turtlematic.TurtleAccessNbtOnlyCrutch;

import java.util.List;
import java.util.function.Function;

@Mixin(TurtleModelParts.class)
public class TurtleModelPartsMixin {
    @Unique
    private Function<List<BakedModel>, Object> turtlematic$combineModel;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(BakedModel familyModel, BakedModel colourModel, TurtleModelParts.ModelTransformer transformer, Function combineModel, CallbackInfo ci) {
        this.turtlematic$combineModel = combineModel;
    }

    @Unique
    private static BlockState turtlematic$getMimicBlockState(ItemStack stack, TurtleSide side) {
        if (!(stack.getItem() instanceof TurtleItem turtle)) {
            return null;
        }
        final var upgrade = turtle.getUpgradeWithData(stack, side);
        if (upgrade == null) {
            return null;
        }

        final var directState = DataStorageObjects.Mimic.INSTANCE.get(upgrade.data());
        if (directState != null) {
            return directState;
        }

        if (upgrade.upgrade() instanceof TurtleUpgradeHolder upgradeHolder) {
            final var turtleAccess = new TurtleAccessNbtOnlyCrutch(stack);
            for (final var internalUpgrade: upgradeHolder.getInternalUpgrades(turtleAccess, side)) {
                final var internalUpgradeState = DataStorageObjects.Mimic.INSTANCE.get(internalUpgrade.data());
                if (internalUpgradeState != null) {
                    return internalUpgradeState;
                }
            }
        }

        return null;
    }

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true, remap = false)
    private void getModel(ItemStack stack, CallbackInfoReturnable<Object> cir) {
        BlockState state = turtlematic$getMimicBlockState(stack, TurtleSide.LEFT);
        if (state == null) {
            state = turtlematic$getMimicBlockState(stack, TurtleSide.RIGHT);
        }

        if (state == null || state.getRenderShape() != RenderShape.MODEL) {
            return;
        }

        final var minecraft = Minecraft.getInstance();
        final var model = minecraft.getBlockRenderer().getBlockModel(state);
        final var res = this.turtlematic$combineModel.apply(List.of(model));
        cir.setReturnValue(res);
    }
}
