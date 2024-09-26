package de.mayer.penandpaperdmhelper.presentAdventure.actions;

import com.opensymphony.xwork2.ActionSupport;
import de.mayer.penandpaperdmhelper.presentAdventure.model.Chapter;
import de.mayer.penandpaperdmhelper.presentAdventure.model.Picture;
import de.mayer.penandpaperdmhelper.presentAdventure.model.RecordInChapter;
import de.mayer.penandpaperdmhelper.presentAdventure.service.RecordService;
import de.mayer.penandpaperdmhelper.presentAdventure.service.DataFromBackendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.action.CookiesAware;
import org.apache.struts2.action.ServletResponseAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class ViewAdventure extends ActionSupport implements CookiesAware, ServletResponseAware {


    private static final Logger log = LogManager.getLogger(ViewAdventure.class);
    private final DataFromBackendService service;
    private final RecordService recordService;
    private ServletResponse resp;

    private String adventureName;
    private List<Chapter> chapters;
    private String hueIp;

    public static final String KEY_HUE_IP = "hue_ip";

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

    public boolean renderPresentationButton(RecordInChapter record){
        return recordService.renderPresentationButton(record);
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

    @Override
    public void withCookies(Map<String, String> cookies) {
        hueIp = cookies.getOrDefault(KEY_HUE_IP, null);
    }

    public String getHueIp() {
        return hueIp;
    }


    @Override
    public void withServletResponse(HttpServletResponse response) {
        this.resp = response;
    }
}
