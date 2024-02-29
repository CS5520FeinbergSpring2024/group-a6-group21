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
        viewPager.setPageTransformer(new MarginPageTransformer(150));
    }

}