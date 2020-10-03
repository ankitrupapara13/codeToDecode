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

import com.hsbc.exceptions.DocumentNotFound;
import com.hsbc.models.Product;


public class ProductService {
//	private ProductDAO productDAO;
	
	public ProductService() {
//		productDAO = new ProductDAOImpl();
	
	}
	
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
			} catch (DocumentNotFound e) {
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
			}
			break;
		default:
			
			break;
			
		}
		
	}
	private void addProductsFromXML(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException, DocumentNotFound {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		
		if(document == null) {
			throw new DocumentNotFound("XML document not found");
		}
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
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
//				Product product = new Product(productId, productName, productPrice, productCategoryId);
				
//				productDAO.add(product)
				
			}
		}
		
		
		
	}
	
	private void addProductsFromJSON(InputStream inputstream) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
		Object obj = parser.parse(inputStreamReader);
		JSONArray json = (JSONArray)obj;
		for(Object product : json) {
			this.processJSONObject((JSONObject)product);
		}
	
	}
	
	private Product processJSONObject(JSONObject jsonObject) {
		JSONObject product = (JSONObject) jsonObject;
		long productId = (Long)product.get("productId");
		String productName = (String)product.get("productName");
		double productPrice = (Double)product.get("productPrice");
		long productCategoryId = (Long)product.get("productCategoryId");
		System.out.println("[ " + productId + ", " + productName + ", " + productPrice + ", " + productCategoryId + " ]");
		//				Product product = new Product(productId, productName, productPrice, productCategoryId);
		return null;
		
	}
}
