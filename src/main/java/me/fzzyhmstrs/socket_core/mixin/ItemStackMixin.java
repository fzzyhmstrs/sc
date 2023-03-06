package me.fzzyhmstrs.socket_core.mixin;

import com.google.common.collect.ArrayListMultimap;
import me.fzzyhmstrs.socket_core.interfaces.SocketHolder;
import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import me.fzzyhmstrs.socket_core.socket.Socket;
import me.fzzyhmstrs.socket_core.socket.SocketProvider;
import me.fzzyhmstrs.socket_core.socket.SocketStack;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin implements SocketHolder {

    @Unique
    private final List<SocketStack> socket_core_socketStacks = new ArrayList<>();
    private final ArrayListMultimap<Identifier,SocketStack> socket_core_stacksById = ArrayListMultimap.create();

    @Override
    public List<SocketStack> getSockets() {
        return socket_core_socketStacks;
    }
    @Override
    public List<SocketStack> getSocketsById(Identifier id) {
        return socket_core_stacksById.get(id);
    }
    @Override
    public void addSocket(SocketStack newSocket) {
        socket_core_socketStacks.add(newSocket);
        socket_core_stacksById.put(newSocket.getId(),newSocket);
    }
    @Override
    public void removeSocket(SocketStack oldSocket) {
        socket_core_socketStacks.remove(oldSocket);
    }
    @Override
    public SocketStack stackFromSocket(Socket socket) {
        return new SocketStack((ItemStack) (Object) this,socket);
    }

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
    private void gear_core_initializeFromItem(ItemConvertible item, int count, CallbackInfo ci){
        List<SocketProvider> providers = SocketRegistries.SOCKET_PROVIDER.getSocketProviders(item.asItem());
        for (SocketProvider provider : providers){
            for (Socket socket: provider.sockets()){
                this.addSocket(this.stackFromSocket(socket));
            }
        }
    }
}
