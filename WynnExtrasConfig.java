package julianh06.wynnextras.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import julianh06.wynnextras.config.simpleconfig.ConfigData;
import julianh06.wynnextras.config.simpleconfig.ConfigHolder;
import julianh06.wynnextras.config.simpleconfig.SimpleConfig;
import julianh06.wynnextras.config.simpleconfig.annotations.Config;
import julianh06.wynnextras.config.simpleconfig.annotations.ConfigEntry;
import julianh06.wynnextras.features.misc.ItemStackDeserializer;
import julianh06.wynnextras.features.misc.ItemStackSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;

import me.shedaniel.math.Color;
import net.minecraft.util.ActionResult;

@Config(name = "wynnextras/wynnextras", title = "WynnExtras Config")
public class WynnExtrasConfig implements ConfigData {
    public static void registerSave(BiFunction<ConfigHolder<WynnExtrasConfig>, WynnExtrasConfig, ActionResult> saveFunction) {
        SimpleConfig.getConfigHolder(WynnExtrasConfig.class).registerSaveListener(saveFunction::apply);
    }

    public interface Categories {
        String playerHider = "Player Hider";
        String chatNotifier = "Chat Notifier";
        String chatBlocker = "Chat Blocker";
        String bankOverlay = "Bank Overlay";
        String totemRangeVisualizer = "Totem Range Visualizer";
        String provokeTimer = "Provoke Timer";
        String raidTimestamps = "Raid Timestamps";
    }


    //PLAYER HIDER

    @ConfigEntry.Name("Playerhider toggle")
    @ConfigEntry.Category(Categories.playerHider)
    public boolean partyMemberHide = true;

    @ConfigEntry.Name("Maximum hide distance")
    @ConfigEntry.Category(Categories.playerHider)
    public int maxHideDistance = 3;

    @ConfigEntry.Name("Hidden players")
    @ConfigEntry.Category(Categories.playerHider)
    public List<String> hiddenPlayers = new ArrayList<>();

    @ConfigEntry.Name("Only hide in NOTG")
    @ConfigEntry.Category(Categories.playerHider)
    @ConfigEntry.Excluded
    public boolean onlyInNotg = false;

    @ConfigEntry.Name("Print debug messages to minecrafts internal console")
    @ConfigEntry.Category(Categories.playerHider)
    @ConfigEntry.Excluded
    public boolean printDebugToConsole = false;


    //CHAT NOTIFIER

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Name("Notified words")
    public List<String> notifierWords = new ArrayList<>();

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Text
    public String notifierInfo = "The Phrase on the Left is what needs to be in the Message to trigger and " +
            "the one on the right is the text that will be displayed. Separate them by | for it to work.";

//    @ConfigEntry.Category(Categories.chatNotifier)
//    @ConfigEntry.Name("Text scale")
//    public float TextScale = 5f;
//
//    @ConfigEntry.Category(Categories.chatNotifier)
//    @ConfigEntry.Name("Text offset x")
//    public int TextOffsetX = 75;
//
//    @ConfigEntry.Category(Categories.chatNotifier)
//    @ConfigEntry.Name("Text offset y")
//    public int TextOffsetY = 40;

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Name("Text duration in ms")
    public int TextDurationInMs = 2000;

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Dropdown(values = {
            "WHITE",
            "BLACK",
            "AQUA",
            "RED",
            "YELLOW",
            "BLUE",
            "GREEN",
            "DARK_BLUE",
            "DARK_GREEN",
            "DARK_AQUA",
            "DARK_RED",
            "DARK_PURPLE",
            "LIGHT_PURPLE",
            "GRAY",
            "DARK_GRAY",
            "GOLD"
    })
    @ConfigEntry.Name("Text color")
    public String TextColor = "WHITE";

//    @ConfigEntry.Category(Categories.chatNotifier)
//    @ConfigEntry.Name("Text Preview")
//    public boolean NotifierPreview = false;

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Name("Sound")
    @ConfigEntry.Dropdown(values = {
        "entity.experience_orb.pickup",
        "block.bell.use",
        "entity.player.levelup",
        "block.anvil.place",
        "block.note_block.pling",
        "block.note_block.bell",
        "block.note_block.flute",
        "block.note_block.harp",
        "entity.firework_rocket.launch",
        "entity.item.pickup"
    })
    public String Sound = "entity.experience_orb.pickup";

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Name("Sound volume")
    public float SoundVolume = 0.1f;

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Name("Sound pitch")
    public float SoundPitch = 1;

    @ConfigEntry.Category(Categories.chatNotifier)
    @ConfigEntry.Text
    public String notifierCmdInfo = "Run \"/WynnExtras notifiertest\" to test out the notifier.";


