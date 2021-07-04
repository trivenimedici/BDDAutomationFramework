package com.bdd.impl;
import static com.bdd.utils.DataBaseOps.*;


import java.util.LinkedHashMap;
import java.util.LinkedList;


public class EmployeeImplementations {
	public static LinkedHashMap<String,LinkedList<String>> res;
public static LinkedHashMap getEmployeeID(String employeeName) {
	String query="select * from emp.employee where name='"+employeeName+"'";
	res= getResultSet(query);
	System.out.println(res);
	return res;
}


}
