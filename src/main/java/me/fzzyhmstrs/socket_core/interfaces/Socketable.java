package me.fzzyhmstrs.socket_core.interfaces;

import me.fzzyhmstrs.socket_core.socket.BasicSocketable;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public abstract class Socketable<T extends SocketableType<?>> {

    protected Socketable(T type,Item item,List<Formatting> formatting,boolean isEmpty){
        this.type = type;
        this.item = item;
        this.formatting = formatting;
        this.isEmpty = isEmpty;
    }

    protected final T type;
    protected final Item item;
    protected final List<Formatting> formatting;
    protected final boolean isEmpty;


    public abstract Text giveTooltipHint();
    public T type(){
        return type;
    }
    public Item item(){
        return item;
    }
    public List<Formatting> getFormatting(){
        return formatting;
    }
    public boolean isEmpty(){
        return isEmpty;
    }

    public static class Empty extends Socketable<SocketableType<BasicSocketable>>{
        public Empty() {
            super(SocketableType.SIMPLE, Items.AIR, List.of(Formatting.WHITE), true);
        }
        @Override
        public Text giveTooltipHint() {
            return Text.empty();
        }
    }
}