    //CHAT BLOCKER

    @ConfigEntry.Category(Categories.chatBlocker)
    @ConfigEntry.Name("Blocked words")
    public List<String> blockedWords = new ArrayList<>();


    //BANK OVERLAY

    //    public HashMap<Integer, List<SavedItem>> BankPagesSavedItems = new HashMap<>();

    @ConfigEntry.Category(Categories.bankOverlay)
    @ConfigEntry.Name("Bank overlay toggle")
    public boolean toggleBankOverlay = true;

    @ConfigEntry.Category(Categories.bankOverlay)
    @ConfigEntry.Text
    public String bankInfo = "This Feature is still Work in Progress, bugs can (and probably will) occur. " +
                                    "Please report any issues you have on discord. If you haven't joined yet, run \"/WynnExtras Discord\". " +
                                    "The feature is currently only available for the account bank but implementations for character banks, " +
                                    "the tome shelf and the misc bucket are planned. Buying pages is possible but it's a bit buggy so it's " +
                                    "recommended to disable the feature to buy pages.";


    //TOTEM VISUALIZER

    @ConfigEntry.Category(Categories.totemRangeVisualizer)
    @ConfigEntry.Name("Totem range visualizer toggle")
    public boolean totemRangeVisualizerToggle = true;

    @ConfigEntry.Category(Categories.totemRangeVisualizer)
    @ConfigEntry.Name("Totem range")
    public int totemRange = 10;

    @ConfigEntry.Category(Categories.totemRangeVisualizer)
    @ConfigEntry.Name("Eldritchcall range")
    public int eldritchCallRange = 15;


    @ConfigEntry.Category(Categories.raidTimestamps)
       @ConfigEntry.Name("Raid timestamps toggle")
       public boolean toggleRaidTimestamps = true;
//    //Hider
//    public boolean partyMemberHide = true;
//    public int maxHideDistance = 3;
//    public boolean onlyInNotg = false;
//    public boolean printDebugToConsole = false;
//    public List<String> playerHiderList = new ArrayList<>();

//    //Chat Notifier Text
//    public List<String> notifierWords = new ArrayList<>();
//    public float TextScale = 5f;
//    public int TextOffsetX = 75;
//    public int TextOffsetY = 40;
//    public int TextDurationInMs = 2000;
//    public Color TextColor = Color.ofRGB(255, 255, 255);
//    public boolean NotifierPreview = false;

//    //Chat Notifier Sound
//    public String Sound = "entity.experience_orb.pickup";
//    public float SoundVolume = 0.1f;
//    public float SoundPitch = 1;
//
//    //Chat Blocker
//    public List<String> blockedWords = new ArrayList<>();
//
//    //Bank overlay
//    public HashMap<Integer, List<ItemStack>> BankPages = new HashMap<>();
////    public HashMap<Integer, List<SavedItem>> BankPagesSavedItems = new HashMap<>();
//    public boolean toggleBankOverlay = true;
//    public HashMap<Integer, String> BankPageNames = new HashMap<>();


//    //Totem Range Visualizer
//    public boolean totemRangeVisualizerToggle = true;
//    public int totemRange = 10;
//    public int eldritchCallRange = 15;

//    private static final Gson GSON = new GsonBuilder()
//            .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
//            .registerTypeAdapter(ItemStack.class, new ItemStackDeserializer())
////            .registerTypeAdapter(SavedItem.class, new SavedItemSerializer())
//            .setPrettyPrinting()
//            .create();
//    private static final Path CONFIG_PATH = FabricLoader.getInstance()
//            .getConfigDir()
//            .resolve("wynnextras.json");


//    public static WynnExtrasConfig INSTANCE = new WynnExtrasConfig();
//
//    public static void load() {
//        if (Files.exists(CONFIG_PATH)) {
//            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
//                INSTANCE = GSON.fromJson(reader, WynnExtrasConfig.class);
//            } catch (IOException e) {
//                System.err.println("[WynnExtras] Couldn't read the config file:");
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void save() {
//        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
//            GSON.toJson(INSTANCE, writer);
//        } catch (IOException e) {
//            System.err.println("[WynnExtras] Couldn't write the config file:");
//            e.printStackTrace();
//        }
//    }
//
//    public static void openConfigScreen() {
//        if(modMenuApiImpl == null) {
//            modMenuApiImpl = new WynnExtrasModMenuApiImpl();
//        }
//        if(modMenuApiImpl.configScreen == null) {
//            modMenuApiImpl.registerConfig();
//        }
//        MinecraftClient.getInstance().send(() -> MinecraftClient.getInstance().setScreen(modMenuApiImpl.configScreen));
//    }
}
