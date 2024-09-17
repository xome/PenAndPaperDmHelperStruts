package de.mayer.penandpaperdmhelper.adventures.actions;

import com.opensymphony.xwork2.ActionSupport;
import de.mayer.penandpaperdmhelper.adventures.model.Chapter;
import de.mayer.penandpaperdmhelper.adventures.model.Picture;
import de.mayer.penandpaperdmhelper.adventures.model.RecordInChapter;
import de.mayer.penandpaperdmhelper.adventures.service.RecordService;
import de.mayer.penandpaperdmhelper.adventures.service.DataFromBackendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewAdventure extends ActionSupport {


    private static final Logger log = LogManager.getLogger(ViewAdventure.class);
    private final DataFromBackendService service;
    private final RecordService recordService;

    private String adventureName;
    private List<Chapter> chapters;

    public ViewAdventure(DataFromBackendService dataRetrieval, RecordService recordService) {
        this.service = dataRetrieval;
        this.recordService = recordService;
    }

    @Override
    public String execute() throws Exception {
        chapters = service.getAllChapters(adventureName);
        return SUCCESS;
    }

    public boolean isText(RecordInChapter record){
        return recordService.isText(record);
    }

    public boolean isPicture(RecordInChapter record) {
        return recordService.isPicture(record);
    }

    public boolean isBackgroundMusic(RecordInChapter record) {
        return recordService.isBackgroundMusic(record);
    }

    public boolean isEnvironmentLightning(RecordInChapter record) {
        return recordService.isEnvironmentLightning(record);
    }

    public boolean isChapterLink(RecordInChapter record) {
        return recordService.isChapterLink(record);
    }

    public String getText(RecordInChapter record){
        return recordService.getText(record);
    }

    public String getPictureDataForImgSrc(RecordInChapter record) {
        log.traceEntry(() -> "record=" + record);
        if (!isPicture(record)) {return null;}
        Picture picture = (Picture) record;
        String imgSrcValue = "data:image/" + picture.getFileFormat() + ";base64," + picture.getBase64();
        log.traceExit(imgSrcValue);
        return imgSrcValue;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public String getAdventureName() {
        return adventureName;
    }
}
