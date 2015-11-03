package com.pdk.res.util;

import it.sauronsoftware.jave.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class AmrChangeMp3 {

    private static Logger log = LoggerFactory.getLogger(AmrChangeMp3.class);

	public static File change(File source) {

        if(source == null || !source.exists()) {
            throw new IllegalArgumentException("file is null");
        }

        String origFileName = source.getName();

        String newName = origFileName.substring(0, origFileName.lastIndexOf(".")) + ".mp3";

        File target = new File(source.getParent(), newName);

        AudioAttributes audio = new AudioAttributes();
        Encoder encoder;
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            encoder = new Encoder(new FFMPEGLocator() {
                @Override
                protected String getFFMPEGExecutablePath() {
                    return "C:/ffmpeg/bin/ffmpeg";
                }
            });
        }else{
            encoder = new Encoder(new FFMPEGLocator() {
                @Override
                protected String getFFMPEGExecutablePath() {
                    return "ffmpeg";
                }
            });
        }

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, attrs);
        } catch (Exception e) {
            log.error("amr 转换 mp3 异常", e);
        }

        return target;
    }
}
