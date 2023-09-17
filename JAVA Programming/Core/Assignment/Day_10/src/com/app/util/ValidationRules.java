package com.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.app.core.Customer;
import com.app.cust_excs.*;

public class ValidationRules {
	private static int MIN_LENGTH;
	private static int MAX_LENGTH;
	
	public static DateTimeFormatter dtf;
	public static LocalDate endYear;
	public static LocalDate startYear;

	static 
	{
		MIN_LENGTH = 5;
		MAX_LENGTH = 10;
		dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
		startYear = LocalDate.of(LocalDate.now().getYear() - 1,04,01);
		endYear = LocalDate.of(LocalDate.now().getYear(),03,31);
	}
	
	public static Customer validateApp(String email,String password,double reg_amount,String regDate,Map<String,Customer> cust) throws CustomerHandlingException
	{
		checkEmail(email);
		matches(password);
		amount(reg_amount);
		LocalDate dts = validateDate(regDate);
		//checkDuplicate(email,cust);
		
		return new Customer(email,password,reg_amount,dts);
	}
	
	public static void validateLogin(String email,String password,Map<String,Customer> c1) throws CustomerHandlingException
	{
		Customer c2 = c1.get(email);
		//Return Customer Object where key is same as email
		
		if(password.equals(c2.getPassword()))
			System.out.println("Login Sucessfull");
		else
			throw new CustomerHandlingException("Incorrect Password");
	}
	
	public static void changePassword(String email,String password,String newPassword,Map<String,Customer> c1) throws CustomerHandlingException
	{
		Customer c2 = c1.get(email);
		//Return Customer Object where key is same as email
		
		if(password.equals(c2.getPassword()))
		{
			c2.setPassword(newPassword);
			System.out.println("Password Changed Sucessfull");
		}
		else
			throw new CustomerHandlingException("Incorrect Password");
	}
	
	
	public static void checkDuplicate(String email,Map<String,Customer> cust) throws CustomerHandlingException
	{
		if(cust.containsKey(email));
			throw new CustomerHandlingException("Account Already Exist");
		//Internally calls equals.
		//Equals is override to compare email exist or not
	}
	
	public static void checkEmail(String email) throws CustomerHandlingException
	{
		if(email.length() < MIN_LENGTH && email.length() > MAX_LENGTH)
		{
			throw new CustomerHandlingException("Google cha Ceo ka tu Chota Email Asayala");
		}
		
		if(!email.contains("@"))
		{
			throw new CustomerHandlingException("@ tak re");
		}
	}

	public static void matches(String password) throws CustomerHandlingException {
		if(!password.matches("[a-z]+[@,%,#,&,*][0-9]+"))
		{	throw new CustomerHandlingException("Password Neet Tak");
		}
	}
	
	public static void amount(double amount) throws CustomerHandlingException {
		if(!(amount%500 == 0))
		{	throw new CustomerHandlingException(amount + " chi note pahila ka kadhi");
		}
	}
	
	public static LocalDate validateDate(String date) throws CustomerHandlingException {
		LocalDate dt = LocalDate.parse(date,dtf);
		if(dt.isAfter(endYear) && dt.isBefore(startYear))
		{
			throw new CustomerHandlingException("Income Tax Office Raid padnar ata");
		}
		return dt;
	}
	
}
