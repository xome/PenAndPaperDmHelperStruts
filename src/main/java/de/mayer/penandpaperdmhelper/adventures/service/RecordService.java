package de.mayer.penandpaperdmhelper.adventures.service;

import de.mayer.penandpaperdmhelper.adventures.model.*;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    public boolean isText(RecordInChapter record) {
        return record instanceof Text;
    }

    public String getText(RecordInChapter record) {
        return record instanceof Text ? ((Text) record).getText() : null;
    }

    public String getPictureData(RecordInChapter record) {
        return record instanceof Picture ? ((Picture) record).getBase64() : null;
    }

    public boolean isPicture(RecordInChapter record) {
        return record instanceof Picture;
    }


    public boolean isBackgroundMusic(RecordInChapter record) {
        return record instanceof BackgroundMusic;
    }

    public boolean isEnvironmentLightning(RecordInChapter record) {
        return record instanceof EnvironmentLightning;
    }

    public boolean isChapterLink(RecordInChapter record) {
        return record instanceof ChapterLink;
    }
}
