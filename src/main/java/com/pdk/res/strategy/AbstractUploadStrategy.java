package com.pdk.res.strategy;

import java.io.File;
import java.util.Set;

/**
 * Created by hubo on 2015/9/18
 */
public class AbstractUploadStrategy implements UploadStrategy {

    private long sizeLimit;

    private Set<String> accessFileExtSet;

    private String sizeLimitErrorMsg;

    private String errorFileExtErrorMsg;

    private String scopePath;

    @Override
    public String getErrorFileExtErrorMsg() {
        return errorFileExtErrorMsg;
    }

    @Override
    public String getFileName4AfterUpload(File file) {
        return file.getName();
    }

    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Set<String> getAccessFileExtSet() {
        return accessFileExtSet;
    }

    public void setAccessFileExtSet(Set<String> accessFileExtSet) {
        this.accessFileExtSet = accessFileExtSet;
    }

    public void setSizeLimitErrorMsg(String sizeLimitErrorMsg) {
        this.sizeLimitErrorMsg = sizeLimitErrorMsg;
    }

    public void setErrorFileExtErrorMsg(String errorFileExtErrorMsg) {
        this.errorFileExtErrorMsg = errorFileExtErrorMsg;
    }

    public void setScopePath(String scopePath) {
        this.scopePath = scopePath;
    }

    @Override
    public long getSizeLimit() {
        return sizeLimit;
    }

    @Override
    public String getSizeLimitErrorMsg() {
        return sizeLimitErrorMsg;
    }

    @Override
    public String getScopePath() {
        return scopePath;
    }

    @Override
    public boolean checkFileExt(String fileExt) {
        return fileExt != null && accessFileExtSet.contains(fileExt.toLowerCase());
    }


}
