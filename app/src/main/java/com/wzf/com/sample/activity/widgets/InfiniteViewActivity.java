package com.wzf.com.sample.activity.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.wzf.com.sample.R;
import com.wzf.com.sample.util.Tools;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.CubeInTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.DefaultTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;
import com.youth.banner.transformer.FlipVerticalTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.StackTransformer;
import com.youth.banner.transformer.TabletTransformer;
import com.youth.banner.transformer.ZoomInTransformer;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youth.banner.transformer.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soonlen on 2017/2/6.
 */

public class InfiniteViewActivity extends AppCompatActivity implements OnBannerClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.activity_infinite_listview)
    ListView listView;
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_viewpager);
        ButterKnife.bind(this);
        initData();
        init();
    }

    public void initData(){
        transformers.add(DefaultTransformer.class);
        transformers.add(AccordionTransformer.class);
        transformers.add(BackgroundToForegroundTransformer.class);
        transformers.add(ForegroundToBackgroundTransformer.class);
        transformers.add(CubeInTransformer.class);
        transformers.add(CubeOutTransformer.class);
        transformers.add(DepthPageTransformer.class);
        transformers.add(FlipHorizontalTransformer.class);
        transformers.add(FlipVerticalTransformer.class);
        transformers.add(RotateDownTransformer.class);
        transformers.add(RotateUpTransformer.class);
        transformers.add(ScaleInOutTransformer.class);
        transformers.add(StackTransformer.class);
        transformers.add(TabletTransformer.class);
        transformers.add(ZoomInTransformer.class);
        transformers.add(ZoomOutTranformer.class);
        transformers.add(ZoomOutSlideTransformer.class);
    }
    private void init() {
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.b1);
        list.add(R.drawable.b2);
        list.add(R.drawable.b3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
        banner.setOnBannerClickListener(this);

        String[] data=getResources().getStringArray(R.array.anim);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void OnBannerClick(int position) {
        Tools.showInfo(this, "点击了第" + position + "项");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        banner.setBannerAnimation(transformers.get(position));
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .crossFade()
                    .into(imageView);
        }
    }
}
