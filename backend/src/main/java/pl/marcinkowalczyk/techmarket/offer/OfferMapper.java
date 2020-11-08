package pl.marcinkowalczyk.techmarket.offer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.marcinkowalczyk.techmarket.batchoffer.json.OfferFromBatchJson;

@Mapper
public interface OfferMapper {

    @Mapping(target = "identifier", source = "id")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "batch", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateOfferFromOfferFromBatchJson(OfferFromBatchJson offerFromBatchJson,
                                           @MappingTarget OfferEntity offerEntity);
}
