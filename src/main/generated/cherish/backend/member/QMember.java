package cherish.backend.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -9696846L;

    public static final QMember member = new QMember("member1");

    public final DateTimePath<java.time.LocalDateTime> brith = createDateTime("brith", java.time.LocalDateTime.class);

    public final StringPath created_date = createString("created_date");

    public final StringPath email = createString("email");

    // custom
    public final cherish.backend.member.sub.QGender gender = new cherish.backend.member.sub.QGender(forProperty("gender"));

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath info_check = createBoolean("info_check");

    public final StringPath job = createString("job");

    public final StringPath modified_date = createString("modified_date");

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final EnumPath<cherish.backend.member.sub.Role> role = createEnum("role", cherish.backend.member.sub.Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

