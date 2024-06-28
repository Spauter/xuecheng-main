package com.bloducspauter.media.controller;
import com.bloducspauter.base.model.RestResponse;
import com.bloducspauter.media.entities.dto.UploadFileParamsDto;
import com.bloducspauter.media.service.MediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;

/**
 * 大文件上传控制层
 * @author Bloduc Spauter
 *
 */
@Api("大文件上传控制层")
@RestController
@RequestMapping("/media")
public class BigFilesController {

    @Resource
    private MediaService mediaService;

    @ApiOperation( "文件上传前检查文件")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkFile(
            @RequestParam("fileMd5") String fileMd5
    ) throws Exception {
        return mediaService.checkFile(fileMd5);
    }


    @ApiOperation(value = "分块文件上传前的检测")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws Exception {
        return mediaService.checkChunk(fileMd5,chunk);
    }

    @ApiOperation(value = "上传分块文件")
    @PostMapping("/upload/uploadchunk")
    public RestResponse<Boolean> uploadChunk(@RequestParam("file") MultipartFile file,
                                    @RequestParam("fileMd5") String fileMd5, @RequestParam("chunk") int chunk) throws Exception {

        //创建一个临时文件
        File tempFile = File.createTempFile("minio", ".temp");
        file.transferTo(tempFile);
        //文件路径
        String localFilePath = tempFile.getAbsolutePath();
        return mediaService.uploadChunk(fileMd5, chunk, localFilePath);
    }

    @ApiOperation(value = "合并文件")
    @PostMapping("/upload/mergechunks")
    public RestResponse<Boolean> mergeChunks(@RequestParam("fileMd5") String fileMd5,
                                    @RequestParam("fileName") String fileName,
                                    @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        Long companyId = 1232141425L;
        //文件信息对象
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFilename(fileName);
        uploadFileParamsDto.setTags("视频文件");
        uploadFileParamsDto.setFileType("001002");
        return mediaService.mergeChunk(1232141425L, fileMd5, chunkTotal, uploadFileParamsDto);

    }
}
