package com.crm.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseBean<T>
{
    @JsonProperty("data")
    private T data;
    @JsonProperty("result")
    private List<T> result;
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("error")
    private ErrorResponse error;
    @JsonProperty("page")
    private PageBean page;

}
