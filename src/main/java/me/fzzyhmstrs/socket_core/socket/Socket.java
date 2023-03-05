package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.interfaces.Socketable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Socket {

    public Socket(@NotNull Identifier id, int capacity, String empty, String full, Predicate<Item> canAccept){
        this.id = id;
        this.capacity = capacity;
        this.empty = empty;
        this.full = full;
        this.canAccept = canAccept;
    }

    public Socket(Identifier id, int capacity){
        this(id,capacity,"◇","◆", item -> true);
    }

    public Socket(Identifier id, int capacity, TagKey<Item> tag){
        this(id,capacity,"◇","◆",item -> item.getRegistryEntry().isIn(tag));
        Item item = Items.AIR;
    }

    public Socket(Identifier id, int capacity, List<Item> list){
        this(id, capacity, "◇","◆", list::contains);
    }

    public Socket(Identifier id, int capacity, Predicate<Item> canAccept){
        this(id,capacity,"◇","◆",canAccept);
    }

    @NotNull
    private final Identifier id;
    private int capacity = 1;
    private final String empty;
    private final String full;
    private final Predicate<Item> canAccept;
    private final Socketable[] items = new Socketable[capacity];
    private final ItemStack[] itemStacks = new ItemStack[capacity];
    private int emptyIndex = 0;
    private List<Text> tooltip = buildTooltip();

    private List<Text> buildTooltip(){
        List<Text> list = new ArrayList<>(2);
        MutableText name;
        if (id == null){
            name = Text.translatable("socket.socket_core.default");
        } else {
            name = Text.translatable("socket." + id.getNamespace() + "." + id.getPath());
        }
        list.add(name);
        for (Socketable item : items){
            if (item.isEmpty()){
                list.add(Text.translatable("socket_core.socket_empty",empty));
            } else {
                List<Formatting> formatting = item.getFormatting();
                formatting.add(Formatting.BOLD);
                Formatting[] array = new Formatting[formatting.size()];
                list.add(Text.translatable("socket_core.socket", Text.literal(full).formatted(formatting.toArray(array)),item.item().getName(),item.giveTooltipHint()));
            }
        }
        return list;
    }

    public boolean tryInsert(ItemStack itemStack){
        Item item = itemStack.getItem();
        if (item instanceof Socketable socketable){
            if (!canAccept.test(item)) return false;
            if (emptyIndex == -1) return false;
            items[emptyIndex] = socketable;
            itemStacks[emptyIndex] = itemStack;
            tooltip = buildTooltip();
            emptyIndex++;
            if (emptyIndex >= items.length){
                emptyIndex = -1;
            }
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
        items[emptyIndex] = Socketable.EMPTY;
        tooltip = buildTooltip();
        return Optional.of(stack);
    }


    public static void addSockets(ItemStack stack, Socket socket){}

    public static void removeSockets(ItemStack stack, Socket socket){}


}
