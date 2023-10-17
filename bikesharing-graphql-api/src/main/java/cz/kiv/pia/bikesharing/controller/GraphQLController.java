package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.domain.Bike;
import cz.kiv.pia.bikesharing.graphql.BikeDTO;
import cz.kiv.pia.bikesharing.graphql.LocationDTO;
import cz.kiv.pia.bikesharing.graphql.StandDTO;
import cz.kiv.pia.bikesharing.graphql.UserDTO;
import cz.kiv.pia.bikesharing.service.BikeService;
import cz.kiv.pia.bikesharing.service.StandService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class GraphQLController {

    private final StandService standService;
    private final BikeService bikeService;

    public GraphQLController(StandService standService, BikeService bikeService) {
        this.standService = standService;
        this.bikeService = bikeService;
    }

    @QueryMapping
    public String greeting(@Argument String name) {
        return "Hello, " + name + "!";
    }

    @QueryMapping
    public List<StandDTO> retrieveStands() {
        var result = standService.getAll();

        List<StandDTO> dtos = new ArrayList<>();
        for (var stand : result) {
            var locationDTO = LocationDTO.builder()
                    .withLatitude(stand.getLocation().getLatitude())
                    .withLongitude(stand.getLocation().getLongitude())
                    .build();

            var standDTO = StandDTO.builder()
                    .withId(stand.getId())
                    .withName(stand.getName())
                    .withLocation(locationDTO)
                    .build();

            dtos.add(standDTO);
        }

        return dtos;
    }

    @BatchMapping(typeName = "StandDTO", value = "bikes")
    public List<List<BikeDTO>> retrieveStandBikes(@Argument List<StandDTO> standDTOs) {
        List<List<BikeDTO>> lists = new ArrayList<>();

        for (var standDTO : standDTOs) {
            // BEWARE: This causes N+1 queries to database, all bikes should better be loaded in bulk
            //var result = bikeService.getRideableBikes(standDTO.getId());
            List<Bike> result = List.of();

            List<BikeDTO> bikeDTOs = new ArrayList<>();
            for (var bike : result) {
                var locationDTO = LocationDTO.builder()
                        .withLatitude(bike.getLocation().getLatitude())
                        .withLongitude(bike.getLocation().getLongitude())
                        .build();

                var bikeDTO = BikeDTO.builder()
                        .withId(bike.getId())
                        .withLocation(locationDTO)
                        .build();

                bikeDTOs.add(bikeDTO);
            }

            lists.add(bikeDTOs);
        }

        return lists;
    }

    @MutationMapping
    public UserDTO createUser(@Argument String username) {
        return UserDTO.builder()
                .withId(UUID.randomUUID())
                .withUsername(username)
                .withRegistrationDate(OffsetDateTime.now())
                .build();
    }
}
