package com.hsbc.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsbc.exceptions.InputStreamEmptyException;
import com.hsbc.models.Product;


public class ProductService {
//	private ProductDAO productDAO;
	
	public ProductService() {
//		productDAO = new ProductDAOImpl();
	
	}
	/*
	 * Methods calls method to add XML or JSON object to 
	 * database using the extension of file upload
	 */
	public void addProduct(Part file) {
		String name = file.getSubmittedFileName();
		String type = name.split(".")[1];
		switch(type) {
		case "xml":
			try {
				this.addProductsFromXML(file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InputStreamEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "json":
			try {
				this.addProductsFromJSON(file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InputStreamEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			
			break;
			
		}
		
	}
	/*
	 * Method parses XML file using:
	 * 	i. Create a document of XML inputstream using DocumentBuilder
	 *  ii. if document is null DocumentNotFound exception thrown else further process
	 *  iii. List of nodes of XML is obtained in XML file
	 *  iv. Nodes list is iteratively traversed and when node of type ELEMENT_NODE encountered, information
	 *  	is retrieved and an object of Product is created
	 *  v. ProductDAO's method is called to store the object to DB
	 */
	private void addProductsFromXML(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException, InputStreamEmptyException {
		if(inputStream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
				
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				Product product = processXMLObject(node);
				
//				productDAO.addProductsToDB(product)
				
			}
		}
		
		
		
	}
	/*
	 * Method parses individual node of xml element to retrieve the information
	 */
	private Product processXMLObject(Node node) {
		Element element = (Element) node;
		
		int productId  = Integer.parseInt(element.getElementsByTagName("ProductId")
										.item(0).getChildNodes().item(0).getNodeValue());
		String productName = element.getElementsByTagName("ProductName")
										.item(0).getChildNodes().item(0).getNodeValue();
		double productPrice = Double.parseDouble(element.getElementsByTagName("ProductPrice")
										.item(0).getChildNodes().item(0).getNodeValue());
		int productCategoryId = Integer.parseInt(element.getElementsByTagName("ProductCategoryId")
										.item(0).getChildNodes().item(0).getNodeValue());
		
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategoryId + " ]");
		
		Product productObj = new Product((int)productId, productName, productPrice, (int)productCategoryId);
		return productObj;
		
	}
	/*
	 * Method parses JSON file using JSONParser by:
	 * 	i. Calling parse() on inputstream
	 * 	ii. Creation of JSONArray of objects on JSON file
	 * 	iii. Individual object of JSON is then used to extract different fields and 
	 * 		Product object is created out it
	 * 	iv. ProductDAO's method is called to store object to DB
	 */
	private void addProductsFromJSON(InputStream inputstream) throws IOException, ParseException, InputStreamEmptyException {
		if(inputstream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		JSONParser parser = new JSONParser();
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
		Object obj = parser.parse(inputStreamReader);
		JSONArray json = (JSONArray)obj;
		
		for(Object product : json) {
			Product productObj = this.processJSONObject((JSONObject)product);
		
//			productDAO.addProductsToDB(product)
		}
	
	}
	/*
	 * Method parses JSON object to extract fields and created an object out of it
	 */
	private Product processJSONObject(JSONObject jsonObject) {
		JSONObject product = (JSONObject) jsonObject;
		long productId = (Long)product.get("productId");
		String productName = (String)product.get("productName");
		double productPrice = (Double)product.get("productPrice");
		long productCategoryId = (Long)product.get("productCategoryId");
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategoryId + " ]");
		
		Product productObj = new Product((int)productId, productName, productPrice, (int)productCategoryId);
		return productObj;
		
	}
}
