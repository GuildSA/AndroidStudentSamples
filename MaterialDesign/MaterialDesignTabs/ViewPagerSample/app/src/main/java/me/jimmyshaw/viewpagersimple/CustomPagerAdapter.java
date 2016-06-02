package me.jimmyshaw.viewpagersimple;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter
{
    private static final String TAG = "TESTING";

    private Context mContext;
    private List<DataModel> mItemList;

    private LayoutInflater mInflater;

    public CustomPagerAdapter(Context context, List<DataModel> itemList)
    {
        mContext = context;
        mItemList = itemList;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return mItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        // This method asks whether or not the view is related to the object. The object parameter
        // arrives as an argument from the below instantiateItem method. Since instantiateItem
        // should return a view (which is an object) then this method should return true.
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Log.i(TAG, "instantiateItem for position " + position + " [ Item " + (position + 1) + " ]");

        // This method will instantiate each item at each position within the view pager.

        // Get the view of the single view pager item.
        View itemView = mInflater.inflate(R.layout.viewpager_item, container, false);

        // Locate the image view and text view.
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_item);
        TextView textView = (TextView) itemView.findViewById(R.id.textview_item);

        // Get the data model for the current position.
        DataModel dataModel = mItemList.get(position);

        // Set the data for image and text.
        imageView.setImageResource(dataModel.imageId);
        textView.setText(dataModel.title);

        // Add viewpager_item.xml to view pager.
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        // The purpose of this method is to destroy items in the view pager that are not shown on
        // the screen so that the device's memory can be managed properly.
        Log.i(TAG, "destroyItem for position " + position + " [ Item " + (position + 1) + " ]");

        // Remove view of viewpager_item.xml from view pager container. 
        // We know to cast this object as a FrameLayout because the parent container of every
        // view pager item is a FrameLayout.
        container.removeView((FrameLayout) object);
    }
}
