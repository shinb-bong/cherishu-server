package cherish.backend.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {
    public static final String EMPTY = "";
    public static final String LOCALHOST = "http://localhost:8080";
    public static final String CLIENT_ORIGIN = "https://rococo-paprenjak-9b8d81.netlify.app";

    // Refresh token 레디스에 저장하기 위한 prefix
    public static final String REDIS_REFRESH_TOKEN_PREFIX = "refresh_";
}
