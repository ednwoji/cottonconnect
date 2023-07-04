package com.cottonconnect.elearning.ELearning.configuration;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class EmailUtility {
	// Replace sender@example.com with your "From" address.
	// This address must be verified with Amazon SES.
	static final String FROM = "noreply@cottonconnect.org";

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	static final String TO = "vignesh050694@gmail.com";

	// The configuration set to use for this email. If you do not want to use a
	// configuration set, comment the following variable and the
	// .withConfigurationSetName(CONFIGSET); argument below.
	static final String CONFIGSET = "ConfigSet";

	// The subject line for the email.
	static final String SUBJECT = "ELearning Video Published";

	// The HTML body for the email.
	static final String HTMLBODY = "<h1>A New </h1>"
			+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
			+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" + "AWS SDK for Java</a>";

	// The email body for recipients with non-HTML email clients.
	static final String TEXTBODY = "A new video has been published.";

	public static void sendEmail() {
		try {
			String secretkey = "AKIATQJMVMDIDZSH47RB";
			String accessKey = "UfeblAwUQXyKyzQvOzmh/XZED/uJ/ozmJleYsBVz";
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);

			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1)
					.build();
			SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body().withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM)
					// Comment or remove the next line if you are not using a
					// configuration set
					.withConfigurationSetName(CONFIGSET);
			client.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " + ex.getMessage());
		}
	}
}
