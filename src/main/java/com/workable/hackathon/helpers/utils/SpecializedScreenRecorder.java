package com.workable.hackathon.helpers.utils;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manolisvlastos on 02/07/16.
 */
public class SpecializedScreenRecorder extends ScreenRecorder {

    private String name;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg,
                                     Rectangle captureArea, Format fileFormat, Format screenFormat,
                                     Format mouseFormat, Format audioFormat, File movieFolder,
                                     String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat,
                audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH.mm.ss");

        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "."
                + Registry.getInstance().getExtension(fileFormat));
    }
}