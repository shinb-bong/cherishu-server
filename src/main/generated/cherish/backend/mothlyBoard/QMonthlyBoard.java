package cherish.backend.mothlyBoard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMonthlyBoard is a Querydsl query type for MonthlyBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMonthlyBoard extends EntityPathBase<MonthlyBoard> {

    private static final long serialVersionUID = -1808678304L;

    public static final QMonthlyBoard monthlyBoard = new QMonthlyBoard("monthlyBoard");

    public final StringPath id = createString("id");

    public final StringPath monthlyDate = createString("monthlyDate");

    public final StringPath subtitle = createString("subtitle");

    public final StringPath title = createString("title");

    public QMonthlyBoard(String variable) {
        super(MonthlyBoard.class, forVariable(variable));
    }

    public QMonthlyBoard(Path<? extends MonthlyBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMonthlyBoard(PathMetadata metadata) {
        super(MonthlyBoard.class, metadata);
    }

}

