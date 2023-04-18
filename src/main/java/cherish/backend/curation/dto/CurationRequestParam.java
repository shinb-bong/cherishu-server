package cherish.backend.curation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CurationRequestParam (

    @Pattern(regexp = "[남여]자")
    String gender,

    @NotNull
    int age,

    String job,

    @NotEmpty
    String purpose,

    @NotEmpty
    String relation,

    @Min(0)
    @NotNull
    int minPrice,

    @NotNull
    int maxPrice,

    String category,

    String emotion

) {

    public Set<String> getJobs() {
        return Stream.of(this.job.split(",")).collect(Collectors.toSet());
    }
}
