package example.android.gakuseimeshi.gurunavi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tomu on 2018/02/01.
 */

public class ImageAsyncTask  extends AsyncTask<Uri.Builder, Void, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    private String tag = null;

    public ImageAsyncTask(ImageView imageView){
        mImageViewReference = new WeakReference<ImageView>(imageView);
        tag = imageView.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(Uri.Builder... builder){

        // 受け取ったbuilderでインターネット通信する
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try{
            URL url = new URL(builder[0].toString());
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (MalformedURLException exception){

        }catch (IOException exception){

        }finally {
            if (connection != null){
                connection.disconnect();
            }
            try{
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (IOException exception){
            }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        // インターネット通信して取得した画像をImageViewにセットする
        if (tag.equals(mImageViewReference.get().getTag())) {
            if (mImageViewReference != null && result != null) {
                final ImageView imageView = mImageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(result);
                }
            }
        }
    }
}