/**
 *
 */
package com.sai.ffac.addon.paypalcheckoutaddon.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.storefront.controllers.pages.checkout.steps.AbstractCheckoutStepController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentResponseDetailsType;
import urn.ebay.apis.eBLBaseComponents.GetExpressCheckoutDetailsResponseDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;
import urn.ebay.apis.eBLBaseComponents.PaymentStatusCodeType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.paypal.core.credential.ICredential;
import com.paypal.core.credential.SignatureCredential;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.sai.ffac.addon.paypalcheckoutaddon.controllers.SaiffaccheckoutaddonControllerConstants;


/**
 * @author tuan.truong
 *
 */
@Controller
@RequestMapping(value = "/checkout/multi/addon/sai-ffac")
public class SaiFfacCheckoutStepController extends AbstractCheckoutStepController
{
	private final static String SAI_FFAC = "sai-ffac";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.storefront.controllers.pages.checkout.steps.CheckoutStepController#enterStep(org.springframework
	 * .ui.Model, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException,
			CommerceCartModificationException
	{
		this.prepareDataForPage(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.deliveryMethod.breadcrumb"));
		model.addAttribute("metaRobots", "noindex,nofollow");
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		return SaiffaccheckoutaddonControllerConstants.SaiFfacPage;
	}

	/**
	 * @return CheckoutStep
	 */
	private CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(SAI_FFAC);
	}


	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	@RequireHardLogIn
	public String summarize(@RequestParam(value = "token", required = true) final String token,
			@RequestParam(value = "PayerID", required = true) final String payerId)
	{
		final String userName = "saimerchant_api1.sai-it.com";
		final String password = "JRMKS6JBRPFE7LFR";
		final String signature = "An5ns1Kso7MWUdW4ErQKJJJ4qi4-ARCPTqkO53ZfgtV-iv.NAgMWCCCq";

		GetExpressCheckoutDetailsResponseDetailsType expCheckoutDetail;
		boolean isSuccess = false;
		try
		{
			expCheckoutDetail = getExpressCheckoutDetails(token, userName, password, signature);
			isSuccess = commitExpressCheckout(expCheckoutDetail, userName, password, signature, token, payerId);
		}
		catch (OAuthException | SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException | IOException
				| InterruptedException | ParserConfigurationException | SAXException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isSuccess)
		{
			return SaiffaccheckoutaddonControllerConstants.SaiFfacSummaryPage;
		}
		else
		{
			return SaiffaccheckoutaddonControllerConstants.SaiFfacPage;
		}
	}


	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	@RequireHardLogIn
	public String doPayment()
	{
		//		return getCheckoutStep().nextStep();
		final String userName = "saimerchant_api1.sai-it.com";
		final String password = "JRMKS6JBRPFE7LFR";
		final String signature = "An5ns1Kso7MWUdW4ErQKJJJ4qi4-ARCPTqkO53ZfgtV-iv.NAgMWCCCq";
		String redirectUrl = "";
		try
		{
			redirectUrl = SetExpressCheckout(userName, password, signature);
		}
		catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException | InvalidResponseDataException
				| ClientActionRequiredException | MissingCredentialException | OAuthException | IOException | InterruptedException
				| ParserConfigurationException | SAXException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}

