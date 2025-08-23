package com.example.geoIot.service.location;

import com.example.geoIot.entity.Location;
import com.example.geoIot.entity.dto.CoordinateDto;

import com.example.geoIot.entity.dto.LocationDto;
import com.example.geoIot.entity.dto.GeomSaveDto;
import com.example.geoIot.exception.OpenPolygonException;
import com.example.geoIot.repository.LocationRepository;
import com.example.geoIot.util.CoordinateValidator;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CoordinateValidator coordinateValidator;

    @Transactional
    @Override
    public LocationDto getLocation(Long id) {
        Optional<Location> locationOpt = locationRepository.findById(id);
        if (locationOpt.isEmpty()) {
            throw new NoSuchElementException("No such location");
        }

        return buildLocationDto(locationOpt.get());
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locationList = locationRepository.findAllByOrderByIdLocationDesc();
        if (locationList.isEmpty()) {
            throw new NoSuchElementException("No locations exist yet");
        }
        return locationList.stream()
                .map(this::buildLocationDto)
                .toList();
    }

    @Transactional
    @Override
    public LocationDto saveLocation(GeomSaveDto geomSaveDto) {

        Location location = new Location();
        location.setName(geomSaveDto.getName());

        Geometry geometry;
        WKTReader wktReader = new WKTReader();

        try {
            geometry = wktReader.read(geomSaveDto.getGeomwkt());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Polygon polygon = (Polygon) geometry ;
        polygon.setSRID(4326);
        location.setGeom(polygon);
        Location savedLocation = locationRepository.save(location);
        return buildLocationDto(savedLocation);
    }

    @Transactional
    @Override
    public String deleteLocation(Long id) {
        locationRepository.deleteById(id);
        return "Local com id " + id + " deletado com sucesso.";
    }

    private List<CoordinateDto> convertGeometryToCoordinateList(Geometry geometry) {
        List<CoordinateDto> coordinateDtoList = new ArrayList<>();
        if (geometry != null) {
            for (Coordinate coordinate : geometry.getCoordinates()) {
                coordinateDtoList.add(new CoordinateDto(coordinate.getX(), coordinate.getY()));
            }
        }
        return coordinateDtoList;
    }


    private LocationDto buildLocationDto(Location location) {
        LocationDto.LocationDtoBuilder dtoBuilder = LocationDto.builder()
                .idLocation(location.getIdLocation())
                .name(location.getName())
                .geometry(location.getGeom())                ;

        return dtoBuilder.build();
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }}
