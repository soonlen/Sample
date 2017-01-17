package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;
import com.wzf.com.sample.view.ClearEditText;
import com.wzf.com.sample.view.DelTextView;
import com.wzf.com.sample.view.tag.Tag;
import com.wzf.com.sample.view.tag.TagCloudLinkView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soonlen on 2016/11/28.
 */

public class TextActivity extends AppCompatActivity {

    private ClearEditText mEt;
    private DelTextView mDelText;
    private String text = "走在马上上";
    private TagCloudLinkView mTagView;
    List<Tag> tags = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        mEt = (ClearEditText) findViewById(R.id.activity_clear_et);
        mDelText = (DelTextView) findViewById(R.id.activity_clear_dt);
        mDelText.setText(text);
        mDelText.setDelTextViewInterface(new DelTextView.DelTextViewInterface() {
            @Override
            public void del() {
                mDelText.setText("");
            }
        });
        mTagView = (TagCloudLinkView) findViewById(R.id.activity_clear_tag_view);
        for (int i = 0; i < 10; i++) {
            Tag t = new Tag(i, "第" + (i + 1) + "个标签");
            tags.add(t);
            mTagView.add(t);
        }
        mTagView.drawTags();
        mTagView.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int position) {
                L.e("删除了第" + (position + 1) + "个子项！");
            }
        });
    }


    public void reset(View v) {
        mDelText.setText(text);
    }

    public void resetTag(View v) {
        if (mTagView.getTags().size() != 0) {
            mTagView.removelAllTags();
        }
        for (int i = 0; i < tags.size(); i++) {
            mTagView.add(tags.get(i));
        }
        mTagView.drawTags();
    }
}
