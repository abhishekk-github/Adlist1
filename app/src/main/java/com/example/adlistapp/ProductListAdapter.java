package com.example.adlistapp;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

  private List<ListingItems> itemList;
  private Context mContext;
  private ListStyleModel mListStyleModel;

  public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title, actualPrice, discountPrice;
    Button discount_button;
    ImageView mImageView;

    public MyViewHolder(View view) {
      super(view);
      title = (TextView) view.findViewById(R.id.productname_tv);
      actualPrice = (TextView) view.findViewById(R.id.actualpriceTv);
      discountPrice = (TextView) view.findViewById(R.id.discountpriceTv);
      discount_button = (Button) view.findViewById(R.id.dis_per_button);
      mImageView = (ImageView) view.findViewById(R.id.product_image_view);
      title.setTextSize(TypedValue.COMPLEX_UNIT_PX,mListStyleModel.getTitleFontSize()*3);
      actualPrice.setPaintFlags(actualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
  }

  public ProductListAdapter(Context context,List<ListingItems> itemList,ListStyleModel listStyleModel) {
    this.itemList = itemList;
    this.mContext = context;
    this.mListStyleModel = listStyleModel;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.product_item_layout, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position) {
    ListingItems items = itemList.get(position);
    holder.title.setText(items.getName());
    holder.actualPrice.setText("₹"+items.getActualprice()+"");
    holder.discountPrice.setText("₹"+items.getDiscountprice()+"");
    holder.discount_button.setText(items.getDiscountpercent()+"%");
    if("App".equalsIgnoreCase(items.getOffertype()) ||  "VAS".equalsIgnoreCase(items.getOffertype())) {
      holder.discount_button.setText(R.string.install_str);
      holder.actualPrice.setVisibility(View.GONE);
      holder.discountPrice.setVisibility(View.GONE);
    }
    Picasso.with(mContext).load(items.getDirectimageurl()).placeholder(R.drawable.progress_animation ).into(holder.mImageView);
  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  public ListingItems getItem(int position) {
    return itemList.get(position);
  }
}

