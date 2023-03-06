package me.fzzyhmstrs.socket_core.socket;

import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SocketProvider(List<Socket> sockets) {

    public void toBuf(PacketByteBuf buf) {
        buf.writeVarInt(sockets.size());
        for (Socket socket : sockets) {
            buf.writeIdentifier(socket.id);
        }
    }

    public static SocketProvider fromBuf(PacketByteBuf buf) {
        int size = buf.readVarInt();
        List<Socket> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Socket socket = SocketRegistries.SOCKET.get(buf.readIdentifier());
            list.add(socket);
        }
        return new SocketProvider(Collections.unmodifiableList(list));
    }

}
