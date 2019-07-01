package alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * StrToJSONDemo
 * description: JSON格式字符串转换为JSON对象
 */
public class StrToJSONDemo {
	public static void main(String[] args) {
		//json字符串-简单对象型
		String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
		JSONObject jb = JSON.parseObject(JSON_OBJ_STR);
		System.out.println(jb.getString("studentName"));

		//json字符串-数组类型
		String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
		JSONArray jr = JSON.parseArray(JSON_ARRAY_STR);
		for (int i = 0; i < jr.size(); i++) {
			JSONObject jb1 = jr.getJSONObject(i);
			System.out.println(jb1.getString("studentName"));
		}

		//复杂格式json字符串
		String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";
		JSONObject jb2 = JSON.parseObject(COMPLEX_JSON_STR);
		String teacherName = jb2.getString("teacherName");
		System.out.println(teacherName);
		String teacherAge = jb2.getString("teacherAge");
		System.out.println(teacherAge);
		JSONObject course = jb2.getJSONObject("course");
		String courseName = course.getString("courseName");
		System.out.println(courseName);
	}
}
