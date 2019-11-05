package geektime.spring.springbucks.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyc1856
 * @date 2019/11/4
 */
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 6362137966053886777L;

    private String name;
    private Integer age;
    private String gender;
}
