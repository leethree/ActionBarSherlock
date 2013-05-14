package com.actionbarsherlock.internal.view.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

public class HoverActionMenuItemView extends ActionMenuItemView {
    
    private boolean hovered = false;
    
    private boolean hasPerformedLongHover = false;
    private CheckForLongHover pendingCheckForLongHover;
    
    public HoverActionMenuItemView(Context context) {
        super(context);
    }

    public HoverActionMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HoverActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void onHoverChanged(boolean hovered) {
        if (hovered && !this.hovered) {
            checkForLongHover();
        }
        this.hovered = hovered;
        super.onHoverChanged(hovered);
    }
    
    private void checkForLongHover() {
        hasPerformedLongHover = false;

        if (pendingCheckForLongHover == null) {
            pendingCheckForLongHover = new CheckForLongHover();
        }

        this.postDelayed(pendingCheckForLongHover,
                ViewConfiguration.getLongPressTimeout());
    }
    
    private class CheckForLongHover implements Runnable {

        public void run() {
            if (hovered && !hasPerformedLongHover) {
                if (onLongHover()) {
                    hasPerformedLongHover = true;
                }
            }
        }
    }
    
    private boolean onLongHover() {
        return onLongClick(this);
    }
}
