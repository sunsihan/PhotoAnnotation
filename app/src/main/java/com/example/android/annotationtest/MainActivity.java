package com.example.android.annotationtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button red_bn, blue_bn, green_bn;
    private Button reset_bn, add_bn, sub_bn;
    private TextView dotSize;
    public static final int DotSizeIncrement= 5;
    private PaintView drawPot;
    Drawable drawable;
    Switch erase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        erase=findViewById(R.id.erase);

        erase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){

                drawPot.setErase(true);}
                else{ drawPot.setErase(false); }

            }
        });

        this.init();
        Button openCamera= findViewById(R.id.camera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,0);
            }  });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap =(Bitmap)data.getExtras().get("data");
        drawable = new BitmapDrawable(getResources(), bitmap);
        PaintView photoView=findViewById(R.id.photoView);
        photoView.setBackground(drawable);}


    private void init() {
        red_bn=findViewById(R.id.red_bn);
        green_bn=findViewById(R.id.green_bn);
        blue_bn=findViewById(R.id.blue_bn);
        reset_bn=findViewById(R.id.reset_btn);
        add_bn=findViewById(R.id.add_bn);
        sub_bn=findViewById(R.id.sub_bn);
        dotSize=findViewById(R.id.dotSize);
        drawPot=findViewById(R.id.photoView);


        red_bn.setOnClickListener(this);
        blue_bn.setOnClickListener(this);
        green_bn.setOnClickListener(this);
        reset_bn.setOnClickListener(this);
        add_bn.setOnClickListener(this);
        sub_bn.setOnClickListener(this);
        dotSize.setText("BRUSH SIZE:"+drawPot.getDotSize());
        }


    @Override
    public void onClick(View view) {
        Button _b= findViewById(view.getId());
        switch (view.getId()){
            case R.id.red_bn:
                drawPot.setPenColor(Color.RED);
                Log.d("Button Pressed:", _b.getText()+"");
                break;
            case R.id.green_bn:drawPot.setPenColor(Color.GREEN);
                Log.d("Button Pressed:", _b.getText()+"");
                break;
            case R.id.blue_bn:drawPot.setPenColor(Color.BLUE);
                Log.d("Button Pressed:", _b.getText()+"");
                break;
            case R.id.reset_btn:drawPot.reset();
                dotSize.setText("BRUSH SIZE:"+drawPot.getDotSize());
                Log.d("Button Pressed:", _b.getText()+"");
                break;

            case R.id.add_bn:
                drawPot.changeSize(+DotSizeIncrement);
                dotSize.setText("BRUSH SIZE:"+drawPot.getDotSize());

                Log.d("Button Pressed:", _b.getText()+"");
                break;

            case R.id.sub_bn:
                drawPot.changeSize(-DotSizeIncrement);
                dotSize.setText("BRUSH SIZE:"+drawPot.getDotSize());

                Log.d("Button Pressed:", _b.getText()+"");
                break;
        }

    }
}
