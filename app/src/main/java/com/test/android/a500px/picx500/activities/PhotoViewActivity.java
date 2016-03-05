package com.test.android.a500px.picx500.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.android.a500px.picx500.R;
import com.test.android.a500px.picx500.models.Photo;
import com.test.android.a500px.picx500.views.ScaleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoViewActivity extends AppCompatActivity {

    public static final String EXTRAS_PHOTO_OBJECT = "extras_photo_object";

    @Bind(R.id.photo)
    ScaleImageView photo;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.camera)
    TextView camera;

    @Bind(R.id.shareButton)
    FloatingActionButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Photo photo = intent.getParcelableExtra(EXTRAS_PHOTO_OBJECT);
        if (photo == null) {
            throw new IllegalArgumentException("Activity must retrive EXTRAS_PHOTO_OBJECT");
        }

        setupUI(photo);
        setupShareButton(photo);
    }

    private void setupUI(final Photo photo) {
        setTitle(photo.getName());
        author.setText(photo.getAuthor().getFullName());
        camera.setText(photo.getCamera());
        Glide
                .with(this)
                .load(photo.getImageUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        PhotoViewActivity.this.photo.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(this.photo);
    }

    private void setupShareButton(final Photo photo) {

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, photo.getName(), photo.getCamera(), photo.getAuthor().getFullName(), photo.getImageUrl()));
                startActivity(intent);
            }
        });
    }


}
