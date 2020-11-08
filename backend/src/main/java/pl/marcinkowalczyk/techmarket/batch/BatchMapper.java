package pl.marcinkowalczyk.techmarket.batch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface BatchMapper {

    @Mapping(target = "contentLength", source = "batchEntity", qualifiedByName = "contentLengthMethod")
    BatchDetailsResponseDto mapToBatchDetailsResponseDto(BatchEntity batchEntity);

    @Named("contentLengthMethod")
    default int contentLength(BatchEntity batchEntity) {
        return batchEntity.getContent().length();
    }
}
