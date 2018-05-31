package pl.mytrip.trip.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.Model.Photo;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    @Mapping(target = "photoId", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    Photo toEntity(PhotoInfoDTO dto);

    @Mapping(target = "photoId", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    Photo updateEntity(PhotoInfoDTO dto, @MappingTarget Photo photo);

    PhotoInfoDTO toDto(Photo entity);
}
