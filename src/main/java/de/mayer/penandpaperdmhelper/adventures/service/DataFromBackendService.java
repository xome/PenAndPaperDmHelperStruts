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
                                new Picture(2, "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", "jpg", true),
                                new ChapterLink(3, new Chapter("Testchapter 2", 1d, Collections.emptyList())),
                                new EnvironmentLightning(4, new int[]{0, 0, 0}, 0.0f)
                        )
                )
        );
        log.traceExit(chapters);
        return chapters;
    }

}
