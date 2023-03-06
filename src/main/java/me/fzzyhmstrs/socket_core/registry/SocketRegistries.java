package me.fzzyhmstrs.socket_core.registry;

import com.google.common.collect.ArrayListMultimap;
import me.fzzyhmstrs.socket_core.SC;
import me.fzzyhmstrs.socket_core.interfaces.Socketable;
import me.fzzyhmstrs.socket_core.interfaces.SocketableType;
import me.fzzyhmstrs.socket_core.socket.BasicSocketable;
import me.fzzyhmstrs.socket_core.socket.Socket;
import me.fzzyhmstrs.socket_core.socket.SocketProvider;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;

import java.util.List;

public class SocketRegistries {

    public static void init(){}

    public static Socketable<SocketableType<BasicSocketable>> EMPTY_SOCKETABLE = new Socketable.Empty();

    public static DefaultedRegistry<SocketableType> SOCKETABLE_TYPE = FabricRegistryBuilder.createDefaulted(SocketableType.class,new Identifier(SC.MOD_ID,"socketable_types"),new Identifier(SC.MOD_ID,"default")).buildAndRegister();
    public static DefaultedRegistry<Socket> SOCKET = FabricRegistryBuilder.createDefaulted(Socket.class,new Identifier(SC.MOD_ID,"socket"),new Identifier(SC.MOD_ID,"default_socket")).buildAndRegister();
    public static SocketProviderRegistry SOCKET_PROVIDER = new SocketProviderRegistry();

    public static class SocketProviderRegistry{

        private final ArrayListMultimap<Item, SocketProvider> SOCKET_PROVIDERS = ArrayListMultimap.create();

        public void register(Item item, SocketProvider provider){
            SOCKET_PROVIDERS.put(item,provider);
        }

        public List<SocketProvider> getSocketProviders(Item item){
            return SOCKET_PROVIDERS.get(item);
        }

    }

}
