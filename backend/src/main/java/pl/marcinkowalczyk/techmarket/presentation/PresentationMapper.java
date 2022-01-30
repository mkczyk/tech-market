package pl.marcinkowalczyk.techmarket.presentation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.marcinkowalczyk.techmarket.presentation.dto.GraphLinkDto;
import pl.marcinkowalczyk.techmarket.technologylink.TechnologyLinkEntity;

@Mapper
public interface PresentationMapper {

    @Mapping(target = "value", source = "weight")
    GraphLinkDto mapToGraphLinkDto(TechnologyLinkEntity technologyLinkEntity);
}
