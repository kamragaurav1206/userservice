package com.yash.userservice.services;

import com.yash.userservice.dao.UserRepository;
import com.yash.userservice.dto.HotelDTO;
import com.yash.userservice.dto.RatingDTO;
import com.yash.userservice.dto.UserDTO;
import com.yash.userservice.entities.User;
import com.yash.userservice.exception.ResourceNotFoundException;
import com.yash.userservice.external.service.feign.HotelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelClient hotelClient;

    @Override
    public User addUser(UserDTO userDTO) {
        String userID = UUID.randomUUID().toString();
        User user = User.build(userID,userDTO.getUserName(),userDTO.getEmail(),userDTO.getAbout(),new ArrayList<>());
        user.setUserId(userID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.stream().forEach((user)->{
            RatingDTO[] ratingDTO = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), RatingDTO[].class);
            for (RatingDTO dto : ratingDTO) {
                System.out.println("userId :"+user.getUserId()+" :: hotelId ::"+dto.getHotelId()+" :: ratingId "+dto.getRattingId());
            }
            List<RatingDTO> ratings = Arrays.asList(ratingDTO).stream().filter(r->(r.getHotelId()!=null && r.getHotelId()!="")).map((rating)->{
                //ResponseEntity<HotelDTO> entity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), HotelDTO.class);
                //System.out.println("Sampleeee:"+entity.getBody().getHotelId());

                //rating.setHotel(entity.getBody());
                rating.setHotel(hotelClient.getHotel(rating.getHotelId()));
                return rating;
            }).collect(Collectors.toList());

            user.setRatings(ratings);
        });
        return users;
    }

    @Override
    public User getUserByID(String userId) {

        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found :"+userId));
        RatingDTO[] ratingDTO = restTemplate.getForObject("http://localhost:8082/ratings/users/"+userId, RatingDTO[].class);
        List<RatingDTO> ratings =  Arrays.asList(ratingDTO);
        user.setRatings(ratings.stream().filter(r->r.getHotelId()!=null).map((rating)->{
            ResponseEntity<HotelDTO> entity = restTemplate.getForEntity("http://localhost:8083/"+rating.getHotelId(), HotelDTO.class);
            rating.setHotel(entity.getBody());
            return rating;
        }).collect(Collectors.toList()));
        return user;
    }

    @Override
    public void deleteUserByID(String userId) {
        userRepository.deleteById(userId);
    }
}
