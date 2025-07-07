package com.tencent.demo.tceffectplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.tcmediax.tceffectplayer.api.TCEffectAnimView;
import com.tencent.tcmediax.tceffectplayer.api.data.TCEffectText;
import com.tencent.tcmediax.tceffectplayer.api.mix.IFetchResource;
import com.tencent.tcmediax.tceffectplayer.api.mix.IFetchResourceImgResult;
import com.tencent.tcmediax.tceffectplayer.api.mix.IFetchResourceTxtResult;
import com.tencent.tcmediax.tceffectplayer.api.mix.Resource;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String FILE_NAME_TEP = "anim.mp4";
    private static final String[] FILE_NAME_TEPGS = {
            "avatar1.tcmp4", "avatar2.tcmp4", "avatar3.tcmp4",
    };

    private String copyResDir;

    private FrameLayout frameContainerView;
    private View btnPlayFirst;
    private View btnPlaySecond;
    private TCEffectAnimView animViewTEP;
    private FrameLayout animViewGroupTEPG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TCMediaXLicenseService.getInstance().init();
        copyResourceIfNeed();

        frameContainerView = findViewById(R.id.frame_container_view);
        BackgroundView backgroundView = new BackgroundView(this);
        backgroundView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameContainerView.addView(backgroundView);
        btnPlayFirst = findViewById(R.id.btn_play_first);
        btnPlayFirst.setOnClickListener(this);
        btnPlaySecond = findViewById(R.id.btn_play_second);
        btnPlaySecond.setOnClickListener(this);
    }

    private void addTEPAnimAndPlay() {
        if (animViewTEP == null) {
            String playTepUrl = new File(copyResDir, FILE_NAME_TEP).getAbsolutePath();

            animViewTEP = new TCEffectAnimView(this);
            animViewTEP.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            animViewTEP.setOnClickListener(v -> {
                if (animViewTEP.isPlaying()) {
                    animViewTEP.pause();
                } else {
                    animViewTEP.resume();
                }
            });
            animViewTEP.setFetchResource(new IFetchResource() {
                @Override
                public void fetchImage(Resource resource, IFetchResourceImgResult result) {
                    String tag = resource.tag;
                    if (!TextUtils.isEmpty(tag)) {
                        int headerId = R.drawable.head1;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inScaled = false;
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), headerId, options);
                        result.fetch(bitmap);
                    } else {
                        result.fetch(null);
                    }
                }

                @Override
                public void fetchText(Resource resource, IFetchResourceTxtResult result) {
                    String tag = resource.tag;
                    if (!TextUtils.isEmpty(tag)) {
                        TCEffectText tcEffectText = new TCEffectText();
                        tcEffectText.text = "TCEffect Test";
//                        tcEffectText.color = Color.RED
//                        tcEffectText.fontStyle = "bold";
                        result.loadTextForPlayer(tcEffectText);
                    } else {
                        result.fetch("");
                    }
                }

                @Override
                public void releaseResource(List<Resource> resources) {
                    for (Resource resource : resources) {
                        if (resource.bitmap != null) resource.bitmap.recycle();
                    }
                }
            });
            frameContainerView.addView(animViewTEP);
            animViewTEP.setLoop(true);
            int result = animViewTEP.startPlay(playTepUrl);
            if (result != 0) {
                Toast.makeText(this, "Play Failed: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addTEPGAnimAndPlay() {
        if (animViewGroupTEPG == null) {
            int row = 5;
            int column = 4;

            Display display = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int screenWidth = point.x;
            int itemWidth = screenWidth / (column + 1);
            int itemHeight = screenWidth / (column + 1);

            animViewGroupTEPG = new FrameLayout(this);
            animViewGroupTEPG.setLayoutParams(new FrameLayout.LayoutParams(itemWidth * column, itemWidth * row, Gravity.CENTER));
            frameContainerView.addView(animViewGroupTEPG);

            for (int i = 0; i < row * column; i++) {
                TCEffectAnimView pagImageView = new TCEffectAnimView(this);
                String playTepUrl = new File(copyResDir, FILE_NAME_TEPGS[i % FILE_NAME_TEPGS.length]).getAbsolutePath();

                animViewGroupTEPG.addView(pagImageView);
                ViewGroup.LayoutParams params = pagImageView.getLayoutParams();
                params.width = itemWidth;
                params.height = itemHeight;
                pagImageView.setLayoutParams(params);

                pagImageView.setX((i % column) * itemHeight);
                pagImageView.setY((i / column) * itemWidth);

                pagImageView.setLoop(true);
                pagImageView.startPlay(playTepUrl);
            }
        }
    }

    private void releaseTEPGAnimGroup() {
        if (animViewGroupTEPG != null) {
            for (int i = 0; i < animViewGroupTEPG.getChildCount(); i++) {
                View child = animViewGroupTEPG.getChildAt(i);
                if (child instanceof TCEffectAnimView) {
                    ((TCEffectAnimView) child).stopPlay(true);
                }
            }
            animViewGroupTEPG.removeAllViews();
        }
    }

    private void activatedView(int viewId) {
        if (viewId == R.id.btn_play_first) {
            btnPlayFirst.setActivated(true);
            btnPlaySecond.setActivated(false);
        } else if (viewId == R.id.btn_play_second) {
            btnPlayFirst.setActivated(false);
            btnPlaySecond.setActivated(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_second) {
            if (animViewTEP != null) {
                animViewTEP.stopPlay(true);
                frameContainerView.removeView(animViewTEP);
                animViewTEP = null;
            }
            if (animViewGroupTEPG == null) {
                addTEPGAnimAndPlay();
            }
            activatedView(R.id.btn_play_second);
        } else {
            if (animViewGroupTEPG != null) {
                releaseTEPGAnimGroup();
                frameContainerView.removeView(animViewGroupTEPG);
                animViewGroupTEPG = null;
            }
            if (animViewTEP == null) {
                addTEPAnimAndPlay();
            }
            activatedView(R.id.btn_play_first);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animViewTEP != null) {
            animViewTEP.stopPlay(true);
        }
        releaseTEPGAnimGroup();
    }

    private void copyResourceIfNeed() {
        copyResDir = getExternalFilesDir("/tceffectplayer/").getAbsolutePath();
        File copyResDirFile = new File(copyResDir);
        if (!copyResDirFile.exists()) {
            copyResDirFile.mkdirs();
        }
        new Thread(() -> {
            File destFile = new File(copyResDirFile, FILE_NAME_TEP);
            if (!destFile.exists()) {
                Utils.copyAssetFile(getApplicationContext(), FILE_NAME_TEP, destFile.toString());
            }

            for (String filename : FILE_NAME_TEPGS) {
                File destFileItem = new File(copyResDirFile, filename);
                if (!destFileItem.exists()) {
                    Utils.copyAssetFile(getApplicationContext(), filename, destFileItem.toString());
                }
            }
        }).start();
    }
}
