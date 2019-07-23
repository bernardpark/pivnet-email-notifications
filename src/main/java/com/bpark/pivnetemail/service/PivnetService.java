package com.bpark.pivnetemail.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bpark.pivnetemail.entity.LatestRelease;
import com.bpark.pivnetemail.entity.PivnetProduct;
import com.bpark.pivnetemail.entity.PivnetUser;
import com.bpark.pivnetemail.entity.PivnetUserProduct;
import com.bpark.pivnetemail.model.Product;
import com.bpark.pivnetemail.model.Release;
import com.bpark.pivnetemail.repository.LatestReleaseRepository;
import com.bpark.pivnetemail.repository.PivnetProductRepository;
import com.bpark.pivnetemail.repository.PivnetUserRepository;
import com.bpark.pivnetemail.response.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PivnetService {

	@Autowired 
	private PivnetUserRepository pivnetUserRepository;
	@Autowired 
	private PivnetProductRepository pivnetProductRepository;
	@Autowired 
	private LatestReleaseRepository latestReleaseRepository;


	public String addUser( String email ) {
		PivnetUser user = new PivnetUser();
		user.setEmail( email );
		pivnetUserRepository.save( user );
		return "Successfully saved: " + user.toString();
	}

	public String addProductToUser( String email, String slug ) {
		Iterable<PivnetUser> users = pivnetUserRepository.findAll();
		Iterator userIterator = users.iterator();
		while ( userIterator.hasNext() ) {
			PivnetUser user = ( PivnetUser ) userIterator.next();
			if ( user.getEmail().equals( email ) ) {
				PivnetUserProduct product = new PivnetUserProduct();
				product.setPivnetUser( user );
				product.setSlug( slug );
				user.getPivnetUserProducts().add( product );
				pivnetUserRepository.save( user );
				return "Successfully saved: " + slug + " to user: " + user.toString();
			}
		}
		return "Could not save: " + slug + " to user: " + email;
	}

	public String listAllUsers() {
		Iterator<PivnetUser> users = pivnetUserRepository.findAll().iterator();
		String message = "";

		while ( users.hasNext() ) {
			PivnetUser user = ( PivnetUser ) users.next();
			message += "----USER----\n";
			message += user.getEmail();
			message += "\n";
			for ( PivnetUserProduct p : user.getPivnetUserProducts() ) {
				message += "  " + p.getSlug();
				message += "\n";
			}
		}

		return message;
	}

	public List<PivnetUser> getAllUsers() {
		Iterator<PivnetUser> users = pivnetUserRepository.findAll().iterator();
		List<PivnetUser> userList = new ArrayList<PivnetUser>();

		while ( users.hasNext() ) {
			userList.add( users.next() );
		}

		return userList;
	}

	public String getAccessToken( String refreshToken ) throws MalformedURLException, IOException {
		URL urlObject = new URL( "https://network.pivotal.io/api/v2/authentication/access_tokens" );

		HttpURLConnection connection = ( HttpURLConnection ) urlObject.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod( "POST" );

		OutputStream os = connection.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter( os, "UTF-8" );    
		osw.write( "{\"refresh_token\":\"" + refreshToken + "\"}" );
		osw.flush();
		osw.close();
		os.close();

		BufferedReader in = new BufferedReader(
				new InputStreamReader( connection.getInputStream() ) );
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ( ( inputLine = in.readLine() ) != null ) {
			response.append( inputLine );
		}
		in.close();
		connection.disconnect();

		String responseJson = response.toString();
		ObjectMapper objectMapper = new ObjectMapper();
		AccessToken accessTokenObject = objectMapper.readValue( responseJson, AccessToken.class );

		return accessTokenObject.getAccess_token();

	}

	public String checkProductReleases( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		List<Release> releases = getProductReleases( slug, accessToken );
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue( out, releases );
		final byte[] data = out.toByteArray();

		return new String( data ); 
	}

	public String updateProductReleases( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		List<Release> releases = getProductReleases( slug, accessToken );
		
		savePivnetProducts( releases, slug );

		return "Saved :" + releases.size() + slug + "release records"; 
	}

	public String listProductReleases( String slug ) throws MalformedURLException, IOException {
		Iterator<PivnetProduct> releases = pivnetProductRepository.findAll().iterator();
		List<PivnetProduct> releaseList = new ArrayList<PivnetProduct>();
		while ( releases.hasNext() ) {
			releaseList.add( releases.next() );
		}

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue( out, releaseList );
		final byte[] data = out.toByteArray();

		return new String( data ); 
	}

	public String checkLatestProductRelease( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		List<Release> releases = getProductReleases( slug, accessToken );
		LatestRelease latestRelease = new LatestRelease();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse ( "2000-12-31" ); 
		
		for ( Release r : releases ) {
			if ( format.parse ( r.getRelease_date() ).after( date ) ) {
				date = format.parse ( r.getRelease_date() );
				latestRelease.setName( slug );
				latestRelease.setReleaseDate( r.getRelease_date() );
				latestRelease.setReleaseId( r.getId() );
			}
		}

		String jsonInString = "EMPTY";
		ObjectMapper marshalMapper = new ObjectMapper();
		jsonInString = marshalMapper.writeValueAsString( latestRelease );

		return jsonInString;
	}

	public PivnetProduct checkLatestProductReleaseObject( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		List<Release> releases = getProductReleases( slug, accessToken );
		String latestReleaseId = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse ( "2000-12-31" ); 
		
		for ( Release r : releases ) {
			if ( format.parse ( r.getRelease_date() ).after( date ) ) {
				latestReleaseId = r.getId();
			}
		}
		
		List<PivnetProduct> pivnetProducts = pivnetProductRepository.findById( latestReleaseId );

		return pivnetProducts.get( 0 );
	}

	public String updateLatestProductRelease( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		Product product = new Product();

		List<Release> releases = getProductReleases( product, slug, accessToken );

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse ( "2000-12-31" ); 
		
		for ( Release r : releases ) {
			if ( format.parse ( r.getRelease_date() ).after( date ) ) {
				date = format.parse ( r.getRelease_date() );
				LatestRelease latestRelease = new LatestRelease();
				latestRelease.setName( slug );
				latestRelease.setReleaseDate( r.getRelease_date() );
				latestRelease.setReleaseId( r.getId() );
				latestReleaseRepository.save( latestRelease );
			}
		}

		String jsonInString = "EMPTY";
		ObjectMapper marshalMapper = new ObjectMapper();
		jsonInString = marshalMapper.writeValueAsString( product );

		return jsonInString;
	}

	public String listLatestProductRelease( String slug ) throws MalformedURLException, IOException, ParseException {
		String jsonInString = "";
		Iterator<LatestRelease> latestReleases = latestReleaseRepository.findAll().iterator();

		while( latestReleases.hasNext() ) {
			LatestRelease latestRelease = latestReleases.next();
			if ( latestRelease.getName().equals( slug ) ) {
				List<PivnetProduct> pivnetProducts = pivnetProductRepository.findById( latestRelease.getReleaseId() );
				PivnetProduct pivnetProduct = pivnetProducts.get( 0 );

				ObjectMapper marshalMapper = new ObjectMapper();
				jsonInString = marshalMapper.writeValueAsString( pivnetProduct );
				return jsonInString;
			}
		}

		return jsonInString;
	}

	public PivnetProduct listLatestProductReleaseObject( String slug ) throws MalformedURLException, IOException, ParseException {
		Iterator<LatestRelease> latestReleases = latestReleaseRepository.findAll().iterator();
		LatestRelease latestReleast = new LatestRelease();
		PivnetProduct pivnetProduct = new PivnetProduct();

		while( latestReleases.hasNext() ) {
			latestReleast = latestReleases.next();
			if ( latestReleast.getName().equals( slug ) ) {
				List<PivnetProduct> pivnetProducts = pivnetProductRepository.findById( latestReleast.getReleaseId() );
				pivnetProduct = pivnetProducts.get( 0 );
			}
		}

		return pivnetProduct;
	}

	private void removeReleasesByReleaseType ( List<Release> releases, String releaseType ) {
		Collection <Release> removeReleases = new ArrayList<Release>();

		for ( Release r : releases ) {
			if ( r.getRelease_type().equals( releaseType ) ) {
				removeReleases.add( r );
			}
		}
		releases.removeAll( removeReleases );

	}

	private List<Release> getProductReleases( String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		String responseJson = getSimpleHttpRequestBody( "https://network.pivotal.io/api/v2/products/" + slug + "/releases", accessToken );
		ObjectMapper unmarshalMapper = new ObjectMapper();
		Product productObject = unmarshalMapper.readValue( responseJson, Product.class );

		List<Release> releases = productObject.getReleases();
		removeReleasesByReleaseType( releases, "Developer Release" );
		removeReleasesByReleaseType( releases, "Alpha Release" );
		removeReleasesByReleaseType( releases, "Beta Release" );
		removeReleasesByReleaseType( releases, "Release Candidate" );

		return releases; 
	}

	private List<Release> getProductReleases( Product product, String slug, String accessToken ) throws MalformedURLException, IOException, ParseException {
		String responseJson = getSimpleHttpRequestBody( "https://network.pivotal.io/api/v2/products/" + slug + "/releases", accessToken );
		ObjectMapper unmarshalMapper = new ObjectMapper();
		product = unmarshalMapper.readValue( responseJson, Product.class );

		List<Release> releases = product.getReleases();
		removeReleasesByReleaseType( releases, "Developer Release" );
		removeReleasesByReleaseType( releases, "Alpha Release" );
		removeReleasesByReleaseType( releases, "Beta Release" );
		removeReleasesByReleaseType( releases, "Release Candidate" );

		return releases; 
	}

	private void savePivnetProducts ( List<Release> releases, String product ) throws ParseException {
		ArrayList<PivnetProduct> productList = new ArrayList<PivnetProduct>();

		for ( Release r : releases ) {
			PivnetProduct pivnetProduct = new PivnetProduct();
			pivnetProduct.set_links( r.get_links().getSelf().getHref() );
			pivnetProduct.setAvailability(r.getAvailability() );
			pivnetProduct.setDescription( r.getDescription() );
			pivnetProduct.setEccn( r.getEccn() );
			pivnetProduct.setEnd_of_support_date( r.getEnd_of_support_date() );
			pivnetProduct.setId( r.getId() );
			pivnetProduct.setLicense_exception( r.getLicense_exception() );
			pivnetProduct.setRelease_date( r.getRelease_date() );
			pivnetProduct.setRelease_notes_url( r.getRelease_notes_url() );
			pivnetProduct.setRelease_type( r.getRelease_type() );
			pivnetProduct.setSoftware_files_updated_at( r.getSoftware_files_updated_at() );
			pivnetProduct.setUpdated_at( r.getUpdated_at() );
			pivnetProduct.setVersion( r.getVersion() );
			pivnetProduct.setName( product );
			productList.add( pivnetProduct );
		}

		Iterable<PivnetProduct> iterable = productList;

		pivnetProductRepository.saveAll( iterable );
	}

	private String getSimpleHttpRequestBody( String url, String accessToken ) throws MalformedURLException, IOException {
		URL urlObject = new URL( url );

		HttpURLConnection connection = ( HttpURLConnection ) urlObject.openConnection();
		connection.setRequestProperty ( "Authorization", "Bearer " + accessToken );
		connection.setRequestMethod( "GET" );

		BufferedReader in = new BufferedReader(
				new InputStreamReader( connection.getInputStream() ) );
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ( ( inputLine = in.readLine() ) != null ) {
			response.append( inputLine );
		}
		in.close();

		return response.toString();
	}

}
