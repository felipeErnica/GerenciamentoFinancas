package com.santacarolina.util;

import com.santacarolina.exceptions.DeleteFailException;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.interfaces.FromDTO;
import com.santacarolina.interfaces.ToDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service<T extends ToDTO<DTO>, DTO extends FromDTO<T>> {

    private static final Logger logger = LogManager.getLogger();

    private final ApiRequest<DTO> apiRequest;

    public Service(Class<DTO> dtoClass) {
        this.apiRequest = new ApiRequest<>(dtoClass); 
    }

    public Optional<T> getRequest(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query).map(FromDTO::fromDTO);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<T> getListRequest(String query) throws FetchFailException {
        try {
            return apiRequest.getListRequest(query).stream()
                    .map(FromDTO::fromDTO)
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public Optional<DTO> getRequestDTO(String query) throws FetchFailException {
        try {
            return apiRequest.getRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }

    public List<DTO> getListRequestDTO(String query) throws FetchFailException {
        try {
            return apiRequest.getListRequest(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new FetchFailException(e, logger);
        }
    }


    public void postRequest(String query, T t) throws SaveFailException {
        try {
            DTO dto = t.toDTO();
            apiRequest.postRequest(query, dto);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public T postRequestWithResponse(String query, T t) throws SaveFailException {
        try {
            DTO dto = t.toDTO();
            DTO saved = apiRequest.postRequestWithResponse(query, dto);
            return saved.fromDTO();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new SaveFailException(e, logger);
        }
    }

    public void postListRequest(String query, List<T> list) throws SaveFailException {
        try {
            List<DTO> dtoList = list.stream()
                    .map(ToDTO::toDTO)
                    .toList();
            apiRequest.postListRequest(query, dtoList);
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

}
