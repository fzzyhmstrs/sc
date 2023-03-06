package me.fzzyhmstrs.socket_core.interfaces;

import me.fzzyhmstrs.socket_core.socket.Socket;
import me.fzzyhmstrs.socket_core.socket.SocketStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SocketHolder {
    default List<SocketStack> getSockets(){
        return List.of();
    }
    default List<SocketStack> getSocketsById(Identifier id) {
        return List.of();
    }
    default void addSocket(SocketStack newSocket){}
    default void removeSocket(SocketStack oldSocket){}
    @Nullable
    default SocketStack stackFromSocket(Socket socket){return null;}
}
