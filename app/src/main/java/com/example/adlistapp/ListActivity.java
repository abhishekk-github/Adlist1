package com.example.adlistapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import static com.example.adlistapp.ListStyleModel.getModelFromJson;
import static com.example.adlistapp.ListingItems.getItemListFromJson;


public class ListActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private ProductListAdapter mAdapter;
  private ListStyleModel mListStyleModel;
  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    init();
  }

  private void init() {
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    mProgressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addOnChildAttachStateChangeListener(new ChildAttachListener(layoutManager));
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    fetchDataFromServer();
    setOnitemClickListener();
  }

  private void setOnitemClickListener() {
    recyclerView.addOnItemTouchListener(
        new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
          @Override public void onItemClick(View view, int position) {
            // do whatever
            ListingItems items =  mAdapter.getItem(position);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.getTargeturl()));
            startActivity(browserIntent);
          }
          @Override public void onLongItemClick(View view, int position) {
          }
        })
    );
  }


  private void fetchDataFromServer() {
    mProgressBar.setVisibility(View.VISIBLE);
    String JSON_URL =  "http://www.tyroocentral.com/www/api/v3/API.php?requestParams={ \"randohum\": \"cjjlsk\", \"ads\": [ { \"adViewId\": \"1376 \", \"isAdWall\": \"false\", \"sendRepeat\": \"false\", \"size\": \"10\", \"startIndex\": \"0\" } ], \"apiVersion\": \"3\", \"deviceLanguage\": \"en\", \"deviceX\": \"1080\", \"deviceY\": \"1776\", \"directImageUrl\": \"1\", \"subid1\": \"hello\", \"subid2\": \"how\", \"subid4\": \"you\", \"subid3\": \"are\", \"subid5\": \"fine\", \"idfa\": \"6D92078A - 8246 - 4BA4 - AE5B - 76104861E7DC\" , \"gaid\": \"d4f8030b - 4a52 - 4261 - 9830 - bd6c987cd261\", \"hashCode\": \"QEfsvw8LSjssGSpSPNb+SissH0v890dODTkiTAY+QO33OxEW\", \"isMobile\": \"true\", \"packageName\": \"009\", \"adWallId\":\"\", \"requestSource\": \"SDK\", \"dynamicPlacement\": \"false\" }";
        StringRequest stringRequest = new StringRequest(JSON_URL.replace(" ",""),
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
                  Log.v("MSG",response);
            mListStyleModel=getModelFromJson(response);
            Picasso.with(ListActivity.this).load(mListStyleModel.getLoadingImage());
            mAdapter = new ProductListAdapter(ListActivity.this,getItemListFromJson(response),mListStyleModel);
            recyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
              try {
                String res = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                JSONObject obj = new JSONObject(res);
              } catch (UnsupportedEncodingException | JSONException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
              }
            }
          }
        });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
  }

  private class ChildAttachListener implements RecyclerView.OnChildAttachStateChangeListener {
    LinearLayoutManager llm;
    int Max_visible=0;
    Set<ListingItems> set = new HashSet<>();

    ChildAttachListener(LinearLayoutManager llm){
      super();
      this.llm = llm;
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {

      Handler handler = new Handler();
      handler.post(new Runnable() {
        @Override
        public void run() {
         if(Max_visible < llm.findLastVisibleItemPosition()) {
           Max_visible = llm.findLastVisibleItemPosition();
           Log.v("Impression work ",Max_visible + "  Max visible");
           for(int itr = Max_visible ; itr>=0 ;itr--){
             ListingItems items = mAdapter.getItem(itr);
             if(items.getOffertype().equalsIgnoreCase("App") && !items.isImpressingTrackingFlag()) {
               if(!mListStyleModel.isAdUnitUrlFired()){
                 startAdUnitTracking(mListStyleModel);
                 mListStyleModel.setAdUnitUrlFired(true);
               }
               set.add(mAdapter.getItem(itr));
               items.setImpressingTrackingFlag(true);
               startImpressionTracking(items);
             }
           }
         }
        }
      });
    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {

    }
  }

  private void startAdUnitTracking(final ListStyleModel listStyleModel) {
    StringRequest stringRequest = new StringRequest(Request.Method.GET,listStyleModel.getAduniturl().replace(" ",""),
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            listStyleModel.setAdUnitUrlFired(true);
            Log.v("Impression work",listStyleModel.getCreativeType() + "  unit tracking hit");
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            listStyleModel.setAdUnitUrlFired(false);
          }
        });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
  }

  public void startImpressionTracking(final ListingItems item){
    StringRequest stringRequest = new StringRequest(Request.Method.GET,item.getImpressiontracking().replace(" ",""),
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            item.setImpressingTrackingFlag(true);
            Log.v("Impression work",item.getName() + "  impression tracking hit");
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            item.setImpressingTrackingFlag(false);
          }
        });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
  }
}
