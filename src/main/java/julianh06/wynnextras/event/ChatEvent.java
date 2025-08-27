package julianh06.wynnextras.event;

import julianh06.wynnextras.event.api.WEEvent;
import net.minecraft.text.Text;

public class ChatEvent extends WEEvent {
    public Text message;

    public ChatEvent(Text Message) {
        this.message = Message;
    }
}
