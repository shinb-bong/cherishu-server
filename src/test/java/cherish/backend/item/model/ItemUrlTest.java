package cherish.backend.item.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ItemUrlTest {

    @Test
    void platformUrl() {
        //given
        ItemUrl url1 = ItemUrl.builder().url("https://gift.kakao.com/product/2259144").build();
        ItemUrl url2 = ItemUrl.builder().url("https://search.shopping.naver.com/search/all?query=%EB%94%94%EC%98%AC%20%EC%96%B4%EB%94%95%ED%8A%B8%20%EB%A6%BD%20%EA%B8%80%EB%A1%9C%EC%9A%B0&cat_id=&frm=NVSHATC").build();
        ItemUrl url3 = ItemUrl.builder().url("https://www.coupang.com/np/search?component=&q=%EB%94%94%EC%98%AC+%EB%A6%BD%EA%B8%80%EB%A1%9C%EC%9A%B0&channel=user").build();

        //when
        url1.setPlatform(url1.getUrl().substring(13, 18));
        url2.setPlatform(url2.getUrl().substring(24, 29));
        url3.setPlatform(url3.getUrl().substring(12, 19));

        //then
        assertEquals(url1.getPlatform(), "kakao");
        assertEquals(url2.getPlatform(), "naver");
        assertEquals(url3.getPlatform(), "coupang");
    }
}
