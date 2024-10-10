package com.santacarolina.ui;

import com.formdev.flatlaf.util.Animator;
import com.santacarolina.areas.mainFrame.mainPage.SideMenu;
import com.santacarolina.util.MenuDecorator;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.beans.BeanProperty;
import java.util.ArrayList;
import java.util.List;

public class SubMenuImpl {

    private static final int MAX_HEIGHT = 80;

    private SideMenu father;
    private JPanel pane;
    private JButton mainButton;
    private List<AbstractButton> buttons = new ArrayList<>();
    private boolean active;
    private MigLayout layout;

    public SubMenuImpl(SideMenu father, String menuName) {
        this.father = father;
        mainButton = new JButton(menuName);
        MenuDecorator.paintButton(mainButton);
        init();
    }

    private void init() {
        layout = new MigLayout("insets 0, gap 0, flowy",
                "fill, grow",
                "fill");

        pane = new JPanel(layout);
        mainButton.addActionListener(e -> revalidateMenu());
        pane.add(mainButton);
        MenuDecorator.paintPanel(pane);
    }

    public JPanel getPane() { return pane; }

    @BeanProperty(visualUpdate = true, description = "The button's default icon")
    public void setMainIcon(Icon defaultIcon) { mainButton.setIcon(defaultIcon); }

    public void addButton(AbstractButton button) {
        MenuDecorator.paintButton(button);
        pane.add(button, "h 0!");
        buttons.add(button);
    }

    private void revalidateMenu() {
        if (active) {
            active = false;
            Animator animator = new Animator(300, this::narrowButtons);
            animator.start();
        } else {
            active = true;
            Animator animator = new Animator(300, this::expandButton);
            animator.start();
        }
    }

    private void expandButton(float fraction) {
        buttons.forEach(b -> layout.setComponentConstraints(b, "gap 20, h " + MAX_HEIGHT * fraction + "!"));
        pane.repaint();
        father.revalidate();
    }

    private void narrowButtons(float fraction) {
        float heightMultiplier = 1 - fraction;
        buttons.forEach(b -> layout.setComponentConstraints(b, "gap 20, h " + MAX_HEIGHT * heightMultiplier + "!"));
        pane.repaint();
        father.revalidate();
    }

}
