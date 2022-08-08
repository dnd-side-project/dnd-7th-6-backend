package com.hot6.phopa.core.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class PointUtil {
    private double latitude;
    private double longitude;
}
