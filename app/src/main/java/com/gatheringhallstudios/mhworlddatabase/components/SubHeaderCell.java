package com.gatheringhallstudios.mhworlddatabase.components;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gatheringhallstudios.mhworlddatabase.R;

/**
 * This is a full height, full width cell that displays a sub header. Used to generate
 * header rows in RecyclerView or inside XML layouts.
 */

public class SubHeaderCell extends LinearLayout {

    private final String TAG = getClass().getSimpleName();

    TextView labelView;

    public SubHeaderCell(Context context, String labelText) {
        super(context);
        init(labelText);
    }

    public SubHeaderCell(Context context) {
        super(context);
        init("");
    }

    public SubHeaderCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SubHeaderCell);

        // Set values from attributes
        String labelText;
        try {
            labelText = attributes.getString(R.styleable.SubHeaderCell_labelText);
        } finally {
            // Typed arrays should be recycled after use
            attributes.recycle();
        }

        init(labelText);
    }

    public void init(String labelText) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.cell_sub_header, this, true);
        setTag(R.id.view_is_header, true);

        labelView = findViewById(R.id.label_text);

        setLabelText(labelText);
    }

    public void setLabelText(String labelText) {
        labelView.setText(labelText);
    }

}
