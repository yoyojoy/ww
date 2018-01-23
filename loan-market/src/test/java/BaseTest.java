import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations = "classpath:spring-test.xml")
public abstract class BaseTest {
	public abstract void queryTest();
	public abstract void addTest();
	public abstract void loadTest();
	public abstract void updateTest();
	public abstract void deleteTest();
}
