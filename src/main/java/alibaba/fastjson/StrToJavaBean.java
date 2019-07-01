package alibaba.fastjson;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * StrToJavaBean
 * description:JSON字符串转化为JavaBean
 */
public class StrToJavaBean {
	public static void main(String[] args) {
		//json字符串-简单对象型
		String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
		Student student = JSON.parseObject(JSON_OBJ_STR, Student.class);
		System.out.println(student.getStudentAge());

		//json字符串-数组类型
		String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
		List<Student> students = JSON.parseArray(JSON_ARRAY_STR, Student.class);
		for (Student stu : students) {
			System.out.println(stu.getStudentAge());
		}

		//复杂格式json字符串
		String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";
		Teacher teacher = JSON.parseObject(COMPLEX_JSON_STR, Teacher.class);
		System.out.println(teacher.getStudents().get(0).getStudentName());
	}
}
