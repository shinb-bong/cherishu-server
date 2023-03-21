package cherish.backend.common.config.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class P6spyPrettyFormattingStrategy implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return String.format("execution time: %dms %s", elapsed, formatSql(category, sql));
    }

    private String formatSql(String category, String sql) {
        if (Category.COMMIT.getName().equals(category)
            || Category.ROLLBACK.getName().equals(category)) {
            return "\n    " + category;
        }
        if (Category.STATEMENT.getName().equals(category)) {
            if (ObjectUtils.isEmpty(sql)) {
                return sql;
            }
            if (StringUtils.startsWithIgnoreCase(sql, "create")
                || StringUtils.startsWithIgnoreCase(sql, "alter")
                || StringUtils.startsWithIgnoreCase(sql, "comment")) {
                return FormatStyle.DDL.getFormatter().format(sql);
            }
            return FormatStyle.BASIC.getFormatter().format(sql);
        }

        return sql;
    }
}
