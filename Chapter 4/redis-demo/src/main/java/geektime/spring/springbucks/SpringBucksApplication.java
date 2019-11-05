package geektime.spring.springbucks;

import com.fasterxml.jackson.databind.ObjectMapper;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.Person;
import geektime.spring.springbucks.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class SpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Coffee> coffeeRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Coffee> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Coffee.class));
		return template;
	}

	@Bean
	public RedisTemplate<String, Person> personRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Person> personRedisTemplate = new RedisTemplate<>();
		personRedisTemplate.setConnectionFactory(redisConnectionFactory);
		personRedisTemplate.setKeySerializer(new StringRedisSerializer());
		personRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Person.class));
		personRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
		personRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Person.class));
		return personRedisTemplate;
	}

	@Bean
	public LettuceClientConfigurationBuilderCustomizer customizer() {
		return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("服务启动成功>>>>>>>>>");
//		Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
//		log.info("Coffee {}", c);
//
//		for (int i = 0; i < 5; i++) {
//			c = coffeeService.findOneCoffee("mocha");
//		}
//
//		log.info("Value from Redis: {}", c);
	}
}

