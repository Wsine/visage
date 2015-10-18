package life.visage.visage;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GridSpacingDecoration extends RecyclerView.ItemDecoration {
    private int spacing;
    private int half_spacing;

    public GridSpacingDecoration(int spacing) {
        this.spacing = spacing;
        this.half_spacing = spacing / 2;
    }

    @Override
    public void getItemOffsets(
        Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        int childIndex = parent.getChildPosition(view);
        int spanCount = getTotalSpan(view, parent);

        /* INVALID SPAN */
        if (spanCount < 1) return;

        outRect.left = half_spacing;
        outRect.right = half_spacing;
        outRect.bottom = spacing;

        if (parent.getAdapter() instanceof SectionedRecyclerViewAdapter) {
            outRect.top = childIndex == 0 ? spacing : 0;
        }
        else {
            outRect.top = childIndex < spanCount ? spacing : 0;
        }
    }

    protected int getTotalSpan(View view, RecyclerView parent) {
        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
        }

        return -1;
    }
}