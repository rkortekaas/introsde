package introsde.rest.servlet;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = "/salutation/en")
public class HelloWorld extends HttpServlet {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 3376725678883362348L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		Integer indexParam = request.getParameter();
//		Integer index = indexParam == null ? null : new Integer(indexParam);
		
//		if ("format=json".equals(indexParam)) {
//			try {
//				jsonReply(response, indexParam);
//			} catch (JSONException e) {
//				e.printStackTrace();
//				errorReply(response, e, new Error("JSON Exception"));
//			}
//		}
	}
	
	private void jsonReply(HttpServletResponse response, Integer index) throws IOException, JSONException {
		System.out.println("Preparing JSON reply...");
		response.setContentType("text/json");
		
		JSONObject obj = new JSONObject();
		obj.put("Hello", "World");
		response.getWriter().write(obj.toString());
		System.out.println("--> "+obj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
