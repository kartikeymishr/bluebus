package com.kartikeymishr.bluebusbackend.service.impl;

import com.kartikeymishr.bluebusbackend.dto.model.bus.StopDto;
import com.kartikeymishr.bluebusbackend.dto.request.StopRequest;
import com.kartikeymishr.bluebusbackend.exception.BluebusException;
import com.kartikeymishr.bluebusbackend.exception.EntityType;
import com.kartikeymishr.bluebusbackend.exception.ExceptionType;
import com.kartikeymishr.bluebusbackend.model.bus.Stop;
import com.kartikeymishr.bluebusbackend.repository.bus.StopRepository;
import com.kartikeymishr.bluebusbackend.service.StopService;
import com.kartikeymishr.bluebusbackend.util.RandomStringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class StopServiceImpl implements StopService {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StopDto createStop(StopRequest stopRequest) {
        Stop stop = new Stop()
                .setCode(RandomStringUtil.getAlphaNumericString(8, stopRequest.getName()))
                .setName(stopRequest.getName())
                .setDetail(stopRequest.getDetails());

        stop = stopRepository.save(stop);

        return modelMapper.map(stop, StopDto.class);
    }

    @Override
    public StopDto updateStopDetails(StopDto stopDto) {
        Stop stop = stopRepository.findByCode(stopDto.getCode());
        if (stop != null) {
            stop.setDetail(stopDto.getDetail())
                    .setName(stopDto.getName());

            stopRepository.save(stop);

            return modelMapper.map(stop, StopDto.class);
        }

        throw exception(stopDto.getCode());

    }

    @Override
    public void deleteStop(String stopCode) {
        Stop stop = stopRepository.findByCode(stopCode);
        if (stop != null) {
            stopRepository.deleteByCode(stopCode);
        } else {
            throw exception(stopCode);
        }

    }

    @Override
    public StopDto getStopByCode(String stopCode) {
        Optional<Stop> stop = Optional.ofNullable(stopRepository.findByCode(stopCode));
        if (stop.isPresent()) {
            return modelMapper.map(stop.get(), StopDto.class);
        }

        throw exception(stopCode);
    }

    @Override
    public Set<StopDto> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> modelMapper.map(stop, StopDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private RuntimeException exception(String... args) {
        return BluebusException.throwException(EntityType.STOP, ExceptionType.ENTITY_NOT_FOUND, args);
    }
}
