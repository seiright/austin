package com.java3y.austin.web.service;


import com.java3y.austin.common.vo.BasicResultVO;
import com.java3y.austin.support.domain.MessageTemplate;
import com.java3y.austin.web.vo.MessageTemplateParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @description: 素材接口 TODO：目前只实现了钉钉
 * @author zhaolifeng
 * @date 2022/10/5 21:41
 * @version 1.0
 */
public interface MaterialService {


    /**
     * 钉钉素材上传
     * @param file-文件
     * @param sendAccount-发送账户
     * @param fileType-文件类型
     * @return 结果
     */
    BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType);

}
