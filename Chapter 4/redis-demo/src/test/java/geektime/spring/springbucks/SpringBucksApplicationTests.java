package geektime.spring.springbucks;

import com.fasterxml.jackson.databind.ObjectMapper;
import geektime.spring.springbucks.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBucksApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, Person> personRedisTemplate;

	@Test
	public void contextLoads() throws Exception{
		String key = "serialization";
		Boolean exist = redisTemplate.hasKey(key);
		if (exist != null && exist){
			Person value = (Person) redisTemplate.opsForValue().get(key);
			log.info("{}", objectMapper.writeValueAsString(value));
		}else {
			Person person = new Person();
			person.setName("小胖");
			person.setAge(24);
			person.setGender("男");
			redisTemplate.opsForValue().set(key, person, 2, TimeUnit.MINUTES);
		}
	}

	@Test
	public void opsForValueTest(){
		stringRedisTemplate.opsForValue().set("opsForValue", "hello", 30, TimeUnit.SECONDS);
		Person person = new Person();
		person.setName("小胖");
		person.setAge(24);
		person.setGender("男");
		personRedisTemplate.opsForValue().set(String.format("person:%s", person.getName()), person, 1, TimeUnit.MINUTES);
	}

	@Test
	public void opsForHashTest() throws Exception{
		String personTable = "personTable";
		String personName = "Tom";
		HashOperations<String, String, Person> hashOperations = personRedisTemplate.opsForHash();
		if (!hashOperations.hasKey(personTable, personName)) {
			log.info("person表中不存在，开始插入");
			Person person = new Person();
			person.setName(personName);
			person.setAge(24);
			person.setGender("男");
			hashOperations.put(personTable, personName, person);
			personRedisTemplate.expire(personTable, 2, TimeUnit.MINUTES);
		}else {
			log.info("person表中存在，取出并打印");
			Person person = hashOperations.get(personTable, personName);
			if (person != null){
				log.info("{}", objectMapper.writeValueAsString(person));
			}
		}
	}

	@Test
	public void opsForListTest() throws Exception{
		String personList = "personList";
		ListOperations<String, Person> listOperations = personRedisTemplate.opsForList();
		Boolean exist = personRedisTemplate.hasKey(personList);
		if (exist != null && exist) {
			Person person = listOperations.leftPop(personList);
			log.info("person集合存在，取出第一个元素并打印");
			log.info("{}", objectMapper.writeValueAsString(person));
		}else {
			log.info("person集合不存在，开始插入");
			Person person = new Person();
			person.setName("小胖");
			person.setAge(24);
			person.setGender("男");
			listOperations.leftPush(personList, person);
			personRedisTemplate.expire(personList, 2, TimeUnit.MINUTES);
		}
	}

	@Test
	public void opsForSetTest(){
		SetOperations<String, Person> setOperations = personRedisTemplate.opsForSet();
	}

}

