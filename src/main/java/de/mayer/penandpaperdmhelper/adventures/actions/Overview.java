package de.mayer.penandpaperdmhelper.adventures.actions;

import com.opensymphony.xwork2.ActionSupport;
import de.mayer.penandpaperdmhelper.adventures.model.Adventure;
import de.mayer.penandpaperdmhelper.adventures.service.DataFromBackendService;
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
