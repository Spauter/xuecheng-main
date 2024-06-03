package com.bloducspauter.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;

import java.io.*;

import static com.bloducspauter.media.FileEntity.TARGET_FILE_PATH;

/**
 *
 * 大文件上传测试
 * @author Bloduc Spauter
 *
 */
public class BigFileTest {
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.24.130:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    private final FileEntity fileEntity=new FileEntity();


    @Test
    public void uploadChunk() throws Exception {
        fileEntity.selectFile();
        for (int i = 0; i < fileEntity.getPartSizeNum(); i++) {
            try {
                UploadObjectArgs testbucket = UploadObjectArgs.builder()
                        .bucket("mediafiles")
                        //添加子目录
                        .object("chunk/"+fileEntity.getFileMd5()+"/"+i)
                        //本地文件mul
                        .filename(TARGET_FILE_PATH+fileEntity.getFileMd5()+"/"+i)
                        .build();
                minioClient.uploadObject(testbucket);
                System.out.println("上传分块"+i+"成功");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("上传失败，分块"+i);
            }
        }
    }

    @Test
    public void mergeTest(){
        //合并文件

    }
    @Test
    public void getFile() throws IOException {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("mediafiles").object("001/test001.bmp").build();
        try(
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream("C:\\users\\32306\\desktop\\1.bmp");
        ) {
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream1 = new FileInputStream("E:\\backup\\spauter\\Genshin Impact\\胡桃 (7).bmp");
        String source_md5 = DigestUtils.md5Hex(fileInputStream1);
        FileInputStream fileInputStream = new FileInputStream("C:\\users\\32306\\desktop\\1.bmp");
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if(source_md5.equals(local_md5)){
            System.out.println("Download success");
        }else {
            System.out.println("Download failed");
        }
    }

    @Test
    public void get() throws Exception {
        fileEntity.selectFile();
        System.out.println(fileEntity.getBinarySystemLong()+"\n" +
                fileEntity.getDecimal());
//        fileEntity.outputFile();
    }
}
