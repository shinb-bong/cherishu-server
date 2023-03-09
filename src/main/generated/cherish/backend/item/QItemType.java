package cherish.backend.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItemType is a Querydsl query type for ItemType
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QItemType extends BeanPath<ItemType> {

    private static final long serialVersionUID = -1286269268L;

    public static final QItemType itemType1 = new QItemType("itemType1");

    public final StringPath itemType = createString("itemType");

    public QItemType(String variable) {
        super(ItemType.class, forVariable(variable));
    }

    public QItemType(Path<ItemType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItemType(PathMetadata metadata) {
        super(ItemType.class, metadata);
    }

}

