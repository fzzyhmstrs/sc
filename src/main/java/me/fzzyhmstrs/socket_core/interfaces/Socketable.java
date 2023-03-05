package me.fzzyhmstrs.socket_core.interfaces;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Socketable {

    Socketable EMPTY = new Empty();

    Text giveTooltipHint();
    Item item();
    List<Formatting> getFormatting();
    default boolean isEmpty(){
        return false;
    }

    class Empty implements Socketable{
        @Override
        public Text giveTooltipHint() {
            return Text.empty();
        }
        @Override
        public Item item() {
            return Items.AIR;
        }
        @Override
        public List<Formatting> getFormatting() {
            return new ArrayList<>(Collections.singleton(Formatting.WHITE));
        }
        @Override
        public boolean isEmpty() {
            return true;
        }
    }
}
