package middleware.apachecommons.lang3;

import org.apache.commons.lang3.ArchUtils;
import org.apache.commons.lang3.arch.Processor;

/**
 * @Title: ArchUtilsDemo
 */
public class ArchUtilsDemo {
	public static void main(String[] args) {
		Processor processor = ArchUtils.getProcessor();
		// 获取电脑处理器体系结构 32 bit、64 bit
		System.out.println(processor.getArch().name());
//		返回处理器类型 x86、ia64
		System.out.println(processor.getType().name());
		System.out.println(processor.isX86());
		System.out.println(processor.is64Bit());
		System.out.println(processor.is32Bit());
		System.out.println(processor.isIA64());
	}
}
