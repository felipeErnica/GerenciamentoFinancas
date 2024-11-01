package com.santacarolina.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * RoundedBorder
 */
public class RoundedBorder implements Border {

    private LineBorder baseBorder;
    private Color color;

    public RoundedBorder(Color color) {
        this.baseBorder = new LineBorder(color);
        this.color = color;
    }

    @Override
    public Insets getBorderInsets(Component c) { return baseBorder.getBorderInsets(c); }

    @Override
    public boolean isBorderOpaque() { return baseBorder.isBorderOpaque(); }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D roundGraphics = (Graphics2D) g.create();
        roundGraphics.setColor(color);
        roundGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        roundGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        roundGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        roundGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        roundGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        roundGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        roundGraphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        roundGraphics.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, width, height));
        roundGraphics.dispose();
    }

    
}
