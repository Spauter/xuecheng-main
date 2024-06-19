package com.bloducspauter.media;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bloducspauter.media.FileEntity.TARGET_FILE_PATH;

/**
 * 大文件上传测试
 *
 * @author Bloduc Spauter
 */
public class BigFileTest {
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.24.130:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    private final FileEntity fileEntity = new FileEntity();


    @Test
    public void uploadChunk() throws Exception {
        for (int i = 0; i < fileEntity.getPartSizeNum(); i++) {
            try {
                UploadObjectArgs testbucket = UploadObjectArgs.builder()
                        .bucket("mediafiles")
                        //添加子目录
                        .object("chunk/" + fileEntity.getFileMd5() + "/" + i)
                        //本地文件mul
                        .filename(TARGET_FILE_PATH + fileEntity.getFileMd5() + "/" + i)
                        .build();
                minioClient.uploadObject(testbucket);
                System.out.println("上传分块" + i + "成功");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("上传失败，分块" + i);
            }
        }
    }

    @Test
    public void mergeTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //添加所有分块文件
        List<ComposeSource> sources=  Stream.iterate(0,i->++i)
                .limit(fileEntity.getPartSizeNum())
                .map(i->
                        ComposeSource.builder()
                                .bucket("mediafiles")
                                .object("chunk/" + fileEntity.getFileMd5() + "/" + i)
                                .build()
                )
                .collect(Collectors.toList());

        //分块文件信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket("mediafiles")
                .object(fileEntity.getFileMd5()+"/"+fileEntity.getFileName())
                .sources(sources)
                .build();
        minioClient.composeObject(composeObjectArgs);


    }


    @Test
    public void get() throws Exception {
        fileEntity.selectFile();
        uploadChunk();
        mergeTest();
    }
}
