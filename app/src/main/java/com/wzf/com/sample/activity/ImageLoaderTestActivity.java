package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.image.ImageLoader;
import com.wzf.com.sample.util.Images;
import com.wzf.com.sample.util.L;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageLoaderTestActivity extends AppCompatActivity {

    @BindView(R.id.activity_image_loader_test_recycle_view)
    RecyclerView mRecyclerView;

    private List<String> images;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_test);
        ButterKnife.bind(this);
        ImageLoader.init(getApplicationContext());
        images = Arrays.asList(Images.imageThumbUrls);
        L.e("图片列表大小：" + images.size());
        adapter = new ImageAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

        @Override
        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageHolder(LayoutInflater.from(ImageLoaderTestActivity.this).inflate(R.layout.image_loader_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ImageHolder holder, int position) {
            ImageLoader.getInstance().loadImage(images.get(position), holder.imageView);
            holder.textView.setText("条目" + (position + 1));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        class ImageHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;

            public ImageHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_loader_item_iv);
                textView = (TextView) itemView.findViewById(R.id.image_loader_item_tv);
            }
        }
    }
}
