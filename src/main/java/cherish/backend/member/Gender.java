package cherish.backend.member;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Gender {
    Male, Female, None;
}
