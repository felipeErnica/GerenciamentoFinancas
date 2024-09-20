package com.santacarolina.util;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.DTOConversible;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service<T extends DTOConversible<T,DTO> ,DTO> {

    private final Logger logger = LogManager.getLogger();
    private Class<T> tClass;
    private Class<DTO> dtoClass;
    private ApiRequest<DTO> apiRequest = new ApiRequest<>();

    public Service(Class<T> tClass, Class<DTO> dtoClass) {
        this.tClass = tClass;
        this.dtoClass = dtoClass;
    }

    public Optional<T> getRequest(String queryString) throws FetchFailException {
        try {
            T t = tClass.getConstructor().newInstance();
            return apiRequest.getRequest(queryString, dtoClass).map(dto -> t.returnNew().fromDTO(dto) );
        } catch (URISyntaxException | IOException | InterruptedException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<T> getListRequest(String queryString) throws FetchFailException {
        try {
            T t = buildT();
            return apiRequest.getListRequest(queryString, dtoClass).stream()
                    .map(dto -> t.returnNew().fromDTO(dto))
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException | InstantiationException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DTO> getRequestDTO(String queryString) throws FetchFailException {
        try {
            return apiRequest.getRequest(queryString, dtoClass);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<DTO> getListRequestDTO(String queryString) throws FetchFailException {
        try {
            return apiRequest.getListRequest(queryString, dtoClass);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }


    public void postRequest(String queryString, T t) throws SaveFailException {
        try {
            DTO dto = t.toDTO();
            apiRequest.postRequest(queryString, dto);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void postListRequest(String queryString, List<T> list) throws SaveFailException {
        try {
            List<DTO> dtoList = list.stream()
                    .map(DTOConversible::toDTO)
                    .toList();
            apiRequest.postListRequest(queryString, dtoList);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void deleteRequest(String query) throws DeleteFailException {
        try {
            apiRequest.deleteRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DeleteFailException(e, logger);
        }
    }

    private T buildT() throws InstantiationException {
        try {
            return tClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

}
