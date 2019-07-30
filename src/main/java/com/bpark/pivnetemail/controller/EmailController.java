package com.bpark.pivnetemail.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpark.pivnetemail.entity.PivnetProduct;
import com.bpark.pivnetemail.entity.PivnetUser;
import com.bpark.pivnetemail.entity.PivnetUserProduct;
import com.bpark.pivnetemail.service.PivnetService;

@Controller
public class EmailController {

	@Autowired
	PivnetService pivnetService;
	@Autowired
	private JavaMailSender javaMailSender;

	private String refreshToken = "";
	private String accessToken = "";

	@GetMapping( "/add-user" )
	public ResponseEntity<String> addUser( @RequestParam(name="email", required=true) String email ) {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.addUser( email ) );
	}

	@GetMapping( "/add-product" )
	public ResponseEntity<String> addProduct( @RequestParam(name="email", required=true) String email, @RequestParam(name="product", required=true) String product ) {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.addProductToUser( email, product ) );
	}

	@GetMapping( "/list-user" )
	public ResponseEntity<String> listUser() {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.listAllUsers() );
	}

	@GetMapping( "/send-new-emails" )
	public ResponseEntity<String> sendNewEmails() throws MalformedURLException, IOException, ParseException {
		sendNewEmailsHelper();
		return ResponseEntity.status( HttpStatus.OK ).body( "Emails sent to all users" );
	}

	@GetMapping( "/send-emails" )
	public ResponseEntity<String> sendEmails() throws MalformedURLException, IOException, ParseException {
		sendEmailsHelper();
		return ResponseEntity.status( HttpStatus.OK ).body( "Emails sent to all users" );
	}

	@GetMapping( "/send-emails-test" )
	public ResponseEntity<String> sendEmailsTest( @RequestParam String email ) throws MalformedURLException, IOException, ParseException {
		sendTestEmailsHelper( email );
		return ResponseEntity.status( HttpStatus.OK ).body( "Test email sent to user: " + email );
	}

	@GetMapping( "/add-refreshtoken" )
	public ResponseEntity<String> setRefreshToken( @RequestParam String token ) {
		refreshToken = token;
		return ResponseEntity.status( HttpStatus.OK ).body( "Set PivNet Refresh Token to: " + token );
	}

	@GetMapping( "/get-accesstoken" )
	public ResponseEntity<String> getAccessToken() throws MalformedURLException, IOException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.getAccessToken( refreshToken ) );
	}

	@GetMapping( "/check-releases")
	public ResponseEntity<String> checkReleases( @RequestParam String slug ) throws MalformedURLException, IOException, ParseException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.checkProductReleases( slug, accessToken ) );
	}

	@GetMapping( "/update-releases")
	public ResponseEntity<String> updateReleases( @RequestParam String slug ) throws MalformedURLException, IOException, ParseException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.updateProductReleases( slug, accessToken ) );
	}

	@GetMapping( "/list-releases")
	public ResponseEntity<String> listReleases( @RequestParam String slug ) throws MalformedURLException, IOException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.listProductReleases( slug ) );
	}

	@GetMapping( "/check-latest-release")
	public ResponseEntity<String> checkLatestRelease( @RequestParam String slug ) throws MalformedURLException, IOException, ParseException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.checkLatestProductRelease( slug, accessToken ) );
	}

	@GetMapping( "/update-latest-release")
	public ResponseEntity<String> updateLatestrelease( @RequestParam String slug ) throws MalformedURLException, IOException, ParseException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.updateLatestProductRelease( slug, accessToken ) );
	}

	@GetMapping( "/list-latest-release")
	public ResponseEntity<String> checkLatestrelease( @RequestParam String slug ) throws MalformedURLException, IOException, ParseException {
		return ResponseEntity.status( HttpStatus.OK ).body( pivnetService.listLatestProductRelease( slug ) );
	}

	private void sendNewEmailsHelper() throws MalformedURLException, IOException, ParseException {

		List<PivnetUser> userList = pivnetService.getAllUsers();
		for ( PivnetUser u : userList ) {
			SimpleMailMessage emailMessage = new SimpleMailMessage();

			emailMessage.setTo( u.getEmail() );

			emailMessage.setSubject( "Email Alert for Pivnet Products" );

			String productReleases = "";
			for ( PivnetUserProduct p : u.getPivnetUserProducts() ) {
				String slug = p.getSlug();
				pivnetService.updateProductReleases( slug, accessToken );

				PivnetProduct latestProductReleasePivnet = pivnetService.checkLatestProductReleaseObject( slug, accessToken );
				PivnetProduct latestProductReleaseKnown = pivnetService.listLatestProductReleaseObject( slug );


				String latestStringDateProductReleasePivnet = latestProductReleasePivnet.getRelease_date();
				String latestStringDateProductReleaseKnown = latestProductReleaseKnown.getRelease_date();

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date latestDateProductReleasePivnet = format.parse ( latestStringDateProductReleasePivnet );
				Date latestDateProductReleaseKnown = format.parse ( "2000-12-31" ); 
				if ( latestStringDateProductReleaseKnown != null && !latestStringDateProductReleaseKnown.isEmpty() ) {
					latestDateProductReleaseKnown = format.parse ( latestProductReleaseKnown.getRelease_date() ); 
				}

				if ( latestDateProductReleaseKnown.before( latestDateProductReleasePivnet ) ) {
					updateReleases( slug );
					updateLatestrelease( slug );

					productReleases += pivnetService.listLatestProductReleaseObject( slug ).toStringEmail();
					productReleases += "\n\n";

				}
			}

			emailMessage.setText( "You have an alert for new product updates\n\n" + productReleases + "\n\nThank you\nPivotal PA Team");

			if ( !productReleases.isEmpty() ) {
				javaMailSender.send( emailMessage );
			}

		}

	}

	private void sendEmailsHelper() throws MalformedURLException, IOException, ParseException {

		List<PivnetUser> userList = pivnetService.getAllUsers();
		for ( PivnetUser u : userList ) {
			SimpleMailMessage emailMessage = new SimpleMailMessage();

			emailMessage.setTo( u.getEmail() );

			emailMessage.setSubject( "Pivnet Products Subscription" );

			String productReleases = "";
			for ( PivnetUserProduct p : u.getPivnetUserProducts() ) {
				String slug = p.getSlug();
				pivnetService.updateProductReleases( slug, accessToken );

				updateReleases( slug );
				updateLatestrelease( slug );

				productReleases += pivnetService.listLatestProductReleaseObject( slug ).toStringEmail();
				productReleases += "\n\n";
			}

			emailMessage.setText( "Here are the latest products \n\n" + productReleases + "\n\nThank you\nPivotal PA Team");

			if ( !productReleases.isEmpty() ) {
				javaMailSender.send( emailMessage );
			}

		}

	}

	public void sendTestEmailsHelper( String email ) throws MalformedURLException, IOException, ParseException {

		SimpleMailMessage emailMessage = new SimpleMailMessage();

		emailMessage.setTo( email );

		emailMessage.setSubject( "Test Email from PivnetAlerts" );

		emailMessage.setText( "This is a test");

		javaMailSender.send( emailMessage );
	}


}

