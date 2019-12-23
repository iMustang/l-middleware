package middleware.bean.spring;


import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * TestDemoFrom
 *
 * @author xMustang
 * @since 1.0
 */
@Data
public class TestDemoFrom {
	private String name;
	private Map<String, String> citys;
	private Date gmtStart;
	private Boolean flag;
	private Integer age;
	private List<String> types;
}
