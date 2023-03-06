package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.interfaces.SocketableType;
import net.minecraft.item.Item;
import net.minecraft.util.Formatting;

import java.util.List;

public record SocketableInfo<T extends SocketableType<?>>(T type, Item item, List<Formatting> formatting, boolean isEmpty) {
}
