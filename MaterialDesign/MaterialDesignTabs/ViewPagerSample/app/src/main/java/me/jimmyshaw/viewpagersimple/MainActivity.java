package me.jimmyshaw.viewpagersimple;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare the data model.
        List<DataModel> itemList = getDataList();

        // Locate the view pager in activity_main.xml.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an instance of our pager adapter.
        CustomPagerAdapter adapter = new CustomPagerAdapter(this, itemList);

        // Bind the adapter to the view pager.
        viewPager.setAdapter(adapter);
    }

    public List<DataModel> getDataList()
    {
        List<DataModel> itemList = new ArrayList<>();

        int[] imageIds = new int[]
                {
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        R.drawable.image4, R.drawable.image5, R.drawable.image6
                };

        String[] titles = new String[]
                {
                        "Image 1", "Image 2", "Image 3",
                        "Image 4", "Image 5", "Image 6"
                };

        int count = imageIds.length;

        for (int i = 0; i < count; i++)
        {
            itemList.add(new DataModel(imageIds[i], titles[i]));
        }

        return itemList;
    }
}
