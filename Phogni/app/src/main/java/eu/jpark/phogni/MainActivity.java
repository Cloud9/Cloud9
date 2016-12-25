package eu.jpark.phogni;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final int REQ_CODE_SELECT_IMAGE = 1000;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));
        setCustomActionbar(); // 커스텀 액션바 적용

        linearLayout = (LinearLayout) findViewById(R.id.picLayout);
    }

    public void takeAlbum(View v) {
        doTakePicAction();
    }

    public void doTakePicAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                   // ClipData clipData = data.getClipData();
                    String name_Str = null;
                    //Uri에서 이미지 이름을 얻어온다.
                   // for (int i = 0; i < clipData.getItemCount(); i++) {
                        name_Str = getImageNameToUri(data.getData());
                        //Uri uri = clipData.getItemAt(i).getUri();
                   // }
                        //이미지 데이터를 비트맵으로 받아온다.
                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        ImageView image = new ImageView(this);
                        image.setLayoutParams(new LinearLayout.
                                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        //배치해놓은 ImageView에 set
                        image.setImageBitmap(image_bitmap);
                        linearLayout.addView(image);

                        Toast.makeText(getBaseContext(), "name_Str : " + name_Str, Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getImageNameToUri(Uri data) {
    //public String getImageNameToUri(ClipData.Item item) {
        //Uri data = item.getUri();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    private void setCustomActionbar() {

        ActionBar actionBar = getSupportActionBar();

        // 커스텀 액션바를 사용하기 위해 필요한 것만 true
        actionBar.setDisplayShowCustomEnabled(true); // 커스텀하겠다
        actionBar.setDisplayHomeAsUpEnabled(false); //네비게이션 ㄴㄴ
        actionBar.setDisplayShowTitleEnabled(false); // 제목 ㄴㄴ

        // 적용
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.layout_actionbar, null);
        actionBar.setCustomView(mCustomView);

        // 패딩 ㄴㄴ
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ImageButton actionBtn = (ImageButton) findViewById(R.id.bar_action);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 클릭했을 때!
                Toast.makeText(MainActivity.this, "혜린찡이 아직 안정함", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
