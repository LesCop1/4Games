package fr.bcecb.util;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class ItemDescriptor {
    private int id;
    private ResourceHandle<Texture> texture;
    private String name;
    private String desc;
    private int price;

    public ItemDescriptor(int id, ResourceHandle<Texture> texture, String name, String desc, int price) {
        this.id = id;
        this.texture = texture;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public ResourceHandle<Texture> getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }
}
