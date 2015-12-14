package life.visage.visage;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

public class GridAutofitLayoutManager extends GridLayoutManager {
    private int mColumnWidth;
    private boolean mColumnWidthChanged = true;

    public GridAutofitLayoutManager(Context context, int columnWidth) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    public GridAutofitLayoutManager(
            Context context, int columnWidth, int orientation, boolean reverseLayout) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(Context context, int columnWidth) {
        if (columnWidth <= 0) {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    public void setColumnWidth(int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth) {
            mColumnWidth = newColumnWidth;
            mColumnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mColumnWidthChanged && mColumnWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / mColumnWidth);
            setSpanCount(spanCount);
            mColumnWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }

    public static class GridSpacingDecoration extends RecyclerView.ItemDecoration {
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
}