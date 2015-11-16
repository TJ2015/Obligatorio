package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import util.PayPalUtil;

/**
 * Servlet implementation class PayPalPay
 */
@WebServlet("/PayPalPay")
public class PayPalPagar extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /*
	@EJB
	IControladorUsuario cUsu;
	*/
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalPagar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aprovado = request.getParameter("approved");
		
		if( aprovado != null ) {
			if( aprovado.equals("true")) {
				APIContext apiContext = PayPalUtil.prepararCliente();
				String payerId = request.getParameter("PayerID");
				String paymentId = request.getParameter("paymentId");
				
				try {
					// Get the PayPal payment
					Payment payment = Payment.get(apiContext, paymentId);
					PaymentExecution execution = new PaymentExecution();
					execution.setPayerId(payerId);
					
					// Execute PayPal payment (charge)
					payment.execute(apiContext, execution);
					
					/*
					 cUsu.pasarAPremium(nickname);					 
					 */
					response.getWriter().append("Compra Completada.");
					
				} catch (PayPalRESTException e) {
					// TODO Mostrar error "No se pudo completar el pago, intente de nuevo" o algo así"
					e.printStackTrace();
					//response.sendRedirect("error.xhtml");
				}
			} else {		
				response.getWriter().append("Compra cancelada.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
