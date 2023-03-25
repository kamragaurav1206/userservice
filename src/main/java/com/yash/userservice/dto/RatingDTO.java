package com.yash.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private String rattingId;
    private String hotelId;
    private String userId;
    private int rating;
    private String feedback;

}
