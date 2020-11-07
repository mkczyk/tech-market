package pl.marcinkowalczyk.techmarket.batch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface BatchMapper {

    @Mapping(source = "batchEntity", target = "contentLength", qualifiedByName = "contentLength")
    BatchDetailsResponseDto mapToBatchDetailsResponseDto(BatchEntity batchEntity);

    @Named("contentLength")
    default int contentLength(BatchEntity batchEntity) {
        return batchEntity.getContent().length();
    }
}
