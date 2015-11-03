package com.pdk.res.strategy;

import java.io.File;

/**
 * Created by hubo on 2015/9/18
 */
public interface UploadStrategy {

    long getSizeLimit();

    String getSizeLimitErrorMsg();

    String getScopePath();

    boolean checkFileExt(String fileExt);

    String getErrorFileExtErrorMsg();

    String getFileName4AfterUpload(File uploadFile);
}
