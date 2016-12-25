package eu.jpark.phogni;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by JPark on 2016-12-25.
 */

public class SplashActivity extends AppCompatActivity {

    private int REQUEST_READ_STORAGE = 1000;
    //private int REQUEST_WRITE_STORAGE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    private void checkPermission(){

        // 마쉬멜로우 이상 버젼인지?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            Log.d("권한 체크?", "들어옴");
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                // 권한이 없을 때
                Log.d("권한 체크?", "들어옴2");
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // 권한을 거부한 적이 있나?
                    Log.d("권한 체크?", "들어옴3");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
                    dialog.setTitle("권한 필요")
                            .setMessage("기능을 사용하기 위해 단말기의 '외부 저장소 읽기' 권한이 필요합니다")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create().show();

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                    Log.d("권한 체크?", "들어옴4");
                }

            } else {
                // 권한이 있을 때
                // 스플래시 액티비티 3초 후 종료
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            }

        } else {
            // 스플래시 액티비티 3초 후 종료
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permission[], @NonNull int[] grantResults) {
        if(requestCode == REQUEST_READ_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //권한 허용시
                // 스플래시 액티비티 3초 후 종료
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            } else {
                Toast.makeText(this, "권한 요청 거부", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        /*
        else if(requestCode == REQUEST_WRITE_STORAGE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //권한 허용시
            } else {
                Toast.makeText(this, "권한 요청 거부", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        */
    }
}
