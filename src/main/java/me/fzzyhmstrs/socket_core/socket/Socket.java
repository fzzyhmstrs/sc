package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.SC;
import me.fzzyhmstrs.socket_core.interfaces.Socketable;
import me.fzzyhmstrs.socket_core.interfaces.SocketableType;
import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class Socket {

    public Socket(@NotNull Identifier id, int capacity, String empty, String full, Predicate<Socketable<?>> canAccept){
        this.id = id;
        this.capacity = capacity;
        this.empty = empty;
        this.full = full;
        this.canAccept = canAccept;
    }

    private static final Socket DEFAULT = Registry.register(
            SocketRegistries.SOCKET,
            new Identifier(SC.MOD_ID,"default_socket"),
            new Builder(new Identifier(SC.MOD_ID,"default_socket")).build()
    );

    @NotNull
    final Identifier id;
    int capacity = 1;
    final String empty;
    final String full;
    final Predicate<Socketable<?>> canAccept;

    public static class Builder{

        public Builder(Identifier id){
            this.id = id;
        }

        private final Identifier id;
        private int capacity = 1;
        private String empty = "◇";
        private String full = "◆";
        private Predicate<Socketable<?>> canAccept = socketable -> true;

        public Socket build(){
            return new Socket(id,capacity,empty,full,canAccept);
        }

        public Builder capacity(int capacity){
            this.capacity = capacity;
            return this;
        }

        public Builder empty(String empty){
            this.empty = empty;
            return this;
        }

        public Builder full(String full){
            this.full = full;
            return this;
        }

        public Builder emptyFull(String empty, String full){
            this.empty = empty;
            this.full = full;
            return this;
        }

        public Builder canAcceptType(SocketableType<?> type){
            this.canAccept = socketable -> socketable.type().equals(type);
            return this;
        }

        public Builder canAcceptTag(TagKey<Item> tag){
            this.canAccept = socketable ->  socketable.item().getRegistryEntry().isIn(tag);
            return this;
        }

        public Builder canAccept(Predicate<Socketable<?>> canAccept){
            this.canAccept = canAccept;
            return this;
        }

    }

}