		return REDIRECT_PREFIX + redirectUrl;
	}

	public String SetExpressCheckout(final String userName, final String password, final String signature)
			throws SSLConfigurationException, InvalidCredentialException, HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException,
			ParserConfigurationException, SAXException
	{

		final BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setValue("25");
		orderTotal.setCurrencyID(CurrencyCodeType.USD);

		final PaymentDetailsType payDetail = new PaymentDetailsType();
		payDetail.setOrderDescription("Test Order");
		payDetail.setInvoiceID("INVOICE-" + Math.random());
		payDetail.setOrderTotal(orderTotal);
		payDetail.setPaymentAction(PaymentActionCodeType.SALE);

		final List<PaymentDetailsType> paymentDetails = new ArrayList<PaymentDetailsType>();
		paymentDetails.add(payDetail);

		final String returnURL = "https://54.169.44.29:9002/ffacstorefront/en/checkout/multi/addon/sai-ffac/summary";
		final String cancelURL = "https://54.169.44.29:9002/ffacstorefront/en/checkout/multi/addon/sai-ffac";
		final String customId = "C1703";

		final SetExpressCheckoutRequestDetailsType expCheckoutReqDetail = new SetExpressCheckoutRequestDetailsType();
		expCheckoutReqDetail.setPaymentDetails(paymentDetails);
		expCheckoutReqDetail.setReturnURL(returnURL);
		expCheckoutReqDetail.setCancelURL(cancelURL);
		expCheckoutReqDetail.setCustom(customId);

		final SetExpressCheckoutRequestType pprequest = new SetExpressCheckoutRequestType();
		pprequest.setSetExpressCheckoutRequestDetails(expCheckoutReqDetail);

		final SetExpressCheckoutReq request = new SetExpressCheckoutReq();
		request.setSetExpressCheckoutRequest(pprequest);

		final Properties profile = new Properties();
		//		profile.put("service.EndPoint", "https://api-3t.sandbox.paypal.com/2.0"); //or set mode=sandbox
		profile.put("mode", "sandbox");

		final PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(profile);

		final ICredential credential = new SignatureCredential(userName, password, signature);
		final SetExpressCheckoutResponseType response = service.setExpressCheckout(request, credential);

		final String token = response.getToken();
		final String ack = response.getAck().getValue();
		//		System.out.println("Ack code: " + ack);
		//		System.out.println("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token);

		final String redirectUrl = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token;
		return redirectUrl;
	}

	public static GetExpressCheckoutDetailsResponseDetailsType getExpressCheckoutDetails(final String token,
			final String userName, final String password, final String signature) throws OAuthException, SSLConfigurationException,
			InvalidCredentialException, HttpErrorException, InvalidResponseDataException, ClientActionRequiredException,
			MissingCredentialException, IOException, InterruptedException, ParserConfigurationException, SAXException
	{

		final GetExpressCheckoutDetailsRequestType pprequest = new GetExpressCheckoutDetailsRequestType();
		pprequest.setToken(token);

		final GetExpressCheckoutDetailsReq request = new GetExpressCheckoutDetailsReq();
		request.setGetExpressCheckoutDetailsRequest(pprequest);

		final Properties profile = new Properties();
		//		profile.put("service.EndPoint", "https://api-3t.sandbox.paypal.com/2.0");
		profile.put("mode", "sandbox");

		final PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(profile);

		final ICredential credential = new SignatureCredential(userName, password, signature);
		final GetExpressCheckoutDetailsResponseType expCheckoutDetailResponse = service.getExpressCheckoutDetails(request,
				credential);
		final GetExpressCheckoutDetailsResponseDetailsType expCheckoutDetail = expCheckoutDetailResponse
				.getGetExpressCheckoutDetailsResponseDetails();
		return expCheckoutDetail;
	}

	public static boolean commitExpressCheckout(final GetExpressCheckoutDetailsResponseDetailsType expCheckoutDetail,
			final String userName, final String password, final String signature, final String token, final String payerId)
			throws SSLConfigurationException, InvalidCredentialException, HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException, OAuthException, IOException, InterruptedException,
			ParserConfigurationException, SAXException
	{

		final DoExpressCheckoutPaymentRequestDetailsType expCheckoutPaymentDetail = new DoExpressCheckoutPaymentRequestDetailsType();
		expCheckoutPaymentDetail.setToken(token);
		expCheckoutPaymentDetail.setPayerID(payerId);
		expCheckoutPaymentDetail.setPaymentDetails(expCheckoutDetail.getPaymentDetails());
		expCheckoutPaymentDetail.setPaymentAction(expCheckoutDetail.getPaymentDetails().get(0).getPaymentAction()); //remember to check null

		final DoExpressCheckoutPaymentRequestType pprequest = new DoExpressCheckoutPaymentRequestType();
		pprequest.setDoExpressCheckoutPaymentRequestDetails(expCheckoutPaymentDetail);

		final DoExpressCheckoutPaymentReq request = new DoExpressCheckoutPaymentReq();
		request.setDoExpressCheckoutPaymentRequest(pprequest);

		final Properties profile = new Properties();
		//		profile.put("service.EndPoint", "https://api-3t.sandbox.paypal.com/2.0");
		profile.put("mode", "sandbox");

		final PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(profile);

		final ICredential credential = new SignatureCredential(userName, password, signature);
		final DoExpressCheckoutPaymentResponseType expCheckoutPaymentResponse = service.doExpressCheckoutPayment(request,
				credential);
		final DoExpressCheckoutPaymentResponseDetailsType expCheckoutPaymentDetails = expCheckoutPaymentResponse
				.getDoExpressCheckoutPaymentResponseDetails();
		if (expCheckoutPaymentDetails != null)
		{
			final PaymentInfoType paymentInfo = expCheckoutPaymentDetails.getPaymentInfo().get(0);
			if (paymentInfo.getPaymentStatus() == PaymentStatusCodeType.COMPLETED)
			{
				System.out.println("Payment completed.");
				return true;
			}
			else
			{
				System.out.println("Payment has error.");
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.storefront.controllers.pages.checkout.steps.CheckoutStepController#back(org.springframework
	 * .web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	@RequireHardLogIn
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.storefront.controllers.pages.checkout.steps.CheckoutStepController#next(org.springframework
	 * .web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	@RequireHardLogIn
	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

}