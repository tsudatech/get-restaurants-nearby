package com.example.demoapplication;

public class ImageGetTaskListenerImpl implements ImageGetTask.ImageGetTaskListener {

    private MainActivity activity;
    private HotPepperGourmetListHolder hotPepperGourmetListHolder;
    private ImagesHolder imagesHolder;

    public ImageGetTaskListenerImpl(MainActivity activity,
                                    HotPepperGourmetListHolder hotPepperGourmetListHolder,
                                    ImagesHolder imagesHolder){
        this.activity = activity;
        this.hotPepperGourmetListHolder = hotPepperGourmetListHolder;
        this.imagesHolder = imagesHolder;
    }

    // 画像取得後のコールバック
    @Override
    public void onImageGetTaskCallBack(){
        BitmapAdapter adapter = new BitmapAdapter(
                activity.getApplicationContext(), R.layout.grid_items,
                imagesHolder.getImages()
        );
        activity.setAdaptor(adapter);

        // 画像クリック時のリスナーをセット
        OnItemClickListenerImpl onItemClickListener = new OnItemClickListenerImpl(hotPepperGourmetListHolder.getHotPepperGourmetList(), activity);
        activity.setOnItemClickListenerToGridView(onItemClickListener);

        // 検索結果が0以上の場合はテキストを消去する
        if(hotPepperGourmetListHolder.getHotPepperGourmetListSize() > 0)
            activity.setText("");

        // プログレスバーの消去
        activity.dismissProgressDialog();

    }
}

