package pl.marcinkowalczyk.techmarket.batchoffer.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferFromBatchJson {

    private String id;
    private String name;
    private String title;
    private String category;
    // TODO rest of fields
}
