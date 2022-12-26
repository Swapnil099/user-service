package com.ubi.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubi.userservice.error.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> extends BaseResponse implements Serializable{
	private Result<T> result;
}