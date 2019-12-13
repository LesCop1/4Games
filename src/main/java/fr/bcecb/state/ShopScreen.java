package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.Profile;
import fr.bcecb.ProfilePictureItem;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

import java.util.List;

public class ShopScreen extends ScreenState {
    private List<ProfilePictureItem> items;

    private int currentPage;
    private int selectedItem;

    public ShopScreen(StateManager stateManager) {
        super(stateManager, "shop");

        this.items = Constants.shopItems;

        this.currentPage = 1;
        this.selectedItem = -1;

        setBackgroundTexture(Resources.DEFAULT_BACKGROUND_TEXTURE);
    }

    @Override
    public void initGui() {
        Rectangle topBanner = new Rectangle(1, 0, 0, width, 2 * (height / 20f), false, Constants.COLOR_BANNER);
        Text title = new Text(2, (width / 2f), (height / 20f), true, "Boutique", 2f);
        title.setColor(Constants.COLOR_BLACK);

        Text moneyAmount = new Text(11, 4 * (width / 5f), (height / 20f), true, "") {
            @Override
            public String getText() {
                return "FC : " + Game.instance().getProfile().getMoneyAmount();
            }
        };
        moneyAmount.setColor(Constants.COLOR_BLACK);

        float itemsStartX = 5f;
        float itemsStartY = 2 * (height / 20f) + 5f;
        float itemsHeight = height / 3f;
        float itemsWidth = width / 5f;
        float itemsPreviewRadius = 20f;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;

                RoundedButton itemBG = new RoundedButton(100 + finalI + (finalJ * 10), itemsStartX + (finalJ * itemsWidth), itemsStartY + (finalI * itemsHeight), itemsWidth - 10f,
                        itemsHeight - 10f, 2f, Resources.DEFAULT_BUTTON_TEXTURE) {
                    @Override
                    public boolean isVisible() {
                        return items.size() > ((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return Game.instance().getProfile().getItemsOwns().contains(((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1) ? Resources.DEFAULT_BUTTON_CONFIRMED : Resources.DEFAULT_BUTTON_TEXTURE;
                    }
                };

                float itemCenteredX = (finalJ * itemsWidth) + (itemsWidth / 2f);
                float itemCenteredY = (finalI * itemsHeight) + (itemsHeight / 2f);
                CircleImage itemImage = new CircleImage(200 + finalI + (finalJ * 10), null, itemCenteredX - itemsPreviewRadius, itemCenteredY, itemsPreviewRadius) {
                    @Override
                    public ResourceHandle<Texture> getImage() {
                        return items.get(((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1).getTexture();
                    }

                    @Override
                    public boolean isVisible() {
                        return items.size() > ((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1;
                    }
                };
                Text itemName = new Text(300 + finalI + (finalJ * 10), itemCenteredX, itemCenteredY + (itemsHeight / 3f) + 15f, true, "", 0.8f) {
                    @Override
                    public String getText() {
                        return items.get(((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1).getName();
                    }

                    @Override
                    public boolean isVisible() {
                        return items.size() > ((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1;
                    }
                };
                Text itemPrice = new Text(400 + finalI + (finalJ * 10), itemCenteredX, itemCenteredY + (itemsHeight / 3f) + 15f + 15f, true, "", 0.6f) {
                    @Override
                    public String getText() {
                        return items.get(((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1).getPrice() + "FC";
                    }

                    @Override
                    public boolean isVisible() {
                        return items.size() > ((finalI * 3) + (finalJ + 1) + ((currentPage - 1) * 6)) - 1;
                    }
                };
                addGuiElement(itemBG, itemImage, itemName, itemPrice);
            }
        }

        float previewBGStartX = (itemsWidth * 3);
        float previewBGCenteredX = previewBGStartX + ((width - previewBGStartX) / 2f);
        float previewBGWidth = (width - previewBGStartX) - 10f;
        float previewBGHeight = 2 * itemsHeight;
        RoundedButton previewItemBG = new RoundedButton(6, previewBGStartX + 5f, itemsStartY, previewBGWidth, previewBGHeight,
                2f, Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return getTexture();
            }

            @Override
            public boolean isVisible() {
                return selectedItem != -1;
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                if (Game.instance().getProfile().getItemsOwns().contains(items.get(selectedItem).getId())) {
                    // TODO IS USE
                    return Resources.DEFAULT_BUTTON_CONFIRMED;
                } else {
                    return Resources.DEFAULT_BUTTON_TEXTURE;
                }
            }
        };

        float radius = previewBGWidth / 4;
        Image previewItem = new CircleImage(7, null, previewBGCenteredX - radius, (previewBGHeight / 2f) - radius, radius) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return items.get(selectedItem).getTexture();
            }

            @Override
            public boolean isVisible() {
                return selectedItem != -1;
            }
        };

        Text previewTitle = new Text(8, previewBGCenteredX, itemsStartY + (3 * (previewBGHeight / 5f)), true, "", 1.2f) {
            @Override
            public String getText() {
                return items.get(selectedItem).getName();
            }

            @Override
            public boolean isVisible() {
                return selectedItem != -1;
            }
        };

        Text previewDesc = new Text(9, previewBGCenteredX, itemsStartY + (3 * (previewBGHeight / 5f)) + 20f, true, "", 0.8f) {
            @Override
            public String getText() {
                return items.get(selectedItem).getDesc();
            }

            @Override
            public boolean isVisible() {
                return selectedItem != -1;
            }
        };
        previewDesc.setColor(Constants.COLOR_GREY);

        RoundedButton previewBuyButton = new RoundedButton(10, previewBGCenteredX, itemsStartY + (4 * (previewBGHeight / 5f)) + 20f, previewBGWidth / 2f, previewBGHeight / 8f, 2f, true, "", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public String getTitle() {
                return items.get(selectedItem).getPrice() + Constants.MONEY_NAME_SHORT;
            }

            @Override
            public boolean isVisible() {
                return selectedItem != -1;
            }

            @Override
            public boolean isDisabled() {
                return items.contains(selectedItem);
            }

            @Override
            public ResourceHandle<Texture> getDisabledTexture() {
                return Resources.DEFAULT_BUTTON_CONFIRMED;
            }
        };

        addGuiElement(previewItem, previewItemBG, previewTitle, previewDesc, previewBuyButton);

        float backButtonY = height - (height / 7.5f);
        RoundedButton backButton = new RoundedButton(3, (2 * (height / 20f) + 15) / 2, backButtonY + ((height - backButtonY) / 2), 2 * (height / 20f), 2 * (height / 20f), 5f, true, "Quitter", Resources.DEFAULT_BUTTON_TEXTURE);
        RoundedButton prevButton = new RoundedButton(4, (width / 5f), backButtonY + ((height - backButtonY) / 2), (width / 5f) - 4, 2 * (height / 20f),
                2f, true, "Retour", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public boolean isVisible() {
                return currentPage > 1;
            }
        };
        RoundedButton nextButton = new RoundedButton(5, 2 * (width / 5f), backButtonY + ((height - backButtonY) / 2), (width / 5f) - 4, 2 * (height / 20f),
                2f, true, "Suivant", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public boolean isVisible() {
                return items.size() / 6f > currentPage;
            }
        };

        addGuiElement(topBanner, title, moneyAmount, backButton, prevButton, nextButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == 3) {
            stateManager.popState();
        } else if (id == 4) {
            this.currentPage--;
        } else if (id == 5) {
            this.currentPage++;
        } else if (id == 10) {
            Profile profile = Game.instance().getProfile();
            ProfilePictureItem item = items.get(selectedItem);
            if (!profile.getItemsOwns().contains(item.getId())) {
                if (profile.getMoneyAmount() >= item.getPrice()) {
                    profile.remMoneyAmount(item.getPrice());
                    profile.addItem(item.getId());
                    profile.save();
                }
            }
        } else if (id >= 100 && id < 200) {
            id = id - 100;
            int i = id % 10;
            int j = id / 10;
            this.selectedItem = ((i * 3) + (j + 1) + ((currentPage - 1) * 6)) - 1;
        }
        return false;
    }
}
