package julianh06.wynnextras.utils.overlays;

import com.wynntils.utils.colors.CustomColor;
import com.wynntils.utils.render.RenderUtils;
import julianh06.wynnextras.annotations.WEModule;
import julianh06.wynnextras.event.KeyInputEvent;
import julianh06.wynnextras.event.RenderWorldEvent;
import julianh06.wynnextras.event.TickEvent;
import julianh06.wynnextras.features.inventory.BankOverlay;
import julianh06.wynnextras.mixin.Accessor.KeybindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class EasyTextInput extends EasyElement{
    boolean isActive = false;
    CustomColor color = CustomColor.fromHexString("FFFFFF");
    String input = "";

    private int DEBOUNCE_DELAY_MS = 100;
    HashMap<Long, Long> lastCharTime = new HashMap<>();
    HashMap<Integer, Integer> cooldowns = new HashMap<>();
    int cursorPos = 0;

    long lastBlink = 0;
    boolean blinkToggle = true;

    public EasyTextInput() {
        super(-1, -1, 0, 0);
        cursorPos = 0;
    }

    public EasyTextInput(int x, int y, int height, int width) {
        super(x, y, height, width);
        cursorPos = 0;
        //detectInput();
    }

    @Override
    public void draw(DrawContext context) {
        long now = System.currentTimeMillis();
        RenderUtils.drawRect(context.getMatrices(), color, x, y, 0.0f, width, height);
        if(input.isEmpty() && !isActive) {
            context.drawText(MinecraftClient.getInstance().textRenderer, "Search...", x + 1, y + 3, CustomColor.fromHexString("000000").asInt(), false);
        } else {
            if(cursorPos > input.length()) {
                cursorPos = input.length();
            }
            context.drawText(MinecraftClient.getInstance().textRenderer, input, x + 1, y + 3, CustomColor.fromHexString("000000").asInt(), false);
            if(now - lastBlink > 500) {
                blinkToggle = !blinkToggle;
                lastBlink = now;
            }
            if(blinkToggle && isActive) RenderUtils.drawLine(context.getMatrices(), CustomColor.fromHexString("000000"), x + 1 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y + 2, x + 1 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y + 11, 0, 1);
        }
    }

    public void drawWithoutBackground(DrawContext context, CustomColor color) {
        long now = System.currentTimeMillis();
        if(input.isEmpty() && !isActive) {
            //context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Search...", x + 3, y + 1, CustomColor.fromHexString("FFFFFF").asInt());
        } else {
            if(cursorPos > input.length()) {
                cursorPos = input.length();
            }
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, input, x + 3, y + 1, color.asInt());
            if(now - lastBlink > 500) {
                blinkToggle = !blinkToggle;
                lastBlink = now;
            }
            if(blinkToggle && isActive) RenderUtils.drawLine(context.getMatrices(), CustomColor.fromHexString("FFFFFF"), x + 3 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y, x + 3 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y + 9, 0, 1);
        }
    }

    public void drawWithTexture(DrawContext context, Identifier texture) {
        long now = System.currentTimeMillis();
        RenderUtils.drawTexturedRect(context.getMatrices(), texture, x, y, 0.0f, width, height, width, height);
        if(input.isEmpty() && !isActive) {
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Search...", x + 2, y + 3, CustomColor.fromHexString("FFFFFF").asInt());
        } else {
            if(cursorPos > input.length()) {
                cursorPos = input.length();
            }
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, input, x + 2, y + 3, CustomColor.fromHexString("FFFFFF").asInt());
            if(now - lastBlink > 500) {
                blinkToggle = !blinkToggle;
                lastBlink = now;
            }
            if(blinkToggle && isActive) RenderUtils.drawLine(context.getMatrices(), CustomColor.fromHexString("FFFFFF"), x + 2 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y + 2, x + 2 + MinecraftClient.getInstance().textRenderer.getWidth(input.substring(0, cursorPos)), y + 11, 0, 1);
        }
    }


    @Override
    public void click() {
        if(!isActive) {
            isActive = true;
            BankOverlay.activeTextInput = this;
            cursorPos = input.length();
            color = CustomColor.fromHexString("FFEA00");
            return;
        }
        isActive = false;
        color = CustomColor.fromHexString("FFFFFF");
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean newValue) {
        isActive = newValue;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String value) {
        input = value;
    }

    public void clearInput() {
        input = "";
        cursorPos = 0;
    }

    public void onInput(KeyInputEvent event) {
        System.out.println("input");
        if(!isActive || !BankOverlay.isBank) return;
        //long handle = MinecraftClient.getInstance().getWindow().getHandle();
        AtomicLong now = new AtomicLong();
        int action = event.getAction();
        int key = event.getKey();
        int scancode = event.getScanCode();

        now.set(System.currentTimeMillis());

        if (action == GLFW.GLFW_RELEASE) {
            cooldowns.remove(key);
        } else {
            if(action == GLFW.GLFW_PRESS) {
                DEBOUNCE_DELAY_MS = 100;

            } else if (action == GLFW.GLFW_REPEAT) {
                DEBOUNCE_DELAY_MS = 10;
            }

            if (isActive && now.get() - lastCharTime.getOrDefault((long) key, 0L) >= cooldowns.getOrDefault(key, DEBOUNCE_DELAY_MS)) {
                if (isValidKey(key)) {
                    if (key == GLFW.GLFW_KEY_SPACE) {
                        input = insertAt(cursorPos, " ", input);
                        //input += " ";
                    } else {
                        input = insertAt(cursorPos, InputUtil.fromKeyCode(key, scancode).getLocalizedText().getLiteralString(), input);
                        //input += InputUtil.fromKeyCode(key, scancode).getLocalizedText().getLiteralString();
                    }
                    cursorPos++;
                } else if (key == GLFW.GLFW_KEY_BACKSPACE && !input.isEmpty() && cursorPos > 0) {
                    input = removeAt(cursorPos, input);
                    cursorPos--;
                } else if (key == GLFW.GLFW_KEY_DELETE && !input.isEmpty()) {
                    input = removeAt(cursorPos + 1, input);
                } else if (key == GLFW.GLFW_KEY_LEFT) {
                    cursorPos--;
                } else if (key == GLFW.GLFW_KEY_RIGHT) {
                    cursorPos++;
                }
                lastCharTime.put((long) key, now.get());
                cooldowns.putIfAbsent(key, DEBOUNCE_DELAY_MS);
            }
        }
    }

    private boolean isValidKey(int key) {
        if(key == 32) return true;
        if(key == 39) return true;
        if(key >= 44 && key <= 57) return true;
        if(key == 59) return true;
        if(key == 61) return true;
        if(key >= 65 && key <= 93) return true;
        return false;
    }

    private String insertAt(int i, String value, String src) {
        //Inserts AFTER index
        if(i < 0) {
            return null;
        }
        if(i >= src.length()) {
            src += value;
            return src;
        }
        String leftSub = src.substring(0, i);
        String rightSub = src.substring(i);
        return leftSub + value + rightSub;
    }

    private String removeAt(int i, String src) {
        if(i < 0 || i > src.length() || src.isEmpty()){
            return src;
        }
        String leftSub = src.substring(0, i - 1);
        String rightSub = src.substring(i);
        return leftSub + rightSub;
    }
}
