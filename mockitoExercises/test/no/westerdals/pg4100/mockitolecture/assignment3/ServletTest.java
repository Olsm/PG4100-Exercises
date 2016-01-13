package no.westerdals.pg4100.mockitolecture.assignment3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class ServletTest {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher view;
	private ServletHelper helper;
	
	@Before
	public void setup () {
		request = mock (HttpServletRequest.class);
		response = mock (HttpServletResponse.class);
		view = mock (RequestDispatcher.class);
		helper = mock (ServletHelper.class);
	}
	
	@Test
	/**
	 * Mocking.
	 * Test:
	 * <ul>
	 * <li> the value of the request parameter "input" is used when calling model (helper)
	 * </li>
	 * <li> the servlet forwards the request
	 * </li> 
	 * </ul>
	 * @throws Exception
	 */
	public void servletUsesModelAndForwardsRequest() throws Exception{
		// ASSIGN
		when(request.getParameter("input")).thenReturn("test");
		when(request.getRequestDispatcher(anyString())).thenReturn(view);
		ExampleServlet servlet = new ExampleServlet();
		servlet.setHelper(helper);
		// ACT
		String param = request.getParameter("input");
		servlet.doPost(request, response);
		// ASSERT/VERIFY
		assertEquals("test", param);
		verify(helper).provideAnswer("test");
		verify(view).forward(request, response);
	}

}
