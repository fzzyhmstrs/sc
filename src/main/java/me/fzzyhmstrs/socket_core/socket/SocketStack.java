package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.interfaces.Socketable;
import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.*;

public final class SocketStack {

    public SocketStack(ItemStack parent, Socket socket){
        this.parent = parent;
        this.socket = socket;
        this.items = buildItems(socket.capacity);
        this.presentItems = new LinkedList<>();
        this.itemStacks = buildStacks(socket.capacity);
        this.presentStacks = new LinkedList<>();
        this.tooltip = buildTooltip();
    }

    private final ItemStack parent;
    private final  Socket socket;
    private final Socketable<?>[] items;
    private final List<Socketable<?>> presentItems;
    private final ItemStack[] itemStacks;
    private final List<ItemStack> presentStacks;
    private int emptyIndex = 0;
    private List<Text> tooltip;

    private Socketable<?>[] buildItems(int capacity){
        Socketable<?>[] sockets = new Socketable[capacity];
        Arrays.fill(sockets, SocketRegistries.EMPTY_SOCKETABLE);
        return sockets;
    }

    private ItemStack[] buildStacks(int capacity){
        ItemStack[] stacks = new ItemStack[capacity];
        Arrays.fill(stacks, ItemStack.EMPTY);
        return stacks;
    }

    private List<Text> buildTooltip(){
        List<Text> list = new ArrayList<>(2);
        MutableText name;
        String key = "socket." + socket.id.getNamespace() + "." + socket.id.getPath();
        name = Text.translatable(key);
        if (key.equals(name.getString()))
        list.add(name);
        for (Socketable<?> item : items){
            if (item.isEmpty()){
                list.add(Text.translatable("socket_core.socket_empty",socket.empty));
            } else {
                List<Formatting> formatting = item.getFormatting();
                formatting.add(Formatting.BOLD);
                Formatting[] array = new Formatting[formatting.size()];
                list.add(Text.translatable("socket_core.socket", Text.literal(socket.full).formatted(formatting.toArray(array)),item.giveTooltipHint()));
            }
        }
        return list;
    }

    public ItemStack getParent(){
        return parent;
    }

    public Identifier getId(){
        return socket.id;
    }

    public List<Socketable<?>> getSocketables() {
        return presentItems;
    }

    public List<ItemStack> getStacks(){
        return presentStacks;
    }

    public List<Text> getTooltip(){
        return tooltip;
    }

    public boolean tryInsert(ItemStack itemStack, Socketable<?> socketable){
        if (!socket.canAccept.test(socketable)) return false;
        if (emptyIndex == -1) return false;
        items[emptyIndex] = socketable;
        itemStacks[emptyIndex] = itemStack;
        tooltip = buildTooltip();
        emptyIndex++;
        if (emptyIndex >= items.length){
            emptyIndex = -1;
        }
        return false;
    }

    Optional<ItemStack> tryRemove() {
        if (emptyIndex == 0) return Optional.empty();
        if (emptyIndex == -1){
            emptyIndex = itemStacks.length - 1;
        }
        ItemStack stack = itemStacks[emptyIndex].copy();
        itemStacks[emptyIndex] = ItemStack.EMPTY;
        items[emptyIndex] = SocketRegistries.EMPTY_SOCKETABLE;
        tooltip = buildTooltip();
        return Optional.of(stack);
    }

}
