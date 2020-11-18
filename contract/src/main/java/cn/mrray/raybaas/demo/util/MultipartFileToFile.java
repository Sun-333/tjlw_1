package cn.mrray.raybaas.demo.util; /**
 * Created by TongGuoBo on 2019/6/19.
 */

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
  *MultipartFile转fie
 **/
public class MultipartFileToFile {
 
    /**
     * MultipartFile 转 File
     *
     * @param file 文件
     * @return File文件对象
     * @throws Exception 异常
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
 
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 获取流文件
     * @param ins 流
     * @param file 文件
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 删除本地临时文件
     * @param file 文件
     */
    public static void delteTempFile(File file) {
    if (file != null) {
        File del = new File(file.toURI());
        del.delete();
    }
}
}
