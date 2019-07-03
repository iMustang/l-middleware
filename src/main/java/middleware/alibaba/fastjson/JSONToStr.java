package middleware.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * JSONToStr
 * description:
 */
public class JSONToStr {
	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentAge(14);
		student.setStudentName("mustang");
		String str = JSON.toJSONString(student);
		System.out.println(str);
		JSONObject jb = JSON.parseObject(str);
		System.out.println(jb.getString("studentName"));
	}
}
