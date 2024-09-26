package de.mayer.penandpaperdmhelper.presentAdventure.actions;

import com.opensymphony.xwork2.ActionSupport;
import de.mayer.penandpaperdmhelper.presentAdventure.model.Adventure;
import de.mayer.penandpaperdmhelper.presentAdventure.service.DataFromBackendService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Overview extends ActionSupport {


    private final DataFromBackendService service;
    private List<Adventure> adventures;

    public Overview(DataFromBackendService service) {
        this.service = service;
    }


    @Override
    public String execute() throws Exception {
        this.adventures = service.getAllAdventures();
        return SUCCESS;
    }

    public List<Adventure> getAdventures() {
        return adventures;
    }
}
