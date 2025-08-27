package julianh06.wynnextras.features.misc;

import julianh06.wynnextras.config.WynnExtrasConfig;
import julianh06.wynnextras.annotations.WEModule;
import julianh06.wynnextras.config.simpleconfig.SimpleConfig;
import julianh06.wynnextras.event.RenderWorldEvent;
import julianh06.wynnextras.utils.WEVec;
import julianh06.wynnextras.utils.render.WorldRenderUtils;
import net.neoforged.bus.api.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;

@WEModule
public class ShamanTotemCircle {
    private static final WynnExtrasConfig config = SimpleConfig.getInstance(WynnExtrasConfig.class);


    public static HashMap<Integer, WEVec> totemPositions = new HashMap<>();

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent event) {
        if(!config.totemRangeVisualizerToggle) return;
        for (int i = 0; i < 4; i++) {
            if(totemPositions.containsKey(i)) {
                WorldRenderUtils.draw3DCircle(event, totemPositions.get(i).add(0, -1.5, 0), config.totemRange, Color.WHITE, 4, true);
                WorldRenderUtils.draw3DCircle(event, totemPositions.get(i).add(0, -1.5, 0), config.eldritchCallRange, Color.CYAN, 4, true);
            }
        }
    }
}