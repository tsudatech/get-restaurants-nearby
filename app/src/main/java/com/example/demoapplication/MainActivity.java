package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private GridView gridView;
    private Button button;
    public ProgressDialog m_ProgressDialog;
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ツールバーの設定
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        // アクティビティの設定
        activity = this;

        // 各種Viewの取得
        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridview);
        button = (Button) findViewById(R.id.button);

        // ボタン押下後の処理
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // プログレスダイアログの表示
                createProgressBar();

                // メインロジックの起動
                GetImagesLogic logic = new GetImagesLogic(activity);
                logic.doMainLogic();

            }
        });
    }

    // プログレスダイアログを作成する
    private void createProgressBar(){
        this.m_ProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        this.m_ProgressDialog.setMessage("検索中...");
        this.m_ProgressDialog.setCanceledOnTouchOutside(false); // プログレスダイアログ外をタッチできないようにする
        this.m_ProgressDialog.show();
    }

    // プログレスダイアログを消す
    public void dismissProgressDialog(){
        this.m_ProgressDialog.dismiss();
    }

    // textviewにテキストをセットする
    public void setText(String input){
        this.textView.setText(input);
    }

    // gridviewにadapterをセットする
    public void setAdaptor(BitmapAdapter adaptor){
        this.gridView.setAdapter(adaptor);
    }

    // gridviewにリスナーをセットする
    public void setOnItemClickListenerToGridView(AdapterView.OnItemClickListener listener){
        this.gridView.setOnItemClickListener(listener);
    }
}
