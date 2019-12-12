package fr.bcecb.state.gui;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.StateManager;
import fr.bcecb.util.Constants;

public class RulesPopUpScreen extends PopUpScreenState {
    private String rules;
    private String gameName;

    public RulesPopUpScreen(StateManager stateManager, String gameName, String rules, float width, float height) {
        super(stateManager, "game_rules_" + gameName.toLowerCase(), width, height, Constants.COLOR_LIGHT_GREY);
        this.rules = rules;
        this.gameName = gameName;
    }

    @Override
    public void initGui() {
        int id = 1000;
        Text title = new Text(++id, (width / 2f), ((height - backgroundHeight + 35f) / 2f), true, ("Règles | " + this.gameName), 1.5f);

        int i = 1;
        for (String s : rules.split("(?<=\\G.{35,}\\s)")) {
            Text t = new Text(++id, (width / 2f), ((height - backgroundHeight + 60f + (i * 20f)) / 2f), true, s, 0.75f);
            i++;
            addGuiElement(t);
        }

        addGuiElement(title);
    }

    @Override
    public boolean mouseClicked(MouseButton mouseButton, float x, float y) {
        float startX = (width - backgroundWidth) / 2f;
        float startY = (height - backgroundHeight) / 2f;
        float endX = startX + backgroundWidth;
        float endY = startY + backgroundHeight;

        if (!(startX < x) || !(x < endX) || !(startY < y) || !(y < endY)) {
            stateManager.popState();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        return false;
    }

    @Override
    public boolean shouldPauseBelow() {
        return true;
    }
}
