package com.devdooly.skeleton.listener.zombie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheDataRequest {
    private String key;
    private String value;
}
