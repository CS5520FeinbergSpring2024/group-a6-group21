package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class Banner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        ViewPager2 viewPager = findViewById(R.id.viewPager);
        List<Integer> images = Arrays.asList(
                R.drawable.toronto, R.drawable.washington, R.drawable.mexico,
                R.drawable.sydney, R.drawable.turkey, R.drawable.sahara
        );
        StickerAdapter adapter = new StickerAdapter(images);
        viewPager.setAdapter(adapter);


        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer((page, position) -> {
            final float minScale = 0.85f;
            final float maxScale = 1f;
            final float scaleFactor = minScale + (1 - Math.abs(position)) * (maxScale - minScale);

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            if (position < -1) {
                page.setAlpha(0f);
            } else if (position <= 1) {
                // Modify alpha based on position
                page.setAlpha(Math.max(0.4f, 1 - Math.abs(position)));
            } else {
                page.setAlpha(0f);
            }
        });

    }



}