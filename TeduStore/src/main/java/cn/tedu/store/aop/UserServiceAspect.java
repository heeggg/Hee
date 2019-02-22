package cn.tedu.store.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserServiceAspect {
	//@Before("bean(*Service)")
	//@Before("execution(* cn.tedu.store.service.*Service.login(..))")
	@Before("within(cn.tedu.store.service.impl.*ServiceImpl())")
	public void test1() {
		System.out.println("test1()");
	}
	@AfterReturning("bean(*Service)")
	public void test2() {
		System.out.println("test2()");
	}
	@AfterThrowing("bean(*Service)")
	public void test3() {
		System.out.println("test3()");
	}
	@After("bean(*Service)")
	public void test4() {
		System.out.println("test4()");
	}
}
