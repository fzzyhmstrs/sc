package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.interfaces.Socketable;
import me.fzzyhmstrs.socket_core.interfaces.SocketableType;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicSocketable extends Socketable<SocketableType<BasicSocketable>> {

    public BasicSocketable(Item item, String toolTipKey, List<Formatting> socketableFormatting){
        super(SocketableType.SIMPLE,item,socketableFormatting,false);
        this.toolTipKey = toolTipKey;
    }

    public BasicSocketable(Item item, String toolTipKey){
        super(SocketableType.SIMPLE,item,List.of(Formatting.WHITE),false);
        this.toolTipKey = toolTipKey;
    }

    private final String toolTipKey;

    @Override
    public Text giveTooltipHint() {
        return Text.translatable("socket_core.socket_simple",item.getName(),Text.translatable(toolTipKey));
    }
}
