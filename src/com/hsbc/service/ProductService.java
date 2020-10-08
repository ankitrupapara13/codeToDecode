package com.hsbc.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsbc.dto.ProductFileDTO;
import com.hsbc.exceptions.InputStreamEmptyException;
import com.hsbc.exceptions.InvalidFileFormatException;
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
	public ProductFileDTO addProduct(Part file) {
		String name = file.getSubmittedFileName();
		String type = name.split("\\.")[1];
		
		switch(type) {
			case "xml":
				try {
					return this.addProductsFromXML(file.getInputStream());
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
					return this.addProductsFromJSON(file.getInputStream());
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
				try {
					throw new InvalidFileFormatException("File format invalid");
				} catch (InvalidFileFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
		}
		return new ProductFileDTO();
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
	private ProductFileDTO addProductsFromXML(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException, InputStreamEmptyException {
		if(inputStream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		
		int successCount = 0;
		int failedCount = 0;
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				Product product = processXMLObject(node);
				if(product == null)
					failedCount++;
				else 
					successCount++;
//				productDAO.addProductsToDB(product)
				
			}
		}
		System.out.println("Success Count: " + successCount);
		System.out.println("Failure Count: " + failedCount);
		ProductFileDTO productFileDTO = new ProductFileDTO(successCount, failedCount);
		
		return productFileDTO;
		
	}
	/*
	 * Method parses individual node of xml element to retrieve the information
	 */
	private Product processXMLObject(Node node) {
		Element element = (Element) node;
		int productId = 0;
		String productName = "";
		double productPrice = 0;
		int productCategoryId = 0;
		int companyId = 0;
		try {
			productId  = Integer.parseInt(element.getElementsByTagName("ProductId")
											.item(0).getChildNodes().item(0).getNodeValue());
			productName = element.getElementsByTagName("ProductName")
											.item(0).getChildNodes().item(0).getNodeValue();
			productPrice = Double.parseDouble(element.getElementsByTagName("ProductPrice")
											.item(0).getChildNodes().item(0).getNodeValue());
			productCategoryId = Integer.parseInt(element.getElementsByTagName("ProductCategoryId")
											.item(0).getChildNodes().item(0).getNodeValue());
			companyId = Integer.parseInt(element.getElementsByTagName("CompanyId")
										.item(0).getChildNodes().item(0).getNodeValue());
		}catch(NullPointerException e) {
			return null;
		}
		if(productId <= 0 || productName == null || productPrice <= 0 || productCategoryId <= 0 || companyId <= 0)
			return null;
		
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategoryId + ", " + companyId + " ]");
		
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
	private ProductFileDTO addProductsFromJSON(InputStream inputstream) throws IOException, ParseException, InputStreamEmptyException {
		if(inputstream == null) {
			throw new InputStreamEmptyException("InputStream cannot be empty");
		}
		JSONParser parser = new JSONParser();
		
		int successCount = 0;
		int failedCount = 0;
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
		Object obj = parser.parse(inputStreamReader);
		JSONArray json = (JSONArray)obj;
		
		for(Object product : json) {
			Product productObj = this.processJSONObject((JSONObject)product);
			if(productObj == null)
				failedCount++;
			else
				successCount++;
//			productDAO.addProductsToDB(product)
		}
		System.out.println("Success Count: " + successCount);
		System.out.println("Failure Count: " + failedCount);
		
		ProductFileDTO productFileDTO = new ProductFileDTO(successCount, failedCount);
		
		return productFileDTO;
		
	}
	/*
	 * Method parses JSON object to extract fields and created an object out of it
	 */
	private Product processJSONObject(JSONObject jsonObject) {
		JSONObject product = (JSONObject) jsonObject;
		long productId = 0;
		String productName = "";
		double productPrice = 0;
		long productCategoryId = 0;
		long companyId = 0;
		try {
			productId = (Long)product.get("productId");
			productName = (String)product.get("productName");
			productPrice = (Double)product.get("productPrice");
			productCategoryId = (Long)product.get("productCategoryId");
			companyId = (Long)product.get("companyId");
		}catch(NullPointerException e) {
			return null;
		}
		if(productId <= 0 || productName == null || productPrice <= 0 || productCategoryId <= 0 || companyId <= 0)
			return null;
		
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategoryId + ", " + companyId + " ]");
		
		Product productObj = new Product((int)productId, productName, productPrice, (int)productCategoryId);
		return productObj;
		
	}
}
