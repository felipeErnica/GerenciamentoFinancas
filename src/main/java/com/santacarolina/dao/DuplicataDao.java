package com.santacarolina.dao;

import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.util.ApiRequest;
import com.santacarolina.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class DuplicataDao {

    private final Logger logger = LogManager.getLogger();

    private final Service<Duplicata, DuplicataDTO> service;
    private static final String MAPPING = "/duplicatas";

    public DuplicataDao() {
        this.service = new Service<>(Duplicata.class, DuplicataDTO.class);;
    }

    public List<DuplicataDTO> findAllHomePage() throws FetchFailException {
            return service.getListRequestDTO(MAPPING);
    }

    public List<DuplicataDTO> findByPayed(boolean isPayed) throws FetchFailException {
        String query = MAPPING + "/isPayed=" + isPayed;
        return service.getListRequestDTO(query);
    }

    public void save(Duplicata d) throws SaveFailException {
        service.postRequest(MAPPING, d);
    }

    public void saveAll(List<Duplicata> list) throws SaveFailException {
        String query = MAPPING + "/batch";
        service.postListRequest(query, list);
    }

}
