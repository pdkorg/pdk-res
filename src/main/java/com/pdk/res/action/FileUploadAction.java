package com.pdk.res.action;

import com.pdk.res.strategy.UploadStrategy;
import com.pdk.res.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubo on 2015/9/18
 */
@Controller
@RequestMapping("/file")
public class FileUploadAction {

    private static Logger log = LoggerFactory.getLogger(FileUploadAction.class);

    @Value("#{config.basePath}")
    private String baseUploadPhysicalPath;

    @Value("#{config.baseUrl}")
    private String baseUrl;

    @Value("#{config.token}")
    private String token;

    @Autowired
    @Qualifier("uploadStrategyMap")
    private HashMap<String, UploadStrategy> uploadStrategyMap;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file, String token, String uploadType, String module, String path) {

        Map<String, Object> result = new HashMap<>();

        if (token == null || !this.token.equals(token)) {
            result.put("result", false);
            result.put("errorMsg", "token错误，不能上传");
            return result;
        }

        if (!uploadStrategyMap.containsKey(uploadType)) {
            result.put("result", false);
            result.put("errorMsg", "上传文件类型错误，目前支持image、voice类型");
            return result;
        }

        UploadStrategy strategy = uploadStrategyMap.get(uploadType);

        if (file.getSize() > strategy.getSizeLimit()) {
            result.put("result", false);
            result.put("errorMsg", strategy.getSizeLimitErrorMsg());
            return result;
        }

        String origFileName = file.getOriginalFilename();

        String ext = origFileName.substring(origFileName.lastIndexOf("."), origFileName.length()).toLowerCase();

        if(!strategy.checkFileExt(ext)) {
            result.put("result", false);
            result.put("errorMsg", strategy.getErrorFileExtErrorMsg());
            return result;
        }

        String customPath = "/" + strategy.getScopePath() + "/" + module + path + "/";

        String parentPath = baseUploadPhysicalPath + customPath;

        File parentFile = new File(parentPath);

        String newFileName = UUIDGenerator.generateUUID() + ext;

        File targetFile = new File(parentPath, newFileName);

        boolean f = true;

        if(!parentFile.exists()) {
            f = parentFile.mkdirs();
        }

        //保存
        try {
            if(f) {
                file.transferTo(targetFile);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        result.put("result", true);
        result.put("fileUri",  baseUrl + customPath + strategy.getFileName4AfterUpload(targetFile));

        return result;
    }
}
