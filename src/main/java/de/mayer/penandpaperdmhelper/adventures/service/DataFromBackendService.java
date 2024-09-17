package de.mayer.penandpaperdmhelper.adventures.service;

import de.mayer.penandpaperdmhelper.adventures.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DataFromBackendService {


    private static final Logger log = LogManager.getLogger(DataFromBackendService.class);

    @Value("backendUrl")
    private String backendUrl;


    public List<Adventure> getAllAdventures() throws IOException {
        log.traceEntry();
        List<Adventure> adventures = Collections.singletonList(new Adventure("Testadventure"));
        log.traceExit(adventures);
        return adventures;
    }


    public List<Chapter> getAllChapters(String adventureName) {
        log.traceEntry(() -> "adventureName=" + adventureName);

        List<Chapter> chapters = Collections.singletonList(new Chapter("Testchapter", 20d,
                        Arrays.asList(new Text(0, "Hallo Welt"),
                                new BackgroundMusic(1, "Music", new byte[]{0x0, 0x1}),
                                new Picture(2, "asdf", "png", true),
                                new ChapterLink(3, new Chapter("Testchapter 2", 1d, Collections.emptyList())),
                                new EnvironmentLightning(4, new int[]{0, 0, 0}, 0.0f)
                        )
                )
        );
        log.traceExit(chapters);
        return chapters;
    }

}
