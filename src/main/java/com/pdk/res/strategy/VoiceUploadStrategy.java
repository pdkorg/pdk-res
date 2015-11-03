package com.pdk.res.strategy;

import com.pdk.res.util.AmrChangeMp3;

import java.io.File;

/**
 * Created by hubo on 2015/9/18
 */
public class VoiceUploadStrategy extends AbstractUploadStrategy{

    @Override
    public String getFileName4AfterUpload(File file) {

        if(file == null) {
            return null;
        }

        if(file.exists()) {

            String origFileName = file.getName();

            String ext = origFileName.substring(origFileName.lastIndexOf("."), origFileName.length());

            if (".amr".equals(ext)) {
                return AmrChangeMp3.change(file).getName();
            }

        }

        return file.getName();
    }

}
