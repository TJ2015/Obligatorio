package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import util.PayPalUtil;

@WebServlet("/PayPal")
public class PayPalPreparar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalPreparar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String redirectUrl = "";
		List<Transaction> transactions = new ArrayList<>();
		APIContext apiContext = PayPalUtil.prepararCliente();
		
		Payer payer = new Payer();
		Details details = new Details();
		Amount amount = new Amount();
		Transaction transaction = new Transaction();
		Payment payment = new Payment();
		RedirectUrls redirectUrls = new RedirectUrls();
		
		// Payer
		payer.setPaymentMethod("paypal");
		
		//Details
		details.setShipping("0.00")
				.setTax("0.00")
				.setSubtotal("20.00");
		
		// Amount
		amount.setCurrency("USD")
				.setTotal("20.00")
				.setDetails(details);
		
		// Transaction
		transaction.setAmount(amount)
				.setDescription("Membresia en SAPo.com");
		
		
		transactions.add(transaction);
		// Payment
		payment.setIntent("sale")
				.setPayer(payer)
				.setTransactions(transactions);
		
		// Redirect URLs
		redirectUrls.setReturnUrl("http://localhost:8080/PayPalTest/paypal/pagar?approved=true")
				.setCancelUrl("http://localhost:8080/PayPalTest/paypal/pagar?approved=false");
		
		payment.setRedirectUrls(redirectUrls);
		
		try {
			payment = payment.create(apiContext);
		} catch (PayPalRESTException e) {
			// TODO 505
			e.printStackTrace();
		}
		
		List<Links> links = payment.getLinks();
		for( Links l : links ) {
			if( l.getRel().equals("approval_url")) {
				redirectUrl = l.getHref();
				break;
			}
		}
		
		response.sendRedirect(redirectUrl);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
