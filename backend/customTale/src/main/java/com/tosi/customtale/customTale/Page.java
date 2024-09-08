package com.ssafy.tosi.customTale;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Page {
    private int leftNo;
    private int rightNo;
    private String right;
    private boolean flipped;
}