package cherish.backend.curation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record CurationRequestParam (

    @Pattern(regexp = "[남여]성")
    String gender,

    @NotEmpty
    int age,

    String job,

    @NotEmpty
    String purpose,

    @NotEmpty
    String relation,

    @Min(0)
    @NotEmpty
    int minPrice,

    @NotEmpty
    int maxPrice,

    Set<String> category,

    String emotion

) {}
