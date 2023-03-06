package me.fzzyhmstrs.socket_core.interfaces;

import com.google.gson.*;
import me.fzzyhmstrs.socket_core.SC;
import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import me.fzzyhmstrs.socket_core.socket.BasicSocketable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class SocketableType<T extends Socketable<?>> {
    abstract JsonElement toJson(T input);
    abstract T fromJson(JsonElement input);

    public static final SocketableType<BasicSocketable> SIMPLE = Registry.register(SocketRegistries.SOCKETABLE_TYPE,new Identifier(SC.MOD_ID,"simple_type"), new SocketableType<BasicSocketable>(){

        private Gson gson = new GsonBuilder().setPrettyPrinting().create();

        @Override
        public JsonElement toJson(BasicSocketable input) {
            JsonObject object = new JsonObject();
            object.add("item",new JsonPrimitive(Registry.ITEM.getId(input.item()).toString()));

            return object;
        }

        @Override
        public BasicSocketable fromJson(JsonElement input) {
            return null;
        }

        public String toString() {
            return SC.MOD_ID + ":simple_type";
        }
    });
}
