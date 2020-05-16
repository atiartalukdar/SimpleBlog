package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.atiar.simpleblog.ArticleActivity;
import info.atiar.simpleblog.R;
import model.CategoryModel;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private List<CategoryModel> categoryModelList;
    int lan;
    private final String TAG = getClass().getSimpleName() + " Atiar= ";

    public CategoryAdapter(Activity activity, List<CategoryModel> categoryModelList, int lan) {
        this.activity = activity;
        this.categoryModelList = categoryModelList;
        this.lan = lan;
    }

    @Override
    public int getCount() {
        return categoryModelList.size();
    }

    @Override
    public Object getItem(int location) {
        return categoryModelList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = activity.getApplicationContext();

        // getting lead data for the row
        final CategoryModel categoryModel = categoryModelList.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_item, null);

        LinearLayout listItem = convertView.findViewById(R.id.list_item);
        TextView ctg = convertView.findViewById(R.id.category);
        TextView leftColor = convertView.findViewById(R.id.leftColor);
        TextView rightColor = convertView.findViewById(R.id.rightColor);

        leftColor.setBackgroundColor(Color.parseColor(categoryModel.getCatColorCode()));
        rightColor.setBackgroundColor(Color.parseColor(categoryModel.getCatColorCode()));

        ctg.setBackgroundColor(activity.getResources().getColor(R.color.white));
        ctg.setText(categoryModel.getCategory());


        if (lan==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ctg.setText(Html.fromHtml(categoryModel.getCategory(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                ctg.setText(Html.fromHtml(categoryModel.getCategory()));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ctg.setText(Html.fromHtml(categoryModel.getCatOtherLan(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                ctg.setText(Html.fromHtml(categoryModel.getCatOtherLan()));
            }
        }


            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", categoryModel.getId());
                    intent.putExtra("ctg", categoryModel.getCategory());
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    Log.e(TAG, "Category ID = " + categoryModel.getId() + "Article name = " + categoryModel.getCategory() );

                }
            });

/*        Picasso.get()
                .load(member.getMEMBER_PHOTO_URL())
                .placeholder(R.drawable.dummy_profile)
                .error(R.drawable.dummy_profile)
                .into(_profileImage);
        */


        return convertView;

    }

    //To update the searchView items
    public void update(List<CategoryModel> resuls){
        categoryModelList = new ArrayList<>();
        categoryModelList.addAll(resuls);
        notifyDataSetChanged();
    }


}