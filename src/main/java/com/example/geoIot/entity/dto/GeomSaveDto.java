package com.example.geoIot.entity.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeomSaveDto {
    private String name;
    private String geomwkt;
}
