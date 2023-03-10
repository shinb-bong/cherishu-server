package cherish.backend.member.sub;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGender is a Querydsl query type for Gender
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGender extends EnumPath<Gender> {

    private static final long serialVersionUID = 269718855L;

    public static final QGender gender = new QGender("gender");

    public QGender(String variable) {
        super(Gender.class, forVariable(variable));
    }

    public QGender(Path<Gender> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGender(PathMetadata metadata) {
        super(Gender.class, metadata);
    }

}

