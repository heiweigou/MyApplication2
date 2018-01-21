package com.example.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String CONTENT_AUTHORITY = "edu.monash.fit2081.db.provider";

    //Content URIs will use the following as their base
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_VERSION = "shapes";
    // Content Uri = Content Authority + Path (shapes in this case)
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VERSION).build();
    public static final String SHAPE_NAME = "shape_name";
    public static final String SHAPE_TYPE = "shape_type";
    public static final String SHAPE_WIDTH = "shape_width";
    public static final String SHAPE_HEIGHT = "shape_height";
    public static final String SHAPE_RADIUS = "shape_radius";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = findViewById(R.id.textView);
        Button circle = findViewById(R.id.circle);
        Button rectangle = findViewById(R.id.rectangle);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] project = {SHAPE_NAME, SHAPE_TYPE,SHAPE_RADIUS,SHAPE_HEIGHT,SHAPE_WIDTH};
                final String selection = SHAPE_TYPE + "=?";
                final String[] args = {"Circle"};
                final String sortOrder = null;
                final ContentResolver contentResolver = getApplicationContext().getContentResolver();
                final Cursor cursor = contentResolver.query(CONTENT_URI, project, selection, args, sortOrder);
                double maxArea=0;
                String type="";
                if (cursor.moveToFirst()){
                     type=cursor.getString(cursor.getColumnIndex(SHAPE_TYPE));
                    do {
                        double currentArea=Math.PI*Math.pow(cursor.getInt(cursor.getColumnIndex(SHAPE_RADIUS)),2.0);
                        if (currentArea>maxArea) {
                            maxArea=currentArea;
                        }
                    }
                    while (cursor.moveToNext());

                }
                textView.setText("there are " + cursor.getCount()+" type is "+type +"max area is "+maxArea);
            }
        });

        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] project = {SHAPE_NAME, SHAPE_TYPE,SHAPE_RADIUS,SHAPE_HEIGHT,SHAPE_WIDTH};
                final String selection = SHAPE_TYPE + "=?";
                final String[] args = {"Rectangle"};
                final String sortOrder = null;
                final ContentResolver contentResolver = getApplicationContext().getContentResolver();
                final Cursor cursor = contentResolver.query(CONTENT_URI, project, selection, args, sortOrder);
                int maxArea=0;
                String type="";
                if (cursor.moveToFirst()){
                    type=cursor.getString(cursor.getColumnIndex(SHAPE_TYPE));
                    do {
                        int currentArea=cursor.getInt(cursor.getColumnIndex(SHAPE_HEIGHT))*cursor.getInt(cursor.getColumnIndex(SHAPE_WIDTH));
                        if (currentArea>maxArea) {
                            maxArea=currentArea;
                        }
                    }
                    while (cursor.moveToNext());

                }
                textView.setText("there are " + cursor.getCount()+" type is "+type +"max area is "+maxArea);
            }
        });
    }


}

