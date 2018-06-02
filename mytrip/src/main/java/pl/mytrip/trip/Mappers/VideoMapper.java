package pl.mytrip.trip.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.DTOs.VideoDTO;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Video;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VideoMapper {
    @Mapping(target = "videoId", ignore = true)
    @Mapping(target = "url", ignore = true)
    Video toEntity(VideoDTO dto);

    @Mapping(target = "videoId", ignore = true)
    @Mapping(target = "url", ignore = true)
    Video updateEntity(VideoDTO dto, @MappingTarget Video video);

    VideoDTO toDto(Video entity);
}
