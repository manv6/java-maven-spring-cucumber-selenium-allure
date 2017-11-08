package com.websoul.qatools.helpers.utils;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.AudioFormatKeys.EncodingKey;
import static org.monte.media.AudioFormatKeys.FrameRateKey;
import static org.monte.media.AudioFormatKeys.KeyFrameIntervalKey;
import static org.monte.media.AudioFormatKeys.MIME_AVI;
import static org.monte.media.AudioFormatKeys.MediaType;
import static org.monte.media.AudioFormatKeys.MediaTypeKey;
import static org.monte.media.AudioFormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Created by manolisvlastos on 02/07/16.
 */
@Service
public class VideoMaker {

    private ScreenRecorder screenRecorder;

    @Value("#{properties['record_video_path']}")
    private String record_video_path;

    public void initialize(String scenarioName) throws IOException, AWTException {

        File file = new File(record_video_path);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0,0, width, height);

        //Create a instance of GraphicsConfiguration to get the Graphics configuration
        //of the Screen. This is needed for ScreenRecorder class.
        GraphicsConfiguration gc = GraphicsEnvironment//
                .getLocalGraphicsEnvironment()//
                .getDefaultScreenDevice()//
                .getDefaultConfiguration();

        //Create a instance of ScreenRecorder with the required configurations
       screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
               new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                       CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                       DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                       QualityKey, 1.0f,
                       KeyFrameIntervalKey, 15 * 60),
               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                       FrameRateKey, Rational.valueOf(30)),
               null, file, scenarioName);

        // initialize the screen recorder:
        // - default graphics configuration
        // - full screen recording
        // - record in AVI format
        // - 15 frames per second
        // - black mouse pointer
        // - no audio
        // - save capture to predefined location

//        screenRecorder = new ScreenRecorder(gc,
//                gc.getBounds(),
//                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
//                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
//                        QualityKey, 1.0f,
//                        KeyFrameIntervalKey, 15 * 60),
//                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
//                null,
//                file);
//        screenRecorder.start();





    }

    public void start() throws IOException {
        screenRecorder.start();
    }


    public void stop() throws IOException {
        screenRecorder.stop();
    }
}
