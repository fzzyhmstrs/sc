package me.fzzyhmstrs.socket_core.api;

import me.fzzyhmstrs.socket_core.socket.Socket;
import me.fzzyhmstrs.socket_core.socket.SocketStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class SocketApi {

    public static void addSocket(ItemStack parent, Socket socket){
        SocketStack stack = parent.stackFromSocket(socket);
        parent.addSocket(stack);
    }

    public static void addSocket(SocketStack stack){
        stack.getParent().addSocket(stack);
    }

    public static void removeSocket(ItemStack parent,SocketStack stack){
        parent.removeSocket(stack);
    }

    public static List<SocketStack> getSockets(ItemStack stack){
        return stack.getSockets();
    }

    public static List<SocketStack> getSocketsById(ItemStack stack, Identifier id){
        return stack.getSocketsById(id);
    }
}
