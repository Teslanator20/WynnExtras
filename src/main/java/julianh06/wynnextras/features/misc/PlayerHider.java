package julianh06.wynnextras.features.misc;

import com.wynntils.models.raid.raids.RaidKind;
import com.wynntils.models.raid.type.RaidInfo;
import com.wynntils.utils.mc.McUtils;
import julianh06.wynnextras.config.WynnExtrasConfig;
import julianh06.wynnextras.config.simpleconfig.SimpleConfig;
import julianh06.wynnextras.core.command.Command;
import julianh06.wynnextras.core.command.SubCommand;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static julianh06.wynnextras.features.render.PlayerRenderFilter.*;

public class PlayerHider {
    private static final WynnExtrasConfig config = SimpleConfig.getInstance(WynnExtrasConfig.class);

    //    private static SubCommand subCommandTest = new SubCommand(
//           "subTest",
//           "subTest.",
//           context -> {
//               McUtils.sendMessageToClient(Text.of("subcommand PersonalStorageUtils worked"));
//               return 1;
//           }, null,
//            ClientCommandManager.argument("PersonalStorageUtils", StringArgumentType.greedyString())
//    );
//
//    private static Command testCommand = new Command(
//            "PersonalStorageUtils",
//            "PersonalStorageUtils.",
//            context -> {
//                //String arg = StringArgumentType.getString(context, "PersonalStorageUtils");
//                McUtils.sendMessageToClient(Text.of("PersonalStorageUtils worked"));
//                return 1;
//            },
//            subCommandTest,
//            null
//    );

    private static SubCommand toggleSubCmd = new SubCommand(
            "toggle",
            "",
            context -> {
                config.partyMemberHide = !config.partyMemberHide;
                if(config.partyMemberHide) {
                    McUtils.sendMessageToClient(Text.of("[Wynnextras] Enabled Playerhider"));
                } else {
                    McUtils.sendMessageToClient(Text.of("[Wynnextras] Disabled Playerhider"));
                }
                return 1;
            },
            null,
            null
    );

    private static Command playerhiderCmd = new Command(
            "playerhider",
            "",
            context -> { return 1; },
            toggleSubCmd,
            null
    );

    static boolean inNotg = false;

    public static void registerBossPlayerHider() {
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
            int Distance = SimpleConfig.getInstance(WynnExtrasConfig.class).maxHideDistance;

            MinecraftClient client = MinecraftClient.getInstance();
            if(client.player == null || client.world == null) { return; }
            ClientPlayerEntity me = client.player;

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == null) {
                    if(config.printDebugToConsole) {
                        System.out.println("PLAYER == NULL");
                    }
                    return;
                }

                if (player == me) {
                    continue;
                }

                if(!config.partyMemberHide || (config.onlyInNotg && !inNotg)) {
                    if(isHidden(player)) { show(player); }
                    return;
                }

                double distance = player.getPos().distanceTo(me.getPos());
                if(config.printDebugToConsole) {
                    System.out.println("Distance to " + player.getName() + " is: " + distance + " max hide distance is: " + Distance);
                }
                if (distance >= Distance) {
                    if(isHidden(player)) { show(player); }
                    continue;
                }

                if(config.hiddenPlayers.toString().toLowerCase().contains(player.getName().getString().toLowerCase())) {
                    hide(player);
                } else {
                    if(isHidden(player)) { show(player); }
                }
            }
        });
    }

    public static void onRaidStarted(RaidKind raid) {
        if(raid.getAbbreviation().equals("NOG")){
            inNotg = true;
        }
    }

    public static void onRaidEnded(RaidInfo info) {
        if(info.getRaidKind().getAbbreviation().equals("NOG")){
            inNotg = false;
        }
    }
}
