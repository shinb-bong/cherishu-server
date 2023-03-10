package cherish.backend.member.sub;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Gender {
    Male, Female, None;
}
