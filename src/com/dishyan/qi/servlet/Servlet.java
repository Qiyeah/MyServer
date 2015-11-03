package com.dishyan.qi.servlet;

import com.dishyan.qi.controller.Request;
import com.dishyan.qi.controller.Response;

public class Servlet {
	public void service(Request request,Response response){
		response.println("<html><head><title>Dishyan server</title>");
		//response.println("").println(request.getParameter("name")).println("");
		response.println("</head><body>hello Dishyan server!</body></html>");
	}
}
