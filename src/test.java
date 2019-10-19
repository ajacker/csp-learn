import okhttp3.*;

import java.io.File;
import java.io.IOException;

/**
 * java通过模拟post方式提交表单实现图片上传功能实例
 * 其他文件类型可以传入 contentType 实现
 *
 * @author zdz8207
 * @version 1.0
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            System.out.printf("%2d%%", i);
            Thread.sleep(1000);
            System.out.print("\b\b\b");
        }
    }
}

class UpLoadTest {
    public static void uploadTest() {
        File file = new File("F:\\Desktop\\xx.jpg");
        MediaType mediaType = MediaType.parse("application/octet-stream");
        String filename = file.getName();
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", filename, RequestBody.create(mediaType, file))
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

}
