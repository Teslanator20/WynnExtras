package julianh06.wynnextras.mixin.BankOverlay;

import julianh06.wynnextras.features.inventory.BankOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin (DrawContext.class)
public class DrawContextMixin{

    @Redirect(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V")
    )
    public void redirectTranslate(MatrixStack instance, float x, float y, float z) {
        if(!BankOverlay.isBank) {
            instance.translate(0.0F, 0.0F, 400.0F);
        } else {
            instance.translate(0.0f, 0.0f, 501f);
        }
    }

    @Redirect(
            method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/tooltip/TooltipBackgroundRenderer;render(Lnet/minecraft/client/gui/DrawContext;IIIIILnet/minecraft/util/Identifier;)V")
    )
    private static void redirectRender(DrawContext context, int x, int y, int width, int height, int z, Identifier texture) {
        if(!BankOverlay.isBank) {
            TooltipBackgroundRenderer.render(context, x, y, width, height, 400, texture);
        } else {
            TooltipBackgroundRenderer.render(context, x, y, width, height, 500, texture);
        }
    }
}
