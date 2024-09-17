package de.mayer.penandpaperdmhelper.adventures.service;

import de.mayer.penandpaperdmhelper.adventures.model.Adventure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class DataFromBackendService {

    @Value("backendUrl")
    private String backendUrl;


    public List<Adventure> getAllAdventures() throws IOException {
        return Collections.singletonList(new Adventure("Testadventure"));
    }


}
