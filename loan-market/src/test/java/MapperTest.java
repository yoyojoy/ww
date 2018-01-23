import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cs.mapper.platform.MerchantMapper;
import com.cs.model.platform.Merchant;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class MapperTest extends BaseServiceTest {

    @Autowired
    private MerchantMapper merchantMapper;


    @Ignore
    @Test
    public void add(){
        Merchant merchant = new Merchant();
        merchant.setId(UUID.randomUUID().toString().replace("-",""));
        merchant.setName("joy");
        merchant.setPassword("234234234234234");
        merchant.setPhone("15966335236");
        System.out.println(merchantMapper.insert(merchant) > 0);
    }

    @Ignore
    @Test
    public void queryList(){
        Pagination page = new Pagination(0,50);
        System.out.println(merchantMapper.selectAll());
    }
}
