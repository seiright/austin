package com.java3y.austin.web.controller;


import com.java3y.austin.common.enums.ChannelType;
import com.java3y.austin.common.vo.BasicResultVO;
import com.java3y.austin.web.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * TODO 素材管理待完善-目前只实现了钉钉素材上传
 * @description:
 * @author zhaolifeng
 * @date 2022/10/5 21:47
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/material")
@Api("素材管理接口")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class MaterialController {


    @Autowired
    private MaterialService materialService;


    /**
     * 素材上传接口
     * @param file        文件内容
     * @param sendAccount 发送账号
     * @param sendChannel 发送渠道
     * @param fileType    文件类型
     * @return 结果
     */
    @PostMapping("/upload")
    @ApiOperation("/素材上传接口")
    @SuppressWarnings("rawtypes")
    public BasicResultVO uploadMaterial(@RequestParam("file") MultipartFile file, String sendAccount, Integer sendChannel, String fileType) {
        if (ChannelType.DING_DING_WORK_NOTICE.getCode().equals(sendChannel)) {
            return materialService.dingDingMaterialUpload(file, sendAccount, fileType);
        }
        return BasicResultVO.success();
    }

}
